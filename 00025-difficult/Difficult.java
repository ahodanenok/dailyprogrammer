/**
 * Dailyprogrammer: 25 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/qxv8h/3152012_challenge_25_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int[][] grid = new int[n][n];
        boolean placed = placeQueens(grid, 0);

        if (placed) {
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (grid[row][col] == -1) {
                        System.out.print("Q ");
                    } else {
                        System.out.print("_ ");
                    }
                }

                System.out.println();
            }
        } else {
            System.out.println("couldn't place queens");
        }
    }

    private static boolean placeQueens(int[][] grid, int row) {
        if (row >= grid.length) {
            return true;
        }

        for (int c = 0; c < grid.length; c++) {
            int col = ((grid.length / 2) + c) % grid.length;
            if (grid[row][col] == 0) {
                for (int rr = row; rr < grid.length; rr++) grid[rr][col]++;
                for (int rr = row, cc = col; rr < grid.length && cc < grid.length; rr++, cc++) grid[rr][cc]++;
                for (int rr = row, cc = col; rr < grid.length && cc >= 0; rr++, cc--) grid[rr][cc]++;
                grid[row][col] = -1;

                boolean found = placeQueens(grid, row + 1);
                if (found) return true;

                for (int rr = row; rr < grid.length; rr++) grid[rr][col]--;
                for (int rr = row, cc = col; rr < grid.length && cc < grid.length; rr++, cc++) grid[rr][cc]--;
                for (int rr = row, cc = col; rr < grid.length && cc >= 0; rr++, cc--) grid[rr][cc]--;
                grid[row][col] = 0;
            }
        }

        return false;
    }
}
