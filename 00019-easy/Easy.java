import java.io.BufferedReader;
import java.io.FileReader;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Dailyprogrammer: 19 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/qlwrc/372012_challenge_19_easy/
 */
public class Easy {

    private static final Pattern TITLE_PATTERN = Pattern.compile("(ADVENTURE )?[IVX]+\\.[A-Z '-]*$");

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

        int alphanumericCount = 0;
        for (int i = start; i < end; i++) {
            line = lines.get(i).trim();
            if (line.length() == 0) {
                continue;
            }

            if (!TITLE_PATTERN.matcher(line).matches()) {
                for (int j = 0; j < line.length(); j++) {
                    char ch = line.charAt(j);
                    if (ch >= '0' && ch <= '9'
                            || ch >= 'a' && ch <= 'z'
                            || ch >= 'A' && ch <= 'Z') {
                        alphanumericCount++;
                    }
                }
            } else {
                System.out.println(line);
            }
        }

        System.out.println(alphanumericCount);
    }
}
