/**
 * Dailyprogrammer: 3 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pkw2m/2112012_challenge_3_easy/
 */
public class Easy {

    private static final int LOWERCASE_START = 97;
    private static final int LOWERCASE_END = 122;
    private static final int LOWERCASE_COUNT = LOWERCASE_END - LOWERCASE_START + 1;

    private static final int UPPERCASE_START = 65;
    private static final int UPPERCASE_END = 90;
    private static final int UPPERCASE_COUNT = UPPERCASE_END - UPPERCASE_START + 1;

    public static void main(String[] args) {
        if ("encode".equals(args[0])) {
            System.out.println(encode(args[1], 13));
        } else if ("decode".equals(args[0])) {
            System.out.println(decode(args[1], 13));
        } else {
            System.out.println("usage: java Easy (decode | encode) <message>");
        }
    }

    private static String encode(String message, int offset) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            if (ch >= LOWERCASE_START && ch <= LOWERCASE_END) {
                result.append(offsetLowerCaseChar(ch, offset));
            } else if (ch >= UPPERCASE_START && ch <= UPPERCASE_END) {
                result.append(offsetUpperCaseChar(ch, offset));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    private static String decode(String message, int offset) {
        return encode(message, -offset);
    }

    private static char offsetLowerCaseChar(char ch, int offset) {
        return (char) (LOWERCASE_START + ((LOWERCASE_COUNT + ch - LOWERCASE_START) + offset) % LOWERCASE_COUNT);
    }

    private static char offsetUpperCaseChar(char ch, int offset) {
        return (char) (UPPERCASE_START + ((UPPERCASE_COUNT + ch - UPPERCASE_START) + offset) % UPPERCASE_COUNT);
    }
}
