/**
 * Dailyprogrammer: 25 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/qxvu2/3152012_challenge_25_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println(Integer.toString(n, 2));

        String binary = "";
        do {
            binary = (n % 2) + binary;
            n /= 2;
        } while (n > 0);
        System.out.println(binary);
    }
}
