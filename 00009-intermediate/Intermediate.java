import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Dailyprogrammer: 9 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/pu1y6/2172012_challenge_9_intermediate/
 */
public class Intermediate {

    private static final String FIND_CMD = "find";
    private static final String REPLACE_CMD = "replace";

    public static void main(String[] args) {
        if (FIND_CMD.equals(args[0])) {
            find(args[1], args[2]);
        } else if (REPLACE_CMD.equals(args[0])) {
            replace(args[1], args[2], args[3]);
        } else {
            throw new IllegalArgumentException(args[0]);
        }
    }

    private static void find(String str, String filePath) {
        String content = readFile(filePath);
        if (content == null) {
            return;
        }

        int foundCount = 0;
        int pos = 0;
        while (pos > -1 && pos < content.length()) {
            pos = content.indexOf(str, pos);
            if (pos >= 0) {
                foundCount++;
                pos += str.length();
            }
        }

        System.out.println(String.format("'%s' found %d time(s) in file '%s'", str, foundCount, filePath));
    }

    private static void replace(String str, String replacement, String filePath) {
        String content = readFile(filePath);
        if (content == null) {
            return;
        }

        System.out.println(String.format("Replacing '%s' with '%s' in file '%s'", str, replacement, filePath));
        writeFile(content.replace(str, replacement), filePath);
        System.out.println("Done");
    }

    private static String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeFile(String content, String path) {
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
