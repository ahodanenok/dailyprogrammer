import java.util.List;
import java.util.ArrayList;

/**
 * Dailyprogrammer: 22 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/qr0hg/3102012_challenge_22_easy/
 */
public class Easy {

    public static void main(String[] args) {
        List<Object> list_1 = new ArrayList<Object>();
        list_1.add("a");
        list_1.add("b");
        list_1.add("c");
        list_1.add(1);
        list_1.add(4);

        List<Object> list_2 = new ArrayList<Object>();
        list_2.add("a");
        list_2.add("x");
        list_2.add("34");
        list_2.add("4");

        List<Object> appendObjects = new ArrayList<Object>();
        for (Object obj : list_2) {
            if (list_1.indexOf(obj) < 0) {
                appendObjects.add(obj);
            }
        }

        list_1.addAll(appendObjects);
        System.out.println(list_1);
    }
}
