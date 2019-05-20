import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Dailyprogrammer: 31 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/rg1vv/3272012_challenge_31_easy/
 */
public class Easy {

    private static final BigDecimal NUM_26 = BigDecimal.valueOf(26);

    public static void main(String[] args) {
        String a = args[0];
        String b = args[1];
        System.out.println(String.format("%s * %s = %s", a, b, multiply(a, b)));
    }

    private static String multiply(String a, String b) {
        return toBase26(toBase10(a).multiply(toBase10(b)));
    }

    private static BigDecimal toBase10(String n) {
        BigDecimal p = BigDecimal.ONE;
        BigDecimal result = BigDecimal.ZERO;
        for (int i = n.length() - 1; i >= 0; i--) {
            if (n.charAt(i) < 'A' && n.charAt(i) > 'Z') throw new IllegalArgumentException("number format: A..Z");
            BigDecimal digit = BigDecimal.valueOf((int) n.charAt(i) - 65);
            result = result.add(digit.multiply(p));
            p = p.multiply(NUM_26);
        }

        return result;
    }

    private static String toBase26(BigDecimal n) {
        StringBuilder result = new StringBuilder();
        BigDecimal num = n;
        while (num.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] dr = num.divideAndRemainder(NUM_26);
            result.append((char) (65 + dr[1].intValue()));
            num = dr[0];
        }

        return result.reverse().toString();
    }
}
