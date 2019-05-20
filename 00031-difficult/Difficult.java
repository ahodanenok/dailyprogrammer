import java.util.Arrays;

/**
 * Dailyprogrammer: 31 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/rg2dj/3272012_challenge_31_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(maxSubArray(new int[] { 1, 2, -5, 4, -3, 2 })));
        System.out.println(Arrays.toString(maxSubArray(new int[] { -1, -2, -3 })));
        System.out.println(Arrays.toString(maxSubArray(new int[] { 1, 2, 3, -4, 5, -20 })));
        System.out.println(Arrays.toString(maxSubArray(new int[] { 1, 2, -5, 4, -1, 100 })));
        System.out.println(Arrays.toString(maxSubArray(new int[] { 1, 2, -5, 3, -1, 1, -1, -1, 100 })));
        System.out.println(Arrays.toString(maxSubArray(new int[] { 1, 2, -5, 3, -1, 1, -1, -1, 100, -1 })));
    }

    private static int[] maxSubArray(int[] numbers) {
        int currStart = 0;
        int sum = 0;

        int maxStart = 0;
        int maxEnd = 0;
        int max = 0;

        for (int i = 0; i < numbers.length; i++) {
            int n = numbers[i];
            if (n < 0) {
                if (sum > max) {
                    max = sum;
                    maxStart = currStart;
                    maxEnd = i;
                }

                sum += n;
                if (sum < 0) {
                    sum = 0;
                    currStart = i + 1;
                }
            } else {
                sum += n;
            }
        }

        if (sum > max) {
            maxStart = currStart;
            maxEnd = numbers.length;
        }

        return Arrays.copyOfRange(numbers, maxStart, maxEnd);
    }
}
