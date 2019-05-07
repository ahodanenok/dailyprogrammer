import java.util.HashSet;

/**
 * Dailyprogrammer: 28 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/r59kk/3202012_challenge_28_easy/
 */
public class Easy {

    public static void main(String[] args) {
        int[] numbers = new int[] { 3, 5, 8, 100, 14, 8, 2, 1 };

        HashSet<Integer> seen = new HashSet<Integer>();
        for (int i = 0; i < numbers.length; i++) {
            if (seen.contains(numbers[i])) {
                System.out.println(numbers[i]);
                break;
            }

            seen.add(numbers[i]);
        }
    }
}
