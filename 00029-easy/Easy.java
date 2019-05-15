import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Dailyprogrammer: 29 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/r8a70/3222012_challenge_29_easy/
 */
public class Easy {

    public static void main(String[] args) throws Exception {
        System.out.println("isPalindrom(\"hannah\")=" + isPalindrome("hannah"));
        System.out.println("isPalindrom(\"12321\")=" + isPalindrome("12321"));
        System.out.println("isPalindrom(\"test\")=" + isPalindrome("test"));

        BufferedReader reader = new BufferedReader(new FileReader("poem.txt"));
        StringBuilder poem = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            poem.append(line);
        }
        System.out.println("Demetri Martin's is palindrom: " + isPalindrome(poem.toString()));
    }

    private static boolean isPalindrome(String str) {
        int start = 0;
        int end = str.length() - 1;
        while (start < end) {
            int startChar = str.codePointAt(start);
            if (!Character.isLetterOrDigit(startChar)) {
                start += startChar > 0xFFFF ? 2 : 1;
                continue;
            }

            int endChar = str.codePointAt(end);
            if (!Character.isLetterOrDigit(endChar)) {
                end -= endChar > 0xFFFF ? 2 : 1;
                continue;
            }

            System.out.println(startChar + " --- " + endChar);
            if (Character.toLowerCase(startChar) == Character.toLowerCase(endChar)) {
                start += startChar > 0xFFFF ? 2 : 1;
                end -= endChar > 0xFFFF ? 2 : 1;
            } else {
                return false;
            }
        }

        return true;
    }
}
