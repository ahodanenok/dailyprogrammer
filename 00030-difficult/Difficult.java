import java.util.HashMap;

/**
 * Dailyprogrammer: 30 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/reah4/3262012_challenge_30_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        System.out.println(fibonacci((long) Math.pow(10, 18), (long) Math.pow(10, 8)));
    }

    private static long fibonacci(long n, long mod) {
        HashMap<Long, Long> memo = new HashMap<Long, Long>();
        memo.put(0L, 0L);
        memo.put(1L, 1L);
        return fibonacci(n, mod, memo);
    }

    private static long fibonacci(long n, long mod, HashMap<Long, Long> memo) {        
        if (memo.containsKey(n)) return memo.get(n);
        long a = fibonacci((n + 1) / 2 - 1, mod, memo);
        long b = fibonacci((n + 1) / 2, mod, memo);
        long f = (n % 2 == 1) ? (a * a + b * b) % mod : ((2 * a + b) * b) % mod;
        memo.put(n, f);
        return f;
    }
}
