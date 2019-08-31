import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.LinkedHashMap;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Dailyprogrammer: 39 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/s6bab/4122012_challenge_39_difficult/
 */
public class Difficult {

    public static void main(String[] args) throws Exception {
        WordSearch ws = WordSearch.load(args[0]);

        char[][] grid = ws.grid();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                char ch = grid[row][col];
                if (ch != '\0') {
                    System.out.print(Character.toUpperCase(ch));
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }

        System.out.println();
        for (Map.Entry<String, List<Location>> entry : solve(ws).entrySet()) {
            if (entry.getValue() != null) {
                System.out.printf("+ %s -> %s%n", entry.getKey(), entry.getValue());
            } else {
                System.out.printf("- %s%n", entry.getKey());
            }
        }
    }

    private static Map<String, List<Location>> solve(WordSearch ws) {
        Map<String, List<Location>> result = new LinkedHashMap<String, List<Location>>();

        nextWord:
        for (String word : ws.words()) {
            for (int row = 0; row < ws.height(); row++) {
                for (int col = 0; col < ws.width(row); col++) {
                    Location loc = new Location(row, col);
                    List<Location> path = new ArrayList<>();
                    path.add(loc);
                    boolean found = findWord(ws, String.valueOf(ws.letterAt(loc)), path, word.replaceAll("[^A-Za-z]", ""));
                    if (found) {
                        result.put(word, path);
                        continue nextWord;
                    }
                }
            }

            result.put(word, null);
        }

        return result;
    }

    private static boolean findWord(WordSearch ws, String currentWord, List<Location> path, String word) {
        if (currentWord.length() > word.length() || !word.startsWith(currentWord)) return false;
        if (currentWord.equals(word)) return true;

        for (Location n : ws.neighbors(path.get(path.size() - 1))) {
            if (!path.contains(n)) {
                path.add(n);
                boolean found = findWord(ws, currentWord + ws.letterAt(n),path, word);
                if (found) return true;
                path.remove(n);
            }
        }

        return false;
    }

    private static class Location {

        final int row;
        final int col;

        Location(int row, int col) {
            this.row = row;
            this.col = col;
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

        @Override
        public String toString() {
            return String.format("(%d, %d)", row, col);
        }
    }

    private static class WordSearch {

        static WordSearch load(String file) throws Exception {
            WordSearch ws = new WordSearch();
            ws.words = new ArrayList<String>();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null || line.trim().isEmpty()) {
                    break;
                }

                ws.words.add(line);
            }

            int rowLength = Integer.MIN_VALUE;
            ws.rows = new ArrayList<String>();
            while (true) {
                line = reader.readLine();
                if (line == null || line.trim().isEmpty()) {
                    break;
                }

                ws.rows.add(line);
                rowLength = Math.max(line.length(), rowLength);
            }

            ws.offsets = new int[ws.rows.size()];
            ws.grid = new char[ws.rows.size()][rowLength];
            for (int row = 0; row < ws.rows.size(); row++) {
                line = ws.rows.get(row);
                int offset = 0;
                for (int col = 0; col < rowLength; col++) {
                    if (col < line.length()) {
                        char ch = line.charAt(col);
                        if (Character.isWhitespace(ch)) {
                            ws.grid[row][col] = '\0';
                            offset++;
                        } else {
                            ws.grid[row][col] = ch;
                        }
                    } else {
                        ws.grid[row][col] = '\0';
                    }
                }

                ws.offsets[row] = offset;
            }

            return ws;
        }

        private char[][] grid;
        private List<String> rows;
        private int[] offsets;

        private List<String> words;

        private WordSearch() {}

        List<String> words() {
            return words;
        }

        int height() {
            return rows.size();
        }

        int width(int col) {
            return rows.get(col).length();
        }

        char[][] grid() {
            return grid;
        }

        char letterAt(Location loc) {
            return rows.get(loc.row).charAt(loc.col);
        }

        List<Location> neighbors(Location loc) {
            List<Location> list = new ArrayList<Location>();
            Location n;

            n = adjacent(loc, -1, 0);
            if (n != null) list.add(n);

            n = adjacent(loc, 0, 1);
            if (n != null) list.add(n);

            n = adjacent(loc, 1, 0);
            if (n != null) list.add(n);

            n = adjacent(loc, 0, -1);
            if (n != null) list.add(n);

            return list;
        }

        private Location adjacent(Location loc, int dr, int dc) {
            int row = loc.row + dr;
            if (row < 0 || row >= height()) return null;

            int offset = offsets[row] - offsets[loc.row];
            int col = loc.col + dc + offset;
            if (col < 0 || col >= width(row)) return null;

            return new Location(row, col);
        }
    }
}
