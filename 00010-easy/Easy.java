import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Dailyprogrammer: 10 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pv98f/2182012_challenge_10_easy/
 */
public class Easy {

    private static String PHONE_PATTERN = "^(\\(\\d{3}\\)\\s*|\\d{3}\\s*[-.]?\\s*)?\\d{3}\\s*[-.]?\\s*\\d{4}$";

    public static void main(String[] args) {
        String phone = args[0].trim();
        System.out.println(String.format("%s -> %s", phone, Pattern.matches(PHONE_PATTERN, phone) ? "valid" : "not valid")); 
    }
}
