import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.Closeable;

import java.net.Socket;
import java.net.ServerSocket;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Enumeration;

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Dailyprogrammer: 41 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/shqs1/4192012_challenge_41_difficult/
 */
public class Difficult {

    private static final int PORT = 4141;
    private static final String FILES_DIR = "files";

    private static final byte[] COPY_BUF = new byte[1024];

    public static void main(String[] args) throws Exception {
        if ("fgen".equals(args[0])) {
            generateFiles(FILES_DIR);
        } else if ("client".equals(args[0])) {
            startClient("localhost", PORT);
        } else if ("server".equals(args[0])) {
            new Server(FILES_DIR).start(PORT);
        }
    }

    private static void generateFiles(String filesDir) throws Exception {
        File dir = new File(filesDir);
        if (!dir.exists()) {
            boolean created = dir.mkdir();
            if (!created) {
                System.out.println("Can't create dir: " + dir);
                return;
            }
        }

        for (File f : dir.listFiles()) {
            boolean deleted = f.delete();
            if (!deleted) {
                System.out.println("Can't delete file: " + f);
            }
        }

        int filesCount = randomInt(50, 100);
        for (int i = 0; i < filesCount; i++) {
            int sizeBytes = randomInt(1, 1024 * 1024);
            File file;
            while (true) {
                String fileName = "";
                while (fileName.length() < 5) {
                    fileName += (char) ('a' + randomInt(0, 'z' - 'a' + 1));
                }

                fileName += ".txt";
                file = new File(dir, fileName);
                if (!file.exists()) {
                    break;
                }
            }

            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                for (int b = 0; b < sizeBytes / 2; b++) {
                    writer.write((char) ('a' + randomInt(0, 'z' - 'a' + 1)));
                }
            } finally {
                if (writer != null) writer.close();
            }
        }
    }

    private static int randomInt(int from, int to) {
        return from + (int) Math.floor(Math.random() * to);
    }

    private static void copy(InputStream src, OutputStream dest) throws Exception {
        int bytesRead;
        while ((bytesRead = src.read(COPY_BUF)) != -1) {
            dest.write(COPY_BUF, 0, bytesRead);
        }
    }

    private static void copy(File fileFrom, File fileTo) throws Exception {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(fileFrom));
            out = new BufferedOutputStream(new FileOutputStream(fileTo));
            copy(in, out);
        } finally {
            close(in);
            close(out);
        }
    }

    private static void close(Closeable obj) {
        try {
            if (obj != null) obj.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> parseHeaders(BufferedReader in) throws Exception {
        Map<String, String> request = new HashMap<String, String>();

        String line;
        while (true) {
            line = in.readLine();
            if (line == null) break;

            line = line.trim();
            if (line.isEmpty()) break;

            String[] parts = line.split(":");
            request.put(parts[0].trim(), parts[1].trim());
        }

        return request;
    }

    private static void startClient(String host, int port) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter file name: ");
            if (!scanner.hasNextLine()) break;
            String fileName = scanner.nextLine();

            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("filename: " + fileName);
            out.println();

            Map<String, String> headers = parseHeaders(in);
            String status = headers.get("status");
            if ("ok".equals(status)) {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            } else if ("error".equals(status)) {
                System.out.println("Error occurred on the server");
                System.out.println(headers.get("message"));
            } else if ("not-found".equals(status)) {
                System.out.printf("File '%s' not found on server\n", fileName);
            } else {
                System.out.println("Unknown response status: " + status);
            }

            System.out.println();

            close(out);
            close(in);
            close(socket);
        }
    }

    private static class Server {

        private File filesDir;
        private LinkedHashMap<String, String> cache;
        private Map<String, Long> lastAccessTimestamp;
        private Archive archive;

        Server(String filesDir) {
            this.filesDir = new File(filesDir);
            this.cache = new LinkedHashMap<String, String>() {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, String> e) {
                    return this.size() > 5;
                }
            };
            this.archive = new Archive(this.filesDir);
            this.lastAccessTimestamp = new HashMap<String, Long>();
            long now = System.currentTimeMillis();
            for (File f : this.filesDir.listFiles()) {
                this.lastAccessTimestamp.put(f.getName(), now);
            }
        }

        void start(int port) throws Exception {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = null;
                BufferedReader in = null;
                PrintWriter out = null;
                try {
                    System.out.println("Waiting for request");
                    clientSocket = serverSocket.accept();
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream(), true);

                    Map<String, String> headers = parseHeaders(in);
                    String fileName = headers.get("filename");
                    System.out.printf("Request for '%s'\n", fileName);
                    System.out.printf("Cache: %s\n", cache.keySet());

                    File f = new File(filesDir, fileName);
                    if (f.exists() || archive.isArchived(fileName)) {
                        System.out.println("File was found");

                        String content = readContent(fileName);
                        lastAccessTimestamp.put(fileName, System.currentTimeMillis());

                        System.out.println("Sending content");
                        out.println("status: ok");
                        out.println();
                        out.print(content);
                    } else {
                        System.out.println("File not found");
                        out.println("status: not-found");
                        out.println();
                    }
                } catch (Exception e){
                    out.println("status: error");
                    out.println("message: " + e.getMessage());
                    out.println();
                    e.printStackTrace();
                } finally {
                    close(out);
                    close(in);
                    close(clientSocket);
                }
 
                System.out.println();
                packageUnusedFiles();
                System.out.println();
            }
        }

        private String readContent(String fileName) throws Exception {
            String content;
            if (!cache.containsKey(fileName)) {
                if (archive.isArchived(fileName)) {
                    restoreFromArchive(fileName);
                }

                System.out.println("Reading content from file");
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(new File(filesDir, fileName)));

                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }

                    content = sb.toString();
                    cache.put(fileName, content);
                } finally {
                    close(reader);
                }
            } else {
                System.out.println("Reading content from cache");
                content = cache.get(fileName);
            }

            return content;
        }

        private void packageUnusedFiles() {
            System.out.println("Checking for unused files");
            List<String> filesToPackage = new ArrayList<String>();
            for (File f : filesDir.listFiles()) {
                if (System.currentTimeMillis() - lastAccessTimestamp.get(f.getName()) >= 1 * 60 * 1000 /* 3m */) {
                    filesToPackage.add(f.getName());
                }
            }

            if (filesToPackage.size() > 0) {
                try {
                    archive.add(filesToPackage);
                    for (String fileName : filesToPackage) {
                        cache.remove(fileName);
                    }
                } catch (Exception e) {
                    System.out.println("Couldn't archive files");
                    e.printStackTrace();
                }
            } else {
                System.out.println("No files were archived");
            }
        }

        private void restoreFromArchive(String fileName) throws Exception {
            archive.extract(fileName);
        }
    }

    private static class Archive {

        private File filesDir;
        private File archiveFile;
        private Set<String> archived;

        Archive(File filesDir) {
            this.filesDir = filesDir;
            this.archiveFile = new File("archive.zip");
            this.archived = new HashSet<String>();

            if (archiveFile.exists()) {
                try {
                    ZipFile zip = new ZipFile(archiveFile);
                    Enumeration<? extends ZipEntry> entries = zip.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();
                        archived.add(entry.getName());
                    }
                } catch (Exception e) {
                    System.out.println("Can't list archived files");
                    e.printStackTrace();
                }
            }

            System.out.println("Archived files:");
            if (archived.size() > 0) {
                for (String fileName : archived) {
                    System.out.println("  "  + fileName);
                }
            } else {
                System.out.println("  -- None --");
            }
            System.out.println();
        }

        boolean isArchived(String fileName) {
            return archived.contains(fileName);
        }

        void add(List<String> fileNames) throws Exception {
            File tmpFile = File.createTempFile("archive", ".zip");

            ZipOutputStream out = null;
            try {
                out = new ZipOutputStream(new FileOutputStream(tmpFile, true));
                if (archiveFile.exists()) {
                    ZipFile zip = null;
                    try {
                        zip = new ZipFile(archiveFile);
                        Enumeration <? extends ZipEntry> entries = zip.entries();
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = entries.nextElement();
                            InputStream entryStream = null;
                            try {
                                entryStream = zip.getInputStream(entry);
                                out.putNextEntry(entry);
                                copy(entryStream, out);
                                out.closeEntry();
                            } finally {
                                close(entryStream);
                            }
                        }
                    } finally {
                        close(zip);
                    }
                }

                for (String fileName : fileNames) {
                    System.out.printf("Adding to archive '%s'\n", fileName);
                    File file = new File(filesDir, fileName);

                    InputStream in = null;
                    try {
                        in = new BufferedInputStream(new FileInputStream(file));
                        out.putNextEntry(new ZipEntry(fileName));
                        copy(in, out);
                        out.closeEntry();
                    } finally {
                        close(in);
                    }

                    file.delete();
                    archived.add(fileName);
                }
            } finally {
                close(out);
            }

            archiveFile.delete();
            copy(tmpFile, archiveFile);
            System.out.printf("Archived %d file(s)\n", fileNames.size());
        }

        void extract(String fileName) throws Exception {
            File tmpFile = File.createTempFile("archive", ".zip");
            ZipFile zip = new ZipFile(archiveFile);
            File file = new File(filesDir, fileName);

            OutputStream fileStream = null;
            InputStream entryStream = null;
            ZipOutputStream out = null;
            try {
                fileStream = new BufferedOutputStream(new FileOutputStream(file));
                out = new ZipOutputStream(new FileOutputStream(tmpFile, true));

                Enumeration <? extends ZipEntry> entries = zip.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    try {
                        entryStream = zip.getInputStream(entry);
                        if (entry.getName().equals(fileName)) {
                            copy(entryStream, fileStream);
                        } else {
                            out.putNextEntry(entry);
                            copy(entryStream, out);
                            out.closeEntry();
                        }
                    } finally {
                        close(entryStream);
                    }
                }
            } finally {
                close(fileStream);
                close(entryStream);
                close(out);
                close(zip);
            }

            archiveFile.delete();
            copy(tmpFile, archiveFile);
        }
    }
}
