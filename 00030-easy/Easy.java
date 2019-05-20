import java.util.Arrays;
import java.util.Random;

/**
 * Dailyprogrammer: 30 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/reago/3262012_challenge_30_easy/
 */
public class Easy {

    public static void main(String[] args) {
        int[] numbers = new int[Integer.parseInt(args[0])];
        int target = Integer.parseInt(args[1]);

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 1 + random.nextInt(numbers.length);
        }

        Arrays.sort(numbers);
        boolean found = false;
        for (int i = 0; i < numbers.length - 1; i++) {
            if (Arrays.binarySearch(numbers, i + 1, numbers.length - 1, target - numbers[i]) >= 0) {
                System.out.println(String.format("%d + %d = %d", numbers[i], target - numbers[i], target));
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Not found");
        }
    }
}
