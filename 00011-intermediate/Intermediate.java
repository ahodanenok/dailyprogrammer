/**
 * Dailyprogrammer: 11 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/pwox3/2192012_challenge_11_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        //System.out.println(isUpsideDown(Integer.parseInt(args[0])));

        int gt1961 = -1;
        int count = 0;
        for (int n = 0; n < 10000; n++) {
            if (isUpsideDown(n)) {
                count++;
                if (gt1961 == -1 && n > 1961) {
                   gt1961 = n;
                }
            }
        }

        System.out.println("The next upside down number after 1961 is " + gt1961);
        System.out.println("The number of upside down numbers less than 10000 is " + count);
    }

    private static boolean isUpsideDown(int n) {
        int p = 1;
        while (p <= n) p *= 10;
        for (int a = p / 10, b = 1; a >= b; a /= 10, b *= 10) {
            int ds = (n / a) % 10;
            int de = (n % (b * 10)) / b;
            //System.out.println(String.format("a=%d, ds=%d, b=%d, de=%d", a, ds, b, de));
            if (!(ds == 0 && de == 0
                    || ds == 1 && de == 1
                    || ds == 2 && de == 5
                    || ds == 5 && de == 2
                    || ds == 6 && de == 9
                    || ds == 9 && de == 6
                    || ds == 8 && de == 8)) {
                return false;
            }
        }

        return true;
    }
}
