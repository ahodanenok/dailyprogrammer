/**
 * Dailyprogrammer: 17 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/qheeu/342012_challenge_17_easy/
 */
public class Easy {

    private static final char LINE_CHAR = '@';

    public static void main(String[] args) {
        int linesCount = Integer.parseInt(args[0]);
        printTriangle(linesCount);
        System.out.println();
        printTriangleReversed(linesCount);
    }

    private static void printTriangle(int linesCount) {
        StringBuilder sb = new StringBuilder();
        int lineLength = 1;
        for (int n = 1; n <= linesCount; n++) {
            for (int i = 0; i < lineLength; i++) {
                sb.append(LINE_CHAR);
            }
 
            if (n < linesCount) {
                sb.append(System.lineSeparator());
            }

            lineLength *= 2;
        }

        System.out.println(sb);
    }

    private static void printTriangleReversed(int linesCount) {
        StringBuilder sb = new StringBuilder();
        int lineLength = (int) Math.pow(2, linesCount - 1);
        for (int n = linesCount; n > 0; n--) {
            for (int i = 0; i < lineLength; i++) {
                sb.append(LINE_CHAR);
            }
 
            if (n > 1) {
                sb.append(System.lineSeparator());
            }

            lineLength /= 2;
        }

        System.out.println(sb);
    }
}
