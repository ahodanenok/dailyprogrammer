/**
 * Dailyprogrammer: 39 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/s6bas/4122012_challenge_39_easy/
 */
public class Easy {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }
}
