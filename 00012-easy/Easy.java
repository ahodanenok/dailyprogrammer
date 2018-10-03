/**
 * Dailyprogrammer: 12 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pxs2x/2202012_challenge_12_easy/
 */
public class Easy {

    public static void main(String[] args) {
        printPermutations(args[0], "");
    }

    private static void printPermutations(String remainingStr, String currentStr) {
        if (remainingStr.length() == 0) {
            System.out.println(currentStr);
            return;
        }

        for (int i = 0; i < remainingStr.length(); i++) {
            printPermutations(remainingStr.substring(0, i) + remainingStr.substring(i + 1), currentStr + remainingStr.charAt(i));
        }
    }
}
