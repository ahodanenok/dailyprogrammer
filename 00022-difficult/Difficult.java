import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

import java.io.File;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Dailyprogrammer: 22 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/qr0ox/3102012_challenge_22_difficult/
 */
public class Difficult {

    private static final int CELL_WIDTH = 20;
    private static final int CELL_HEIGHT = 20;
    private static final int BORDER_WIDTH = 4;

    public static void main(String[] args) {
        Maze maze = generate(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        saveToImage(maze, solve(maze, new Location(0, 0), new Location(maze.height() - 1, maze.width() - 1)));
    }

    private static void saveToImage(Maze maze, List<Location> path) {
        int width = (maze.width() + 1) * BORDER_WIDTH + maze.width() * 20;
        int height = (maze.height() + 1) * BORDER_WIDTH + maze.height() * 20;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) img.getGraphics();

        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                int x = (col + 1) * BORDER_WIDTH + col * CELL_WIDTH;
                int y = (row + 1) * BORDER_WIDTH + row * CELL_HEIGHT;

                g.setColor(Color.WHITE);
                g.fill(new Rectangle(x, y, CELL_WIDTH, CELL_HEIGHT));

                g.setColor(maze.isConnected(new Location(row, col), Maze.DIRECTION_NORTH) ? Color.WHITE : Color.BLACK);
                g.fill(new Rectangle(x, y - BORDER_WIDTH, CELL_WIDTH, BORDER_WIDTH));

                g.setColor(maze.isConnected(new Location(row, col), Maze.DIRECTION_EAST) ? Color.WHITE : Color.BLACK);
                g.fill(new Rectangle(x + CELL_WIDTH, y, BORDER_WIDTH, CELL_HEIGHT));

                g.setColor(maze.isConnected(new Location(row, col), Maze.DIRECTION_SOUTH) ? Color.WHITE : Color.BLACK);
                g.fill(new Rectangle(x, y + CELL_HEIGHT, CELL_WIDTH, BORDER_WIDTH));

                g.setColor(maze.isConnected(new Location(row, col), Maze.DIRECTION_WEST) ? Color.WHITE : Color.BLACK);
                g.fill(new Rectangle(x - BORDER_WIDTH, y, BORDER_WIDTH, CELL_HEIGHT));
            }
        }

        try {
            ImageIO.write(img, "png", new File("maze.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                if (path.contains(new Location(row, col))) {
                    int x = (col + 1) * BORDER_WIDTH + col * CELL_WIDTH;
                    int y = (row + 1) * BORDER_WIDTH + row * CELL_HEIGHT;

                    g.setColor(Color.BLUE);
                    g.fill(new Rectangle(x, y, CELL_WIDTH, CELL_HEIGHT));
                }
            }
        }

        try {
            ImageIO.write(img, "png", new File("maze-solved.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Maze generate(int width, int height) {
        MazeBuilder builder = new MazeBuilder(width, height);
        Random r = new Random(System.currentTimeMillis());
        List<Location> cells = new ArrayList<Location>();
        Set<Location> visited = new HashSet<Location>();
        byte[] directions = new byte[] { Maze.DIRECTION_NORTH, Maze.DIRECTION_EAST, Maze.DIRECTION_SOUTH, Maze.DIRECTION_WEST };

        Location currentCell = new Location(r.nextInt(height), r.nextInt(width));
        visited.add(currentCell);
        byte adjacentDirections = builder.adjacentDirections(currentCell);
        if ((adjacentDirections & Maze.DIRECTION_NORTH) > 0) cells.add(builder.adjacent(currentCell, Maze.DIRECTION_NORTH));
        if ((adjacentDirections & Maze.DIRECTION_EAST) > 0) cells.add(builder.adjacent(currentCell, Maze.DIRECTION_EAST));
        if ((adjacentDirections & Maze.DIRECTION_SOUTH) > 0) cells.add(builder.adjacent(currentCell, Maze.DIRECTION_SOUTH));
        if ((adjacentDirections & Maze.DIRECTION_WEST) > 0) cells.add(builder.adjacent(currentCell, Maze.DIRECTION_WEST));

        while (cells.size() > 0) {
            currentCell = cells.remove(r.nextInt(cells.size()));
            if (visited.contains(currentCell)) continue;

            adjacentDirections = builder.adjacentDirections(currentCell);
            byte targetDir;
            while (true) {
                int idx = r.nextInt(directions.length);
                if ((adjacentDirections & directions[idx]) > 0 && visited.contains(builder.adjacent(currentCell, directions[idx]))) {
                    targetDir = directions[idx];
                    break;
                }
            }

            builder.connect(currentCell, targetDir);
            visited.add(currentCell);
            adjacentDirections = builder.adjacentDirections(currentCell);
            if ((adjacentDirections & Maze.DIRECTION_NORTH) > 0) {
                Location adjacent = builder.adjacent(currentCell, Maze.DIRECTION_NORTH);
                if (!visited.contains(adjacent)) cells.add(adjacent);
            }

            if ((adjacentDirections & Maze.DIRECTION_EAST) > 0) {
                Location adjacent = builder.adjacent(currentCell, Maze.DIRECTION_EAST);
                if (!visited.contains(adjacent))cells.add(adjacent);
            }

            if ((adjacentDirections & Maze.DIRECTION_SOUTH) > 0) {
                Location adjacent = builder.adjacent(currentCell, Maze.DIRECTION_SOUTH);
                if (!visited.contains(adjacent)) cells.add(adjacent);
            }

            if ((adjacentDirections & Maze.DIRECTION_WEST) > 0) {
                Location adjacent = builder.adjacent(currentCell, Maze.DIRECTION_WEST);
                if (!visited.contains(adjacent)) cells.add(adjacent);
            }
        }

        return builder.build();
    }

    private static List<Location> solve(Maze maze, Location start, Location end) {
        Set<Location> visited = new HashSet<Location>();
        LinkedList<Location> path = new LinkedList<Location>();
        solve(maze, start, end, visited, path);
        return path;
    }

    private static boolean solve(Maze maze, Location current, Location end, Set<Location> visited, LinkedList<Location> path) {
        if (visited.contains(current)) return false;

        visited.add(current);
        path.add(current);

        if (current.equals(end)) return true;

        boolean solved = false;
        byte adjacentDirections = maze.adjacentDirections(current);
        if (!solved && (adjacentDirections & Maze.DIRECTION_NORTH) > 0 && maze.isConnected(current, Maze.DIRECTION_NORTH)) 
            solved = solve(maze, maze.adjacent(current, Maze.DIRECTION_NORTH), end, visited, path);

        if (!solved && (adjacentDirections & Maze.DIRECTION_EAST) > 0 && maze.isConnected(current, Maze.DIRECTION_EAST))
            solved = solve(maze, maze.adjacent(current, Maze.DIRECTION_EAST), end, visited, path);

        if (!solved && (adjacentDirections & Maze.DIRECTION_SOUTH) > 0 && maze.isConnected(current, Maze.DIRECTION_SOUTH)) 
            solved = solve(maze, maze.adjacent(current, Maze.DIRECTION_SOUTH), end, visited, path);

        if (!solved && (adjacentDirections & Maze.DIRECTION_WEST) > 0 && maze.isConnected(current, Maze.DIRECTION_WEST)) 
            solved = solve(maze, maze.adjacent(current, Maze.DIRECTION_WEST), end, visited, path);

        if (!solved) {
            path.removeLast();
        }

        return solved;
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

    private static class Maze {

        public static final byte DIRECTION_NORTH = 1;
        public static final byte DIRECTION_EAST  = 2;
        public static final byte DIRECTION_SOUTH = 4;
        public static final byte DIRECTION_WEST  = 8;

        private int width;
        private int height;
        private byte[][] cells;

        private Maze(int width, int height) {
            this.width = width;
            this.height = height;
            this.cells = new byte[height][width];
        }

        int width() {
            return width;
        }

        int height() {
            return height;
        }

        boolean isConnected(Location loc, byte dir) {
            return (cells[loc.row][loc.col] & dir) > 0;
        }

        boolean isValidLocation(Location loc) {
            return loc.row >= 0 && loc.row < height && loc.col >= 0 && loc.col < width;
        }

        Location adjacent(Location loc, byte dir) {
            if (dir == Maze.DIRECTION_NORTH) {
                return new Location(loc.row - 1, loc.col);
            } else if (dir == Maze.DIRECTION_EAST) {
                return new Location(loc.row, loc.col + 1);
            } else if (dir == Maze.DIRECTION_SOUTH) {
                return new Location(loc.row + 1, loc.col);
            } else if (dir == Maze.DIRECTION_WEST) {
                return new Location(loc.row, loc.col - 1);
            } else {
                throw new IllegalArgumentException("Illegal direction: " + dir);
            }
        }

        byte adjacentDirections(Location loc) {
            byte directions = 0;

            if (isValidLocation(adjacent(loc, Maze.DIRECTION_NORTH))) {
                directions |= Maze.DIRECTION_NORTH;
            }

            if (isValidLocation(adjacent(loc, Maze.DIRECTION_EAST))) {
                directions |= Maze.DIRECTION_EAST;
            }

            if (isValidLocation(adjacent(loc, Maze.DIRECTION_SOUTH))) {
                directions |= Maze.DIRECTION_SOUTH;
            }

            if (isValidLocation(adjacent(loc, Maze.DIRECTION_WEST))) {
                directions |= Maze.DIRECTION_WEST;
            }

            return directions;
        }
    }

    private static class MazeBuilder {

        private Maze maze;

        MazeBuilder(int width, int height) {
            maze = new Maze(width, height);
        }

        void connect(Location loc, byte dir) {
            maze.cells[loc.row][loc.col] |= dir;
            Location target = adjacent(loc, dir);
            if (dir == Maze.DIRECTION_NORTH) {
                maze.cells[target.row][target.col] |= Maze.DIRECTION_SOUTH;
            } else if (dir == Maze.DIRECTION_EAST) {
                maze.cells[target.row][target.col] |= Maze.DIRECTION_WEST;
            } else if (dir == Maze.DIRECTION_SOUTH) {
                maze.cells[target.row][target.col] |= Maze.DIRECTION_NORTH;
            } else if (dir == Maze.DIRECTION_WEST) {
                maze.cells[target.row][target.col] |= Maze.DIRECTION_EAST;
            } else {
                throw new IllegalArgumentException("Illegal direction: " + dir);
            }
        }

        Location adjacent(Location loc, byte dir) {
            return maze.adjacent(loc, dir);
        }

        byte adjacentDirections(Location loc) {
            return maze.adjacentDirections(loc);
        }

        Maze build() {
            Maze m = maze;
            maze = null;
            return m;
        }
    }
}
