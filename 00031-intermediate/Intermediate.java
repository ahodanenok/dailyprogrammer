import java.io.FileReader;
import java.io.BufferedReader;

import java.util.HashMap;

/**
 * Dailyprogrammer: 31 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/rg25w/3272012_challenge_31_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("b005245.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            int num = Integer.parseInt(parts[0].trim());
            int expected = Integer.parseInt(parts[1].trim());
            int actual = c(num);
            if (actual != expected) {
                System.out.println(String.format("c(%d)=%2d, expected=%2d FAIL", num, actual, expected));
            } else {
                System.out.println(String.format("c(%d)=%2d OK", num, actual));
            }
        }
    }

    private static HashMap<Integer, Integer> COMPLEXITIES = new HashMap<Integer, Integer>();
    static {
        COMPLEXITIES.put(0, 0);
        COMPLEXITIES.put(1, 1);
        COMPLEXITIES.put(2, 2);
        COMPLEXITIES.put(3, 3);
        COMPLEXITIES.put(5, 5);
    }

    private static int c(int n) {
        if (COMPLEXITIES.containsKey(n)) {
            return COMPLEXITIES.get(n);
        }

        int result = Integer.MAX_VALUE;
        for (int s = 1; s < n / 2; s++) {
            result = Math.min(c(n - s) + c(s), result);
        }

        for (int d = 2; d < n / 2; d++) {
            if (n % d == 0) {
                result = Math.min(c(n / d) + c(d), result);
            }
        }

        COMPLEXITIES.put(n, result);
        return result;
    }
}
