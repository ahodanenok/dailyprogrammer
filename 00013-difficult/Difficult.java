import java.util.Random;

/**
 * Dailyprogrammer: 13 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pzobz/2212012_challenge_13_difficult/
 */
public class Difficult {

    private static final boolean DEBUG = false;

    private static Figure[][] MOVES = { 
        { Figure.ROCK,     Figure.PAPER,    Figure.SCISSORS }, // move
        { Figure.SCISSORS, Figure.ROCK,     Figure.PAPER },    // loss
        { Figure.PAPER,    Figure.SCISSORS, Figure.ROCK }      // victory
    };

    private enum Figure {
        ROCK, PAPER, SCISSORS;
    }

    public static void main(String[] args) {
        int games = Integer.parseInt(args[0]);

        double ai_1_winChance = 0.53;
        double ai_2_winChance = 0.13;
        double drawChance = 0.33;

        if (ai_1_winChance + ai_2_winChance + drawChance < 0 
                || ai_1_winChance + ai_2_winChance + drawChance >= 1) {
            throw new IllegalArgumentException(
                "'ai_1_winChance + ai_2_winChance + drawChance' must be in range [0, 1)");
        }

        Random moveRandom = new Random(System.currentTimeMillis() % 9000);
        Random resultRandom = new Random(System.currentTimeMillis());
        int ai_1_wonCount = 0;
        int ai_2_wonCount = 0;
        int drawsCount = 0;
        for (int n = 0; n < games; n++) {
            int idx = moveRandom.nextInt(MOVES[0].length);
            Figure move = MOVES[0][idx];
            if (DEBUG) System.out.println("ai1: " + move);
            double result = resultRandom.nextDouble();
            if (result >= 0 && result < ai_1_winChance) {
                if (DEBUG) System.out.println("ai2: " + MOVES[1][idx]);
                if (DEBUG) System.out.println("ai1 Won");
                ai_1_wonCount++;
            } else if (result >= ai_1_winChance && result < ai_1_winChance + ai_2_winChance) {
                if (DEBUG) System.out.println("ai2: " + MOVES[2][idx]);
                if (DEBUG) System.out.println("ai2 Won");
                ai_2_wonCount++;
            } else if (result >= ai_1_winChance + ai_2_winChance && result < 1) {
                if (DEBUG) System.out.println("ai2: " + move);
                if (DEBUG) System.out.println("Draw");
                drawsCount++;
            } else {
                throw new IllegalStateException("Result > 1: " + result);
            }

            if (DEBUG) System.out.println();
        }

        System.out.println(String.format(
            "Games: %d, ai1 Won: %d, ai2 Won: %d, Draws: %d",
            games, ai_1_wonCount, ai_2_wonCount, drawsCount));
    }
}
