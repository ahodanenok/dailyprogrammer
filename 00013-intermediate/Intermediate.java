import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Dailyprogrammer: 13 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/pzo7g/2212012_challenge_13_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) throws IOException {
        write(reverse(args[0]), "intermediate.txt");
    }

    private static String reverse(String str) {
        String reversed = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed += str.charAt(i);
        }

        return reversed;
    }

    private static void write(String str, String fileName) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(str);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
