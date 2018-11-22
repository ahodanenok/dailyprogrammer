import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;

/**
 * Dailyprogrammer: 22 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/qr0l2/3102012_challenge_22_intermediate/
 */
public class Intermediate extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static final int VIEW_WIDTH = 20;
    private static final int VIEW_HEIGHT = 20;

    private Labyrinth labyrinth;
    private Canvas canvas;
    private GraphicsContext gc;

    public void start(Stage stage) {
        createLabyrinth(100, 100);

        canvas = new Canvas(600, 600);
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                handleMove(event);
            }
        });

        gc = canvas.getGraphicsContext2D();
        gc.scale(canvas.getWidth() / VIEW_WIDTH, canvas.getHeight() / VIEW_HEIGHT);

        drawLabyrinth();
        Group root = new Group();
        root.getChildren().add(canvas);
        stage.setTitle("Labyrinth");
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    }

    private void createLabyrinth(int width, int height) {
        Labyrinth.LabyrinthBuilder builder = new Labyrinth.LabyrinthBuilder(width, height);

        Random r = new Random(System.currentTimeMillis());
        List<Location> cells = new ArrayList<Location>();
        Set<Location> visited = new HashSet<Location>();

        Location currentCell = new Location(r.nextInt(height), r.nextInt(width));
        visited.add(currentCell);
        for (Direction dir : getPossibleDirections(currentCell, width, height)) {
            cells.add(dir.adjust(currentCell));
        }

        while (cells.size() > 0) {
            currentCell = cells.remove(r.nextInt(cells.size()));
            if (visited.contains(currentCell)) continue;

            List<Direction> directions = getPossibleDirections(currentCell, width, height);
            Direction targetDir;
            while (true) {
                int idx = r.nextInt(directions.size());
                if (visited.contains(directions.get(idx).adjust(currentCell))) {
                    targetDir = directions.get(idx);
                    break;
                }
            }

            builder.open(currentCell.row, currentCell.col, targetDir);
            visited.add(currentCell);
            for (Direction dir : getPossibleDirections(currentCell, width, height)) {
                Location adjacentCell = dir.adjust(currentCell);
                if (!visited.contains(adjacentCell)) {
                    cells.add(adjacentCell);
                }
            }
        }

        builder.startAt(0, 0);
        builder.exitAt(height - 1, width - 1);

        labyrinth = builder.build();
    }

    private List<Direction> getPossibleDirections(Location cell, int width, int height) {
        List<Direction> directions = new ArrayList<Direction>(Direction.values().length);
        for (Direction dir : Direction.values()) {
            Location adjacent = dir.adjust(cell);
            if (adjacent.row >= 0 && adjacent.col >= 0 && adjacent.row < height && adjacent.col < width) {
                directions.add(dir);
            }
        }

        return directions;
    }

    private void handleMove(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN) {
            if (labyrinth.canMove(Direction.SOUTH)) {
                labyrinth.move(Direction.SOUTH);
                drawLabyrinth();
            }
        } else if (event.getCode() == KeyCode.UP) {
            if (labyrinth.canMove(Direction.NORTH)) {
                labyrinth.move(Direction.NORTH);
                drawLabyrinth();
            }
        } else if (event.getCode() == KeyCode.LEFT) {
            if (labyrinth.canMove(Direction.WEST)) {
                labyrinth.move(Direction.WEST);
                drawLabyrinth();
            }
        } else if (event.getCode() == KeyCode.RIGHT) {
            if (labyrinth.canMove(Direction.EAST)) {
                labyrinth.move(Direction.EAST);
                drawLabyrinth();
            }
        }

        if (labyrinth.gameOver()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("You won!");

            alert.showAndWait();
        }
    }

    private void drawLabyrinth() {
        /*for (int row = 0; row < labyrinth.height; row++) {
            for (int col = 0; col < labyrinth.width; col++) {
                for (Direction dir : Direction.values()) {
                    if (labyrinth.isOpen(row, col, dir)) {
                        System.out.print(dir.toString().substring(0, 1));
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.print("   ");
            }
            System.out.println();
        }*/

        int startRow = Math.max(0, labyrinth.playerLocation.row - VIEW_HEIGHT / 2);
        int endRow = Math.min(labyrinth.height, labyrinth.playerLocation.row + (VIEW_HEIGHT - (labyrinth.playerLocation.row - startRow)));

        int startCol = Math.max(0, labyrinth.playerLocation.col - VIEW_WIDTH / 2);
        int endCol = Math.min(labyrinth.width, labyrinth.playerLocation.col + (VIEW_WIDTH - (labyrinth.playerLocation.col - startCol)));

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        for (int row = startRow, y = 0; row < endRow; row++, y++) {
            for (int col = startCol, x = 0; col < endCol; col++, x++) {
                int offsetX = 0;
                int offsetY = 0;
            
                if (labyrinth.playerLocation.equals(new Location(row, col))) {
                    gc.setFill(Color.BLUE);
                    gc.fillRect(x + offsetX, y + offsetY, 1, 1);
                } else if (labyrinth.exitLocation.equals(new Location(row, col))) {
                    gc.setFill(Color.GREEN);
                    gc.fillRect(x + offsetX, y + offsetY, 1, 1);
                } else {
                    gc.setFill(Color.WHITE);
                    gc.fillRect(x + offsetX, y + offsetY, 1, 1);
                }

                gc.setStroke(Color.BLACK);
                gc.setLineWidth(0.05);
                if (labyrinth.isWall(row, col, Direction.NORTH)) {
                    gc.strokeLine(x + offsetX, y + offsetY, x + offsetX + 1, y + offsetY);
                }

                if (labyrinth.isWall(row, col, Direction.EAST)) {
                    gc.strokeLine(x + offsetX + 1, y + offsetY, x + offsetX + 1, y + offsetY + 1);
                }

                if (labyrinth.isWall(row, col, Direction.SOUTH)) {
                    gc.strokeLine(x + offsetX, y + offsetY + 1, x + offsetX + 1, y + offsetY + 1);
                }

                if (labyrinth.isWall(row, col, Direction.WEST)) {
                    gc.strokeLine(x + offsetX, y + offsetY, x + offsetX, y + offsetY + 1);
                }
            }
        }
    }

    static class Location {

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

        @Override
        public int hashCode() {
            return 31 * row + col;
        }

        @Override
        public boolean equals(Object obj) {
            Location other = (Location) obj;
            return row == other.row && col == other.col;
        }
    }

    enum GameState {
        VICTORY, IN_PROGRESS;
    }

    enum Direction {
        NORTH(-1, 0),
        EAST(0, 1),
        SOUTH(1, 0),
        WEST(0, -1);

        private int offsetRow;
        private int offsetCol;

        Direction(int offsetRow, int offsetCol) {
            this.offsetRow = offsetRow;
            this.offsetCol = offsetCol;
        }

        Location adjust(int row, int col) {
            return new Location(row + offsetRow, col + offsetCol);
        }

        Location adjust(Location loc) {
            return adjust(loc.row, loc.col);
        }

        static Direction opposite(Direction dir) {
            int targetOffsetRow = -dir.offsetRow;
            int targetOffsetCol = -dir.offsetCol;
            for (Direction d : values()) {
                if (d.offsetRow == targetOffsetRow && d.offsetCol == targetOffsetCol) {
                    return d;
                }
            }

            throw new IllegalStateException("Couldn't find opposite for " + dir);
        }
    }

    static class Labyrinth {

        static class LabyrinthBuilder {

            private Labyrinth labyrinth;

            LabyrinthBuilder(int width, int height) {
                labyrinth = new Labyrinth();
                labyrinth.width = width;
                labyrinth.height = height;
                labyrinth.cells = new EnumSet[height][width];
                for (int row = 0; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        labyrinth.cells[row][col] = EnumSet.noneOf(Direction.class);
                    }
                }
            }

            LabyrinthBuilder startAt(int row, int col) {
                labyrinth.checkBounds(row, col);
                labyrinth.startLocation = new Location(row, col);
                labyrinth.playerLocation = new Location(row, col);
                return this;
            }

            LabyrinthBuilder exitAt(int row, int col) {
                labyrinth.checkBounds(row, col);
                labyrinth.exitLocation = new Location(row, col);
                return this;
            }

            LabyrinthBuilder open(int row, int col, Direction dir) {
                labyrinth.checkBounds(row, col);
                Location target = dir.adjust(row, col);
                labyrinth.checkBounds(target.row, target.col);
                labyrinth.cells[row][col].add(dir);
                labyrinth.cells[target.row][target.col].add(Direction.opposite(dir));
                return this;
            }

            LabyrinthBuilder wall(int row, int col, Direction dir) {
                labyrinth.checkBounds(row, col);
                Location target = dir.adjust(row, col);
                labyrinth.checkBounds(target.row, target.col);
                labyrinth.cells[row][col].remove(dir);
                labyrinth.cells[target.row][target.col].remove(Direction.opposite(dir));
                return this;
            }

            Labyrinth build() {
                Labyrinth lb = labyrinth;
                labyrinth = null;
                return lb;
            }
        }

        private Location playerLocation;
        private Location startLocation;
        private Location exitLocation;
        private GameState state = GameState.IN_PROGRESS;
        private EnumSet<Direction>[][] cells;
        private int width;
        private int height;

        private Labyrinth() { }

        int width() {
            return width;
        }

        int height() {
            return height;
        }

        boolean isWall(int row, int col, Direction dir) {
            checkBounds(row, col);
            return !cells[row][col].contains(dir);
        }

        boolean isOpen(int row, int col, Direction dir) {
            return !isWall(row, col, dir);
        }

        Location playerLocation() {
            return new Location(playerLocation.row, playerLocation.col);
        }

        boolean canMove(Direction dir) {
            Location toCell = dir.adjust(playerLocation);
            return !gameOver()
                && isValidCell(toCell.row, toCell.col)
                && isOpen(playerLocation.row, playerLocation.col, dir);
        }

        void move(Direction dir) {
           if (canMove(dir)) {
                playerLocation = dir.adjust(playerLocation);
                updateState();
            } else {
                throw new IllegalStateException("Can't move left");
            }
        }

        boolean gameOver() {
            return state == GameState.VICTORY;
        }

        private void updateState() {
            if (playerLocation.equals(exitLocation)) {
                state = GameState.VICTORY;
            }
        }

        private boolean isValidCell(int row, int col) {
            if (row < 0) return false;
            if (row >= height) return false;
            if (col < 0) return false;
            if (col >= width) return false;
            return true;
        }

        private void checkBounds(int row, int col) {
            if (!isValidCell(row, col)) {
                throw new IllegalStateException(String.format("Out of bounds: row=%d, col=%d (width=%d, height=%d)", row, col, width, height));
            }
        }
    }
}
