/**
 * Dailyprogrammer: 16 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/q8aom/2272012_challenge_16_easy/
 */
public class Easy {

    public static void main(String[] args) {
        System.out.println(removeCharacters(args[0], args[1]));
    }

    private static String removeCharacters(String str, String blacklist) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (blacklist.indexOf(str.charAt(i)) < 0) {
                sb.append(str.charAt(i));
            }
        }

        return sb.toString();
    }
}
