import java.util.Properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

/**
 * Dailyprogrammer: 28 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/r59c9/3202012_challenge_28_difficult/
 */
public class Difficult {

    public static void main(String[] args) throws Exception {
        String file = args[0];
        String format = args.length > 1 ? args[1] : null;

        BufferedReader fileReader = new BufferedReader(new FileReader(args[0]));

        String line;
        StringBuilder content = new StringBuilder();
        while ((line = fileReader.readLine()) != null) {
            content.append(line).append(System.lineSeparator());
        }
        fileReader.close();

        Properties config = new Properties();
        config.load(new FileReader("config.properties"));

        String data = "api_dev_key=" + config.getProperty("pastebin.apiKey")
            + "&api_paste_code=" + URLEncoder.encode(content.toString(), "utf-8")
            + "&api_option=paste&api_paste_private=0&api_paste_expire_date=1H";

        if (format != null) {
            data += "&api_paste_format=" + URLEncoder.encode(format, "utf-8");
        }

        byte[] dataBytes = data.getBytes("utf-8");

        URL pasteUrl = new URL("https://pastebin.com/api/api_post.php");
        HttpsURLConnection connection = (HttpsURLConnection) pasteUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-formurlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(dataBytes.length));
        connection.setDoOutput(true);
        connection.getOutputStream().write(dataBytes);
        connection.getOutputStream().flush();
        connection.getOutputStream().close();

        BufferedReader responseReader = new BufferedReader(
            new InputStreamReader(connection.getInputStream(), "utf-8"));
        int ch;
        while ((ch = responseReader.read()) != -1) {
            System.out.print((char) ch);
        }

        responseReader.close();
    }
}
