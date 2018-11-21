import java.util.Arrays;

/**
 * Dailyprogrammer: 21 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/qp3ub/392012_challenge_21_easy/
 */
public class Easy {

    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);
        char[] digits = Long.toString(n).toCharArray();
        Arrays.sort(digits);

        long next = n;
        boolean found = false;
        while (true) {
            next++;
            char[] nextDigits = Long.toString(next).toCharArray();
            Arrays.sort(nextDigits);

            if (nextDigits.length != digits.length) {
                found = false;
                break;
            }

            if (Arrays.equals(digits, nextDigits) && next > n) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("The next higher number that uses the same set of digits: " + next);
        } else {
            System.out.println("Couldn't find the next higher number that uses the same set of digits");
        }
    }
}
