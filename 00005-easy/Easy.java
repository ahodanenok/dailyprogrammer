import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Dailyprogrammer: 5 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pnhyn/2122012_challenge_5_easy/
 */
public class Easy {

    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.out.println("No console");
            System.exit(-1);
        }

        String username = console.readLine("username: ");
        char[] password = console.readPassword("password: ");

        String credentialsUsername = null;
        char[] credentialsPassword = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("credentials"))) {
            credentialsUsername = reader.readLine();
            List<Character> chars = new ArrayList<>();
            int ch;
            while ((ch = reader.read()) != -1) {
                chars.add((char) ch);
            }

            credentialsPassword = new char[chars.size()];
            for (int i = 0; i < chars.size(); i++) {
                credentialsPassword[i] = chars.get(i);
            }
        } catch (IOException e) {
            System.out.println("access denied");
            System.exit(0);
        }

        if (credentialsUsername != null && credentialsPassword != null
                && credentialsUsername.equals(username) 
                && Arrays.equals(credentialsPassword, password)) {
            System.out.println("access granted");
        } else {
            System.out.println("access denied");
        }
    }
}