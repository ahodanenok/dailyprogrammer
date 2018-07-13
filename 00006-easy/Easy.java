import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Dailyprogrammer: 6 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pp53w/2142012_challenge_6_easy/
 */
public class Easy {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Provide number of iterations and precision");
            System.exit(-1);
        }

        int precision;
        int iterations;
        if (args.length == 1) {
            precision = 30;
            iterations = Integer.parseInt(args[0]);
        } else {
            precision = Integer.parseInt(args[0]);
            iterations = Integer.parseInt(args[1]);
        }

        System.out.println(calculatePI(precision, iterations));
    }

    private static BigDecimal calculatePI(int precision, int iterations) {
        MathContext mc = new MathContext(precision);
        BigDecimal pi = new BigDecimal(0, mc);
        BigDecimal num = new BigDecimal(4, mc);
        BigDecimal denom = new BigDecimal(1, mc);
        BigDecimal two = new BigDecimal(2, mc);
        boolean add = true;

        for (int i = 0; i < iterations; i++) {
            if (add) {
                pi = pi.add(num.divide(denom, mc), mc);
            } else {
                pi = pi.subtract(num.divide(denom, mc), mc);
            }

            denom = denom.add(two, mc);
            add = !add;
        }

        return pi;
    }
}