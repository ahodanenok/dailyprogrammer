import java.math.BigDecimal;

/**
 * Dailyprogrammer: 28 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/r59e6/3202012_challenge_28_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        BigDecimal balls = new BigDecimal(args[0]);
        BigDecimal x;

        int n = 0;
        do {
            x = t(++n);
            System.out.println(x);
        } while (x.compareTo(balls) < 0);
        System.out.println(x.subtract(t(n - 1)));
    }

    private static final BigDecimal t(int n) {
        return BigDecimal.valueOf(n)
            .multiply(BigDecimal.valueOf(n + 1))
            .multiply(BigDecimal.valueOf(n + 2))
            .divide(BigDecimal.valueOf(6));
    }
}
