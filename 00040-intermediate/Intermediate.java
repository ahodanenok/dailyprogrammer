import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Dailyprogrammer: 40 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/schqq/4162012_challenge_40_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        List<Integer> chain = new ArrayList<Integer>();
        int next = n;
        while (!chain.contains(next)) {
            chain.add(next);
            next = k(next);
        }

        int cycleLength = chain.size() - chain.indexOf(next);
        System.out.println("Kaprekar chain: " + formatChain(chain, cycleLength));
        System.out.println("Cycle length: " + cycleLength);
    }

    private static String formatChain(List<Integer> chain, int cycleLength) {
        StringBuilder sb = new StringBuilder();
        int chainStart = chain.size() - cycleLength;
        for (int i = 0; i < chain.size(); i++) {
            if (i == chainStart) sb.append("{");
            sb.append(chain.get(i));
            sb.append(i == (chain.size() - 1) ? "}" : ", ");
        }

        return sb.toString();
    }

    private static int k(int n) {
        char[] digits = Integer.toString(n).toCharArray();
        Arrays.sort(digits);
        String digitsAscending = String.valueOf(digits);
        String digitsDescending = new StringBuilder(digitsAscending).reverse().toString();
        return Integer.parseInt(digitsDescending) - Integer.parseInt(digitsAscending);
    }
}
