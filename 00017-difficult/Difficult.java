import java.util.Scanner;
import java.util.Random;

/**
 * Dailyprogrammer: 17 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/qhe2i/342012_challenge_17_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        Game game = new Game();
        Player[] players = new Player[] { new HumanPlayer("1", State.CROSS), new RandomAIPlayer("AI", State.OVAL) };

        loop:
        while (true) {
            State winner = null;
            for (Player p : players) {
                p.move(game);
                game.print();
                System.out.println();

                winner = game.winner();
                if (winner != null) {
                    break;
                }

                if (game.isAllFilled()) {
                    System.out.println("Draw");
                    break loop;
                }
            }

            if (winner != null) {
                for (Player p : players) {
                    if (p.state == winner) {
                        System.out.println("Player " + p.id + " has won");
                        break loop;
                    }
                }
            }
        }
    }

    abstract static class Player {

        protected String id;
        protected State state;

        Player(String id, State state) {
            this.id = id;
            this.state = state;
        }

        abstract void move(Game game);
    }

    static class HumanPlayer extends Player {

        private Scanner scanner;

        HumanPlayer(String id, State state) {
            super(id, state);
            this.scanner = new Scanner(System.in);
        }

        @Override
        public void move(Game game) {
            System.out.println("Player " + this.id + ", your move: ");
            String str = scanner.nextLine();
            String[] parts = str.split(" ");
            game.fill(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), state);
        }
    }

    static class RandomAIPlayer extends Player {

        private Random random;

        RandomAIPlayer(String id, State state) {
            super(id, state);
            this.random = new Random(System.currentTimeMillis());
        }

        @Override
        public void move(Game game) {
            while (true) {
                int x = random.nextInt(Game.SIZE);
                int y = random.nextInt(Game.SIZE);
                if (!game.isFilled(x, y)) {
                    System.out.println("Player " + id + " moves: " + x + ", " + y);
                    game.fill(x, y, state);
                    break;
                }
            }
        }
    }

    enum State {
        OVAL, CROSS;
    }

    static class Game {

        static final int SIZE = 3;

        private State[][] grid;
        private State lastMove;

        Game() {
            this.grid = new State[SIZE][SIZE];
        }

        void fill(int x, int y, State state) {
            if (grid[y][x] != null || lastMove == state) {
                throw new IllegalStateException();
            }

            grid[y][x] = state;
            lastMove = state;
        }

        void print() {
            for (int y = 0; y < SIZE; y++) {
                for (int x = 0; x < SIZE; x++) {
                    if (grid[y][x] == State.CROSS) {
                        System.out.print("x");
                    } else if (grid[y][x] == State.OVAL) {
                        System.out.print("o");
                    } else if (grid[y][x] == null) {
                        System.out.print("_");
                    }

                    if (x != SIZE - 1) {
                        System.out.print(" ");
                    }
                }

                System.out.println();
            }
        }

        boolean isFilled(int x, int y) {
            return grid[y][x] != null;
        }

        boolean isAllFilled() {
            for (int y = 0; y < SIZE; y++) {
                for (int x = 0; x < SIZE; x++) {
                    if (grid[y][x] == null) {
                        return false;
                    }
                }
            }

            return true;
        }

        State winner() {
            for (int y = 0; y < SIZE; y++) {
                int sameCount = 1;
                for (int x = 1; x < SIZE; x++) {
                    if (grid[y][x - 1] == grid[y][x]) {
                        sameCount++;
                    } else {
                        break;
                    }
                }

                if (sameCount == SIZE && grid[y][0] != null) {
                    return grid[y][0];
                }
            }

            for (int x = 0; x < SIZE; x++) {
                int sameCount = 1;
                for (int y = 1; y < SIZE; y++) {
                    if (grid[y - 1][x] == grid[y][x]) {
                        sameCount++;
                    } else {
                        break;
                    }
                }

                if (sameCount == SIZE && grid[0][x] != null) {
                    return grid[0][x];
                }
            };

            {
                int sameCount = 1;
                for (int y = 1; y < SIZE; y++) {
                    if (grid[y - 1][y - 1] == grid[y][y]) {
                        sameCount++;
                    } else {
                        break;
                    }

                    if (sameCount == SIZE && grid[0][0] != null) {
                        return grid[0][0];
                    }
                }
            }

            {
                int sameCount = 1;
                for (int y = 1, x = SIZE - 2; y < SIZE && x >= 0; y++, x--) {
                    if (grid[y - 1][x + 1] == grid[y][x]) {
                        sameCount++;
                    } else {
                        break;
                    }

                    if (sameCount == SIZE && grid[0][SIZE - 1] != null) {
                        return grid[0][SIZE - 1];
                    }
                }
            }

            return null;
        }
    }
}
