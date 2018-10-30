import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Dailyprogrammer: 18 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/qivpg/352012_challenge_18_intermediate/
 */
public class Intermediate {

    private static final String HTML_TEMPLATE = 
        "<html>\n" +
        "<body>\n" +
        "<form>\n" +
        "%s" +
        "<input type=\"submit\" value=\"Submit\"/>\n" +
        "</form>\n" +
        "</body>\n" +
        "</html>\n";

    private static final String INPUT_TEMPLATE = "<input type=\"text\" name=\"%s\"/>";
    private static final String RADIO_TEMPLATE = "<input type=\"radio\" name=\"%s\" value=\"%s\"/> %s";
    private static final String SELECT_TEMPLATE = "<select name=\"%s\">%s</select";
    private static final String SELECT_OPTION_TEMPLATE = "<option value=\"%s\">%s</option>";

    private static final int RADIO_MAX_OPTIONS = 5;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        StringBuilder fields = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            int lastLP = line.lastIndexOf('(');
            int lastRP = line.lastIndexOf(')');
            
            

            if (lastLP >= 0 && lastRP >= 0) {
                String label = line.substring(0, lastLP).trim();
                fields.append(label).append(':').append("\n");

                String[] options = line.substring(lastLP + 1, lastRP).split(",\\s+");
                if (options.length >= RADIO_MAX_OPTIONS) {
                    StringBuilder selectOptions = new StringBuilder();
                    for (String option : options) {
                        option = option.trim();
                        selectOptions.append(String.format(SELECT_OPTION_TEMPLATE,
                            option.substring(option.indexOf("[") + 1, option.indexOf("]")).toLowerCase(),
                            option.replace("[", "").replace("]", ""))).append("\n");
                    }

                    fields.append(String.format(SELECT_TEMPLATE, label.toLowerCase(), selectOptions)).append("\n");
                } else {
                    for (String option : options) {
                        option = option.trim();
                        fields.append(String.format(RADIO_TEMPLATE,
                            label.toLowerCase(),
                            option.substring(option.indexOf("[") + 1, option.indexOf("]")).toLowerCase(),
                            option.replace("[", "").replace("]", ""))).append("\n");
                    }
                }
            } else {
                String label = line.substring(0, line.length() - 1).trim();
                fields.append(label).append(':').append("\n");
                fields.append(String.format(INPUT_TEMPLATE, label.toLowerCase())).append("\n");
            }

            fields.append("<br />").append("\n");
        }
        reader.close();

        System.out.println(String.format(HTML_TEMPLATE, fields.toString()));

        BufferedWriter writer = new BufferedWriter(new FileWriter("output.html"));
        writer.write(String.format(HTML_TEMPLATE, fields.toString()));
        writer.flush();
        writer.close();
    }
}
