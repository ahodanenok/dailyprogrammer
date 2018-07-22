import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Dailyprogrammer: 6 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/pp81n/2142012_challenge_6_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        List<List<String>> lines = new ArrayList<List<String>>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(args[0]));

            String line;
            while ((line = reader.readLine()) != null) {
                List<String> words = new ArrayList<String>();
                for (String word : line.trim().split("\\s+")) {
                    words.add(word.trim());
                }

                lines.add(words);
            }
        } catch (IOException e) {
            System.out.println("Can't read file '" + args[0] + "'");
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) { }
            }
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(System.out));

            for (List<String> line : lines) {
                for (int i = 0; i < line.size(); i++) {
                    writer.write(dedup(line.get(i)));
                    if (i != line.size() - 1) {
                        writer.write(' ');
                    }
                }

                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Can't write to file '" + args[0] + "'");
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) { }
            }
        }
    }

    private static String dedup(String str) {
        if (str.length() < 4) {
            return str;
        }

        List<Integer[]> excluded = new ArrayList<Integer[]>();

        for (int seqSize = str.length() / 2; seqSize > 1; seqSize--) {
            next:
            for (int i = 0; i < str.length() - seqSize; i++) {
                for (Integer[] range : excluded) {
                    if (i >= range[0] && i < range[1]) {
                        continue next;
                    }
                }

                int offset = i + seqSize;
                int j = str.indexOf(str.substring(i, offset), offset);
                if (j < offset) {
                    continue next;
                }

                for (Integer[] range : excluded) {
                    if (j >= range[0] && j < range[1]) {
                        continue next;
                    }
                }

                Integer[] range = new Integer[2];
                range[0] = i;
                range[1] = i + seqSize;
                excluded.add(range);

                range = new Integer[2];
                range[0] = j;
                range[1] = j + seqSize;
                excluded.add(range);
            }
        }

        List<Integer[]> remove = new ArrayList<>(excluded.size() / 2);
        for (int i = 1; i < excluded.size(); i += 2) {
            remove.add(excluded.get(i));
        }

        remove.sort(new Comparator<Integer[]>() {
            public int compare(Integer[] a, Integer[] b) {
                if (a[0] < b[0] && a[1] < b[1]) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        StringBuilder sb = new StringBuilder(str);
        for (Integer[] range : remove) {
            sb.delete(range[0], range[1]);
        }

        return sb.toString();
    }
}
