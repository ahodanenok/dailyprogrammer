/**
 * Dailyprogrammer: 35 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/rr4y2/432012_challenge_35_easy/
 */
public class Easy {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        int rows = 1;
        while (sum(rows) <= n) rows++;
        rows--;

        int m = sum(rows);
        for (int row = rows; row > 0; row--) {
            for (int i = m - row + 1; i <= m; i++) {
                System.out.print(String.format("%2d ", i));
            }

            m -= row;
            System.out.println();
        }
    }

    private static int sum(int n) {
        return (n * (2 + n - 1)) / 2;
    }
}
