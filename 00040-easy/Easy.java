/**
 * Dailyprogrammer: 40 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/schtf/4162012_challenge_40_easy/
 */
public class Easy {

    public static void main(String[] args) {
        printNumbers(1, 1001);

        // java 8 has tail call optimization, so this will be valid
        // printNumbers(1, 1001, 1);
    }

    private static void printNumbers(int from, int to) {
        try {
            int r = 1 / (to / (from + 1));
        } catch (Exception e) {
            return;
        }

        printNumbers(from, from + 100, from);
        printNumbers(from + 100, to);
    }

    private static void printNumbers(int from, int to, int n) {
        try {
            int r = 1 / (to - n);
        } catch (Exception e) {
            return;
        }

        System.out.println(n);
        printNumbers(from, to, n + 1);
    }
}
