import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Dailyprogrammer: 23 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/quli5/3132012_challenge_23_easy/
 */
public class Easy {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        Random random = new Random(System.currentTimeMillis());
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) list.add(random.nextInt(100));
        System.out.println("Original list: " + list);

        int middle = (n + 1) / 2;

        List<Integer> firstHalf = new ArrayList<Integer>(middle);
        for (int i = 0; i < middle; i++) firstHalf.add(list.get(i));
        System.out.println("First half: " + firstHalf);

        List<Integer> secondHalf = new ArrayList<Integer>(n - middle);
        for (int i = middle; i < n; i++) secondHalf.add(list.get(i));
        System.out.println("Second half: " + secondHalf);
    }
}
