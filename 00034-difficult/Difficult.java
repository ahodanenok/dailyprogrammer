import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Dailyprogrammer: 34 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/rmmrq/3312012_challenge_34_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        solve(Integer.parseInt(args[0]));
    }

    private static void solve(int n) {
        TrianglePuzzle minPuzzle = null;
        List<Location> notSolvedInitialEmptyLocations = new ArrayList<Location>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < row + 1; col++) {
                Location empty = new Location(row, col);
                TrianglePuzzle result = solve(new TrianglePuzzle(n, empty));
                if (result != null && (minPuzzle == null || result.steps().size() < minPuzzle.steps().size())) {
                    minPuzzle = result;
                }

                if (result == null) {
                    notSolvedInitialEmptyLocations.add(empty);
                }
            }
        }

        if (notSolvedInitialEmptyLocations.size() > 0) {
            System.out.println("Puzzle can't be solved when the following locations are initially empty:");
            for (Location loc : notSolvedInitialEmptyLocations) {
                System.out.println("  " + loc);
            }
        } else {
            System.out.println("Puzzle solvability doesn't depend on which cell is initially empty.");
        }

        System.out.println("Minimal number of steps to solved puzzle: " + minPuzzle.steps().size());
        System.out.println("Moves:");

        TrianglePuzzle replayPuzzle = new TrianglePuzzle(n, minPuzzle.initialEmpty);
        replayPuzzle.print();
        System.out.println();

        for (Move m : minPuzzle.steps()) {
            replayPuzzle = replayPuzzle.move(m);

            replayPuzzle.print();
            System.out.println(m);
            System.out.println();
        }
    }

    private static TrianglePuzzle solve(TrianglePuzzle puzzle) {
        List<Move> availableMoves = puzzle.availableMoves();
        if (availableMoves.size() == 0) {
            return puzzle.solved() ? puzzle : null;
        }

        TrianglePuzzle minPuzzle = null;
        for (Move m : availableMoves) {
            TrianglePuzzle result = solve(puzzle.move(m));
            if (result != null && (minPuzzle == null || result.steps().size() < minPuzzle.steps().size())) {
                minPuzzle = result;
            }
        }

        return minPuzzle;
    }

    private static class Location {

        private final int row;
        private final int col;

        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", row, col);
        }
    }

    private static class Move {

        private final Location from;
        private final Location to;

        Move(Location from, Location to) {
            this.from = from;
            this.to = to;
        }

        Location through() {
            int row;
            if (from.row != to.row) {
                row = from.row + to.row - from.row + (from.row < to.row ?  -1 : 1);
            } else {
                row = from.row;
            }

            int col;
            if (from.col != to.col) {
                col = from.col + to.col - from.col + (from.col < to.col ?  -1 : 1);
            } else {
                col = to.col;
            }

            return new Location(row, col);
        }

        @Override
        public String toString() {
            return String.format("%s -> %s", from, to);
        }
    }

    private static class TrianglePuzzle {

        private static final int[][] OFFSETS = new int[][] {
            {0, -2}, {0, 2}, {-2, 0}, {2, 0},
            {-2, -2}, {2, -2}, {-2, 2}, {2, 2}
        };

        private int tees;
        private List<List<Boolean>> rows;
        private LinkedList<Move> moves;
        private Location initialEmpty;

        private TrianglePuzzle(TrianglePuzzle puzzle) {
            this.tees = puzzle.tees;
            this.rows = new ArrayList<List<Boolean>>(puzzle.rows.size());
            for (int row = 0; row < puzzle.rows.size(); row++) {
                List<Boolean> cols = new ArrayList<Boolean>();
                for (int col = 0; col < puzzle.rows.get(row).size(); col++) {
                    cols.add(puzzle.occupied(new Location(row, col)));
                }

                this.rows.add(cols);
            }
            this.moves = new LinkedList<Move>(puzzle.moves);
            this.initialEmpty = puzzle.initialEmpty;
        }

        TrianglePuzzle(int n, Location initialEmpty) {
            this.moves = new LinkedList<Move>();
            this.rows = new ArrayList<List<Boolean>>(n);
            for (int i = 0; i < n; i++) {
                List<Boolean> cols = new ArrayList<Boolean>();
                for (int j = 0; j < i + 1; j++) {
                    cols.add(true);
                    this.tees++;
                }

                this.rows.add(cols);
            }

            this.initialEmpty = initialEmpty;
            set(initialEmpty, false);
            this.tees--;
        }

        private boolean occupied(Location loc) {
            return rows.get(loc.row).get(loc.col);
        }

        private void set(Location loc, boolean occupied) {
            rows.get(loc.row).set(loc.col, occupied);
        }

        boolean solved() {
            return tees == 1;
        }

        List<Move> availableMoves() {
            List<Move> available = new ArrayList<Move>();
            for (int row = 0; row < rows.size(); row++) {
                for (int col = 0; col < rows.get(row).size(); col++) {
                    Location from = new Location(row, col);
                    if (!occupied(from)) continue;
                    for (int[] offset : OFFSETS) {
                        Location to = new Location(from.row + offset[0], from.col + offset[1]);
                        if (to.row >= 0 && to.row < rows.size() 
                                && to.col >= 0 && to.col < rows.get(to.row).size()
                                && !occupied(to)) {
                            Move m = new Move(from ,to);
                            if (occupied(m.through())) {
                                available.add(m);
                            }
                        }
                    }
                }
            }

            return available;
        }

        List<Move> steps() {
            return moves;
        }

        TrianglePuzzle move(Move m) {
            TrianglePuzzle puzzle = new TrianglePuzzle(this);
            puzzle.set(m.from, false);
            puzzle.set(m.through(), false);
            puzzle.set(m.to, true);
            puzzle.moves.add(m);
            puzzle.tees--;

            return puzzle;
        }

        void print() {
            for (int row = 0; row < rows.size(); row++) {
                for (int col = 0; col < rows.get(row).size(); col++) {
                    if (col == 0) {
                        for (int i = 0; i < rows.size() - row; i++) {
                            System.out.print(" ");
                        }
                    }

                    if (occupied(new Location(row, col))) {
                        System.out.print("X");
                    } else {
                        System.out.print(".");
                    }

                    if (col < rows.get(row).size() - 1) {
                        System.out.print(" ");
                    }
                }

                System.out.println();
            }
        }
    }
}
