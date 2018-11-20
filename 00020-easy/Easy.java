/**
 * Dailyprogrammer: 20 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/qnkro/382012_challenge_20_easy/
 */
public class Easy {

    public static void main(String[] args) {
        int below = 2000;
        System.out.println(String.format("Primes below %d:", below));
        next:
        for (int n = 2; n < below; n++) {
            for (int k = 2; k <= Math.sqrt(n); k++) {
                if (n != k && n % k == 0) {
                    continue next;
                }
            }

            System.out.println("  " + n);
        }
    }
}