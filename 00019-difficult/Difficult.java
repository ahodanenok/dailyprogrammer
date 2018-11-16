import java.io.BufferedReader;
import java.io.FileReader;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * Dailyprogrammer: 19 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/qlxhh/372012_challenge_19_difficult/
 */
public class Difficult {

    private static final Pattern TITLE_PATTERN = Pattern.compile("(ADVENTURE )?[IVX]+\\.[A-Z '-]*$");
    private static final String WORD_PATTERN = "\\s+|--";

    private static final int PAGE_SIZE = 40;

    public static void main(String[] args) throws Exception {
        List<String> lines = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new FileReader("pg1661.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        int start = 0;
        for (int i = 0; i < lines.size(); i++) {
            if ("ADVENTURE I. A SCANDAL IN BOHEMIA".equals(lines.get(i))) {
                start = i;
                break;
            }
        }

        int end = lines.size();
        for (int i = lines.size() - 1; i >= 0; i--) {
            if (lines.get(i).indexOf("End of the Project Gutenberg EBook") == 0) {
                end = i;
                break;
            }
        }

        int page = 1;
        int linesCount = 0;
        Map<String, List<Integer>> index = new HashMap<String, List<Integer>>();
        for (int i = start; i < end; i++) {
            line = lines.get(i).trim();
            linesCount++;
            if (linesCount % PAGE_SIZE == 0) {
                page++;
                linesCount = 0;
            }

            if (line.length() == 0) {
                continue;
            }

            if (!TITLE_PATTERN.matcher(line).matches()) {
                String[] words = line.split(WORD_PATTERN);
                for (String word : words) {
                    word = word.replaceAll("[^A-Za-z0-9-]", "").toLowerCase().trim();
                    if (word.isEmpty()) continue;

                    List<Integer> pages = index.get(word);
                    if (pages == null) {
                        pages = new ArrayList<Integer>();
                        index.put(word, pages);
                    }

                    pages.add(page);
                }
            } else {
                System.out.println(line);
            }
        }

        for (Map.Entry<String, List<Integer>> entry : index.entrySet()) {
            if (entry.getValue().size() <= 100) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }
}
