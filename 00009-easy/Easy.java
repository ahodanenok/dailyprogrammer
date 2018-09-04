import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Dailyprogrammer: 9 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pu1rf/2172012_challenge_9_easy/
 */
public class Easy {

    private static final String SORT_INT = "integers";
    private static final String SORT_STR = "strings";

    private static final String SORT_CMD = ":sort";
    private static final String RESET_CMD = ":reset";
    private static final String EXIT_CMD = ":exit";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        boolean numbers;
        if (SORT_INT.equals(args[0])) {
            numbers = true;
        } else if (SORT_STR.equals(args[0])) {
            numbers = false;
        } else {
            throw new IllegalArgumentException(args[0]);
        }

        Scanner in = new Scanner(System.in);
        List<Comparable> values = new ArrayList<Comparable>();
        while (true) {
            String str = in.next();
            if (SORT_CMD.equals(str)) {
                Collections.sort(values);
                System.out.println(values);
            } else if (RESET_CMD.equals(str)) {
                values = new ArrayList<Comparable>();
            } else if (EXIT_CMD.equals(str)) {
                break;
            } else {
                if (numbers) {
                    values.add(Integer.parseInt(str));
                } else {
                    values.add(str);
                }
            }
        }
    }
}
