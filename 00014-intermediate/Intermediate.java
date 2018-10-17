import java.util.Arrays;

/**
 * Dailyprogrammer: 14 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/q2mwu/2232012_challenge_14_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        boolean[] sieve = new boolean[(n - 1) / 2];
        Arrays.fill(sieve, true);

        for (int i = 1; i < sieve.length; i++) {
            for (int j = i; j < (sieve.length - i) / (2 * i + 1); j++) {
                sieve[i + j + 2 * i * j - 1] = false;
            }
        }

        for (int i = 0; i < sieve.length; i++) {
            if (sieve[i]) {
                int p = (i + 1) * 2 + 1;
                System.out.println(p);
            }
        }
    }
}
