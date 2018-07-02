/**
 * Dailyprogrammer: 4 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pm6oj/2122012_challenge_4_easy/
 */
public class Easy {

    public static void main(String[] args) {
        int count = 10;
        if (args.length > 0) {
            count = Integer.parseInt(args[0]);
        }

        if (count < 0) {
            System.out.println("Count can't be negative");
            System.exit(-1);
        }

        int length = 10;
        if (args.length > 1) {
            length = Integer.parseInt(args[1]);
        }

        if (length < 0) {
            System.out.println("Length can't be negative");
            System.exit(-1);
        }

        for (int i = 0; i < count; i++) {
            System.out.println(generatePassword(length));
        }
    }

    private static String generatePassword(int length) {
        StringBuilder pwd = new StringBuilder();
        for (int i = 0; i < length; i++) {
            pwd.append((char) Math.floor(97 + Math.random() * 26));
        }

        return pwd.toString();
    }
}