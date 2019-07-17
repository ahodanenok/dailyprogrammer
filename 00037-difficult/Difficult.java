import java.util.List;
import java.util.ArrayList;

/**
 * Dailyprogrammer: 37 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/rzdjt/482012_challenge_37_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        int n = 1001;
        for (Integer prime : primes(n)) {
            if ((prime - 1) % 4 != 0) {
                continue;
            }

            int r = prime;
            int rk = quadraticResidue(prime - 1, r);
            if (rk == -1) {
                System.out.printf("Prime %d can't be represented as sum of two squares\n", prime);
                continue;
            }

            while (rk * rk > prime) {
                int c = r % rk;
                r = rk;
                rk = c;
            }

            int s = (int) Math.sqrt(prime - rk * rk);
            if (s * s + rk * rk == prime) {
                System.out.printf("%d^2 + %d^2 = %d, true\n", rk, s, prime);
            } else {
                System.out.printf("Prime %d can't be represented as sum of two squares\n", prime);
            }
        }
    }

    private static int quadraticResidue(int b, int n) {
        for (int i = 0; i < n; i++) {
            if (i * i % n == b) {
                return i;
            }
        }

        return -1;
    }

    private static List<Integer> primes(int n) {
        boolean[] sieve = new boolean[n];
        int p = 2;
        while (p < sieve.length) {
            for (int i = 2; i * p < sieve.length; i++) {
                sieve[i * p] = true;
            }

            do {
                p++;
            } while (p < sieve.length && sieve[p]);
        }

        List<Integer> primesList = new ArrayList<Integer>();
        for (int i = 2; i < sieve.length; i++) {
            if (!sieve[i]) primesList.add(i);
        }

        return primesList;
    }
}
