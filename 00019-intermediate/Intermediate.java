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
 * Dailyprogrammer: 19 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/qlwys/372012_challenge_19_intermediate/
 */
public class Intermediate {

    private static final Pattern STORY_PATTERN = Pattern.compile("(ADVENTURE )?[IVX]+\\.[A-Z '-]+$");
    private static final String WORD_PATTERN = "\\s+|--";

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

        int wordCount = 0;
        String story = null;
        Map<String, Integer> wordsInStory = new HashMap<String, Integer>();
        for (int i = start; i < end; i++) {
            line = lines.get(i).trim();
            if (line.length() == 0) {
                continue;
            }

            if (STORY_PATTERN.matcher(line).matches()) {
                //System.out.println(line);
                if (story != null) wordsInStory.put(story, wordCount);
                story = line;
                wordCount = 0;
            } else {
                String[] words = line.split(WORD_PATTERN);
                wordCount += words.length;
            }
        }

        if (story != null) wordsInStory.put(story, wordCount);
        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(wordsInStory.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                return b.getValue() - a.getValue();
            }
        });

        for (Map.Entry<String, Integer> e : entries) {
            System.out.println(e.getKey() + "   " + e.getValue());
        }
    }
}
