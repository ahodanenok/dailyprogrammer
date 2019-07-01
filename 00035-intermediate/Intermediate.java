import java.util.Random;
import java.util.HashMap;

/**
 * Dailyprogrammer: 35 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/rr526/432012_challenge_35_intermediate/
 */
public class Intermediate {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        HashMap<Integer, Integer> frequency = new HashMap<Integer, Integer>();
        for (int i = 0; i < 100_000_000; i++) {
            int value = random(n);
            if (value >= n) throw new IllegalStateException(value + "");
            if (frequency.containsKey(value)) {
                frequency.put(value, frequency.get(value) + 1);
            } else {
                frequency.put(value, 1);
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.println(String.format("%3d - %d", 
                i, frequency.containsKey(i) ? frequency.get(i) : 0));
        }
    }

    public static int flip() {
        return RANDOM.nextInt(2);
    }

    private static int random(int n) {
        int result = 0;
        int p = 32 - Integer.numberOfLeadingZeros(n - 1); //ceil(log2(n))
        for (int i = 0; i < p; i++) {
            if (result + (1 << i) < n) {
                result += (1 << i) * flip();
            }
        }

        return result;
    }
}
