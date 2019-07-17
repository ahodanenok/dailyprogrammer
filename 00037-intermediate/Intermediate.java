/**
 * Dailyprogrammer: 37 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/rzdft/482012_challenge_37_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int i = 1; i <= n; i++) {
            printTriangle(i);
            System.out.println();
        }
    }

    private static void printTriangle(int n) {
        int size = 1 << (n - 1);
        boolean[][] grid = new boolean[size][size + size - 1];
        triangle(n, 0, 0, size + size - 1, size, grid);
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col]) {
                    System.out.print('X');
                } else {
                    System.out.print(' ');
                }
            }

            System.out.println();
        }
    }

    private static void triangle(int n, int row, int col, int width, int height, boolean[][] grid) {
        if (n == 1) {
            grid[row][col] = true;
        } else {
            int hw = width / 2;
            int hh = height / 2;
            triangle(n - 1, row, col + (hw + 1) / 2, hw, hh, grid); // top
            triangle(n - 1, row + hh, col, hw, hh, grid);           // left
            triangle(n - 1, row + hh, col + hw + 1, hw, hh, grid);  // right
        }
    }
}
