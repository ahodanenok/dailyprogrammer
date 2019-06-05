import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Dailyprogrammer: 33 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/rl2g9/3302012_challenge_33_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        int t = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < t; i++) {
            String[] parts = scanner.nextLine().split(" ");
            int n = Integer.parseInt(parts[0]);
            int k = Integer.parseInt(parts[1]);

            List<TollBooth> booths = new ArrayList<TollBooth>();
            for (int j = 0; j < k; j++) {
                parts = scanner.nextLine().split(" ");
                booths.add(new TollBooth(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            List<Integer> path = cheapestWay(n, booths);
            System.out.println(getFee(path, booths));
            System.out.println(path);
            System.out.println();
        }
    }

    private static List<Integer> cheapestWay(int n, List<TollBooth> booths) {
        List<Integer> s = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) s.add(i);

        int minFee = Integer.MAX_VALUE;
        List<Integer> minPath = null;
        for (int i = 0; i < n; i++) {
            List<Integer> path = cheapestWay(s, i, booths);
            int pathFee = getFee(path, booths);
            if (pathFee < minFee) {
                minPath = path;
                minFee = pathFee;
            }
        }

        return minPath;
    }

    private static List cheapestWay(List<Integer> s, int b, List<TollBooth> booths) {
        if (s.size() == 1) {
            return Arrays.<Integer>asList(s.get(0));
        }

        int minFee = Integer.MAX_VALUE;
        List<Integer> minPath = null;

        for (int i = 0; i < s.size(); i++) {
            if (s.get(i) != b) {
                int a = s.remove(i);
                List<Integer> path = cheapestWay(s, a, booths);
                int pathFee = getFee(path, booths) + getFee(a, b, booths);
                if (pathFee < minFee) {
                    minPath = new ArrayList<Integer>(path);
                    minPath.add(a);
                    minFee = pathFee;
                }
                s.add(i, a);
            }
        }

        return minPath;
    }

    private static int getFee(List<Integer> path, List<TollBooth> booths) {
        int fee = 0;
        for (int i = 1; i < path.size(); i++) {
            fee += getFee(path.get(i - 1), path.get(i), booths);
        }

        return fee;
    }

    private static int getFee(int a, int b, List<TollBooth> booths) {
        int fee = 0;
        for (TollBooth tb : booths) {
            if (tb.needPayment(Math.min(a, b), Math.max(a, b))) {
                fee++;
            }
        }

        return fee;
    }

    private static class TollBooth {

        private final int x;
        private final int y;

        TollBooth(int x, int y) {
            this.x = x;
            this.y = y;
        }

        boolean needPayment(int a, int b) {
            return x >= a && y <= b;
        }
    }
}
