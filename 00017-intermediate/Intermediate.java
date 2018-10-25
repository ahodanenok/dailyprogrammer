import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Dailyprogrammer: 17 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/qhe4u/342012_challenge_17_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) throws Exception {
        URL url = new URL(args[0]);
        String search = args[1];

        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String content = sb.toString();
        content = content.replaceAll("\\<[^>]*>", "");

        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
