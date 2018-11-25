/**
 * Dailyprogrammer: 24 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/qx025/3142012_challenge_24_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int k = 0; k < n; k++) {
            double a = 2 * Math.pow(2, k) * Math.pow(fact(k), 2);
            double b = fact(2 * k + 1);
            System.out.println(String.format("%f/%f", a, b));
        }
    }

    private static double fact(int n) {
        double f = 1;
        for (int i = 2; i <= n; i++) {
            f *= i;
        }

        return f;
    }
}
