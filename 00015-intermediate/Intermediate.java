import java.util.Arrays;
import java.util.Random;

/**
 * Dailyprogrammer: 15 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/q4bk1/2242012_challenge_15_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int width = 30;
        int height = 30;
        int times = 1000;
        int rings = 50;

        int emptyCells = 0;
        for (int t = 0; t < times; t++) {
            Random random = new Random(System.currentTimeMillis());
            int[][] fleas = new int[width][height];
            for (int w = 0; w < width; w++) Arrays.fill(fleas[w], 1);
            for (int r = 0; r < rings; r++) jump(fleas, width, height, random);

            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    if (fleas[w][h] == 0) {
                        emptyCells++;
                    }
                }
            }
        }

        System.out.println(String.format("%.2f", emptyCells / (double) times));
    }

    private static void jump(int[][] fleas, int width, int height, Random random) {
        int[][] snapshot = new int[width][height];
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                snapshot[w][h] = fleas[w][h];
            }
        }

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                if (snapshot[w][h] == 0) continue;

                for (int f = 0; f < snapshot[w][h]; f++) {
                    int nw = w;
                    int nh = h;
                    while (true) {
                        int dest = random.nextInt(4);
                        if (dest == 0 && w > 0) {
                            nw = w - 1;
                            break;
                        } else if (dest == 1 && w < width - 1) {
                            nw = w + 1;
                            break;
                        } else if (dest == 2 && h > 0) {
                            nh = h - 1;
                            break;
                        } else if (dest == 3 && h < height - 1) {
                            nh = h + 1;
                            break;
                        }
                    }

                    fleas[w][h]--;
                    fleas[nw][nh]++;
                }
            }
        }
    }
}
