/**
 * Dailyprogrammer: 34 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/rmmn8/3312012_challenge_34_easy/
 */
public class Easy {

    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int c = Integer.parseInt(args[2]);

        if (a < b) {
            System.out.println(a < c ? (b * b + c * c) : (a * a + b * b));
        } else {
            System.out.println(b < c ? (a * a + c * c) : (a * a + b * b));
        }
    }
}
