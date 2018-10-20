/**
 * Dailyprogrammer: 16 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/q8fqk/2272012_challenge_16_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int games = Integer.parseInt(args[0]);

        int lossCount = 0;
        int winCount = 0;
        int maxRoll = Integer.MIN_VALUE;
        int minRoll = Integer.MAX_VALUE;
        int[] rollCounts = new int[13];
        int totalRollCount = 0;

        for (int n = 0; n < games; n++) {
            int roll = throwDices();
            totalRollCount++;
            maxRoll = Math.max(roll, maxRoll);
            minRoll = Math.min(roll, minRoll);
            rollCounts[roll]++;

            if (roll == 2 || roll == 3 || roll == 12) {
                lossCount++;
            } else if (roll == 7 || roll == 11) {
                winCount++;
            } else {
                while (true) {
                    int roll2 = throwDices();
                    totalRollCount++;
                    maxRoll = Math.max(roll2, maxRoll);
                    minRoll = Math.min(roll2, minRoll);
                    rollCounts[roll2]++;

                    if (roll2 == 7) {
                        lossCount++;
                        break;
                    } else if (roll2 == roll) {
                        winCount++;
                        break;
                    }
                }
            }
        }

        if (games == 0) return;

        System.out.println(String.format("Win count: %d", winCount));
        System.out.println(String.format("Loss count: %d", lossCount));
        System.out.println(String.format("Maximum roll: %d", minRoll));
        System.out.println(String.format("Maximum roll: %d", maxRoll));

        int mostCommonRoll = 1;
        int leastCommonRoll = 1;
        for (int r = 1; r < rollCounts.length; r++) {
            if (rollCounts[r] > rollCounts[mostCommonRoll]) mostCommonRoll = r;
            if (rollCounts[r] < rollCounts[leastCommonRoll]) leastCommonRoll = r;
        }
        System.out.println(String.format("Least common roll: %d", leastCommonRoll));
        System.out.println(String.format("Most common roll: %d", mostCommonRoll));

        System.out.println("Rolls occurrency:");
        for (int r = 1; r < rollCounts.length; r++) {
            System.out.println(String.format("%d - %f", r, rollCounts[r] / (double) totalRollCount));
        }
    }

    private static int throwDices() {
        return (int) Math.floor(Math.random() * 12 + 1);
    }
}
