import java.util.Scanner;

/**
 * Dailyprogrammer: 1 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pii6j/difficult_challenge_1/
 */
public class Difficult {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int lo = 1;
        int hi = 100;
        while (true) {
            int guess = random(lo, hi);
            System.out.println(guess + ": is it equal(eq), lower(lo) or higher(hi) ?");
            String answer = scanner.next();
            if ("eq".equals(answer)) {
                System.out.println("Guessed number is " + guess);
                break;
            } else if ("lo".equals(answer)) {
                hi = lo + (hi - lo) / 2;
            } else if ("hi".equals(answer)) {
                lo = lo + (hi - lo) / 2;
            } else {
                throw new IllegalArgumentException("Unknown answer: " + answer);
            }
        }
    }

    private static int random(int from, int to) {
        return from + (int) Math.floor(Math.random() * (to - from));
    }
}
