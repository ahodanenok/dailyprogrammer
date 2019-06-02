/**
 * Dailyprogrammer: 32 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/rhs8i/3282012_challenge_32_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        System.out.println(hanoi(4, "1", "2", "3"));
    }

    private static int hanoi(int n, String from, String to, String spare) {
        if (n > 0) {
            int steps = 1;
            steps += hanoi(n - 1, from, spare, to);
            System.out.println(String.format("%s -> %s", from, to));
            steps += hanoi(n - 1, spare, to, from);
            return steps;
        } else {
            return 0;
        }
    }
}
