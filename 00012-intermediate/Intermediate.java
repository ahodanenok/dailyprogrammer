import java.util.List;
import java.util.ArrayList;

/**
 * Dailyprogrammer: 12 - intermedite
 * https://www.reddit.com/r/dailyprogrammer/comments/pxrzh/2202012_challenge_12_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        List<Integer> factors = factor(n);

        StringBuilder sb = new StringBuilder();
        sb.append(n).append(" = ");
        for (int i = 0; i < factors.size(); i++) {
            sb.append(factors.get(i));
            if (i != factors.size() - 1) {
                sb.append(" * ");
            }
        }

        System.out.println(sb);
    }

    private static List<Integer> factor(int n) {
        int divider = 2;
        int stop = (int) Math.ceil(Math.sqrt(n));
        List<Integer> factors = new ArrayList<Integer>();

        while (divider < stop) {
            while (n % divider == 0) {
                factors.add(divider);
                n /= divider;
            }

            divider++;
        }

        if (factors.size() > 1 && n != 1 || factors.size() <= 1) {
            factors.add(n);
        }

        return factors;
    }
}
