/**
 * Dailyprogrammer: 39 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/s6be0/4122012_challenge_39_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int i = 1; i <= n; i++) {
            if (isKaprekar(i)) {
                System.out.println(i);
            }
        }
    }

    private static boolean isKaprekar(int n) {
        int s = n * n;
        String nd = Integer.toString(n);
        String sd = Integer.toString(s);

        int splitIdx = sd.length() - nd.length();
        int k = Integer.parseInt(sd.substring(splitIdx));
        if (splitIdx > 0) k += Integer.parseInt(sd.substring(0, splitIdx));

        return k == n;
    }
}
