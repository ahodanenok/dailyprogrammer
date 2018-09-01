/**
 * Dailyprogrammer: 8 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/psf4n/2162012_challenge_8_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsageAndExit("java Difficult <print|value>");
        }

        if ("print".equals(args[0])) {
            if (args.length != 2) {
                printUsageAndExit("java Difficult print <rows>");
            }
        
            print(Integer.parseInt(args[1]));
        } else if ("value".equals(args[0])) {
            if (args.length != 3) {
                printUsageAndExit("java Difficult value <row> <col>");
            }

            System.out.println(valueAt(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
        } else {
            printUsageAndExit("java Difficult <print|value>");
        }
    }

    private static void printUsageAndExit(String usage) {
        System.out.println(usage);
        System.exit(-1);
    }

    private static int valueAt(int rowNum, int colNum) {
        if (rowNum < 1) throw new IllegalArgumentException("rowNum must be >= 1");
        if (colNum < 1) throw new IllegalArgumentException("colNum must be >= 1");
        if (colNum > rowNum) throw new IllegalArgumentException("colNum must be <= " + rowNum);

        int[] row = new int[0];
        for (int r = 0; r < rowNum; r++) {
            row = nextRow(row);
        }

        return row[colNum - 1];
    }

    private static void print(int rowCount) {
        if (rowCount < 1) throw new IllegalArgumentException("rowCount must be >= 1");

        int fieldWith = (((int) Math.ceil(Math.pow(2, rowCount) / rowCount)) + "").length() + 2;
        fieldWith = Math.max(fieldWith, 5);
        System.out.println("fieldWith=" + fieldWith);

        int[] row = new int[0];
        for (int rowNum = 0; rowNum < rowCount; rowNum++) {
            int padding = (int) Math.ceil((rowCount - rowNum - 1) / 2.0 * fieldWith);
            row = nextRow(row);

            StringBuilder line = new StringBuilder();
            line.append(String.format("row=%4d, padding=%4d | ", rowNum + 1, padding));

            fill(' ', padding, line);
            for (int i = 0; i < row.length; i++) {
                center(row[i], fieldWith, line);
            }

            System.out.println(line);
        }
    }

    private static void fill(char ch, int count, StringBuilder sb) {
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
    }

    private static void center(int num, int fieldWith, StringBuilder sb) {
        String value = num + "";
        if (value.length() >= fieldWith) {
            sb.append(value);
        } else {
            int padding = (int) Math.ceil((fieldWith - value.length()) / 2.0);
            fill(' ', padding, sb);
            sb.append(value);
            fill(' ', padding, sb);
        }
    }

    private static int[] nextRow(int[] prevRow) {
        int[] row = new int[prevRow.length + 1];
        row[0] = 1;
        row[row.length - 1] = 1;
        for (int i = 1; i < row.length - 1; i++) {
            row[i] = prevRow[i - 1] + prevRow[i];
        }

        return row;
    }
}
