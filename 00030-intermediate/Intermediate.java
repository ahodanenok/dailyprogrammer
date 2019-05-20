/**
 * Dailyprogrammer: 30 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/red6f/3262012_challenge_30_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        System.out.println(isValid(49927398716L));
        System.out.println(calculateCheckDigit(4992739871L));
    }

    private static boolean isValid(long num) {
        return (luhnSum(num / 10) + (num % 10)) % 10 == 0;
    }

    private static long calculateCheckDigit(long num) {
        long sum = luhnSum(num);
        return (sum / 10 + 1) * 10 - sum;
    }

    private static long luhnSum(long num) {
        long n = num;
        long sum = 0;
        boolean alt = true;
        while (n > 0) {
            if (alt) {
                long d = (n % 10) * 2;
                sum += d / 10;
                sum += d % 10;
            } else {
                sum += (n % 10);
            }

            n /= 10;
            alt = !alt;
        }

        return sum;
    }
}
