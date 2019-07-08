import java.io.BufferedReader;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Dailyprogrammer: 36 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/ruixl/452012_challenge_36_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) throws IOException {
        TranslationTable table = new TranslationTable();
        Reader reader = new FileReader("table.txt");
        table.load(reader);
        reader.close();

        String text = args[0];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            sb.append(table.translate(text.charAt(i)));
        }

        System.out.println(sb);
    }

    private static class TranslationTable {

        private Random random;
        private Map<Character, List<String>> translations;

        TranslationTable() {
            random = new Random(System.currentTimeMillis());
            translations = new HashMap<Character, List<String>>();
        }

        void load(Reader reader) throws IOException {
            String line;
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((line = bufferedReader.readLine()) != null) {
                int separatorIndex = line.indexOf(":");
                char key = Character.toUpperCase(line.substring(0, separatorIndex).charAt(0));
                String[] parts = line.substring(separatorIndex + 1).split(", ");

                List<String> variants = new ArrayList<String>(parts.length);
                for (int i = 0; i < parts.length; i++) {
                    variants.add(parts[i].trim());
                }

                translations.put(key, variants);
            }

            for (Map.Entry<Character, List<String>> entry : translations.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println();
        }

        String translate(Character ch) {
            char key = Character.toUpperCase(ch);
            if (translations.containsKey(key)) {
                List<String> variants = translations.get(key);
                return variants.get(random.nextInt(variants.size()));
            } else {
                return String.valueOf(ch);
            }
        }
    }
}
