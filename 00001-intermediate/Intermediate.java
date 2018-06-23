import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Dailyprogrammer: 1 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/pihtx/intermediate_challenge_1/
 */
public class Intermediate {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private static File ENTRIES_FILE = new File("entries.txt");

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsageAndExit("java Intermediate (add|remove|list) [options]");
        }

        String cmd = args[0];
        if ("add".equals(cmd)) {
            if (args.length != 3) {
                printUsageAndExit("java Intermediate add <date> <eventName>");
            }

            add(args);
        } else if ("list".equals(cmd)) {
            list(args);
        } else if ("remove".equals(cmd)) {
            if (args.length != 2) {
                printUsageAndExit("java Intermediate remote <eventId>");
            }

            remove(args);
        } else {
            printUsageAndExit("java Intermediate (add|remove|list) [options]");
        }
    }

    private static void add(String[] args) {
        int lastId = 0;
        List<Entry> entries = loadEntries();
        for (Entry e : entries) {
            lastId = Math.max(e.id, lastId);
        }

        Date date;
        try {
            date = DATE_FORMAT.parse(args[1]);
        } catch (ParseException e) {
            System.out.println("wrong date format: " + e.getMessage());
            return;
        }

        entries.add(new Entry(lastId + 1, date, args[2]));
        saveEntries(entries);
    }

    private static void printUsageAndExit(String usage) {
        System.out.println("usage: " + usage);
        System.exit(-1);
    }

    private static void list(String[] args) {
        List<Entry> entries = loadEntries();
        for (Entry entry : entries) {
            System.out.println(entry.id + ". " + entry.name + " - " + DATE_FORMAT.format(entry.date));
        }
    }

    private static void remove(String[] args) {
        List<Entry> entries = loadEntries();
        int id = Integer.parseInt(args[1]);
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).id == id) {
                entries.remove(i);
                saveEntries(entries);
                System.out.println("Entry has been deleted");
                return;
            }
        }

        System.out.println("Entry not found");
    }

    private static List<Entry> loadEntries() {
        if (!ENTRIES_FILE.exists()) {
            return new ArrayList<Entry>();
        }

        List<Entry> entries = new ArrayList<Entry>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(ENTRIES_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    break;
                }

                String[] parts = line.split(";");
                entries.add(new Entry(Integer.parseInt(parts[0]), new Date(Long.parseLong(parts[1])), parts[2].trim()));
            }
        } catch (IOException e) {
            System.out.println("can't load entries: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) { }
            }
        }

        return entries;
    }

    private static void saveEntries(List<Entry> entries) {
        if (!ENTRIES_FILE.exists()) {
            try {
                ENTRIES_FILE.createNewFile();
            } catch (IOException e) {
                System.out.println("can't create entries file: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(ENTRIES_FILE));
            for (Entry entry : entries) {
                writer.write(entry.id + ";" + entry.date.getTime() + ";" + entry.name);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("can't save entries: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) { }
            }
        }
    }

    private static class Entry {

        private final int id;
        private final Date date;
        private final String name;

        Entry(int id, Date date, String name) {
            this.id = id;
            this.date = date;
            this.name = name;
        }
    }
}
