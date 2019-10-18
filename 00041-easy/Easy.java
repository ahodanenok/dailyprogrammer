import java.util.List;
import java.util.ArrayList;

/**
 * Dailyprogrammer 41 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/shp28/4192012_challenge_41_easy/
 */
public class Easy {

    private static final int COLUMNS = 10;
    private static final int PADDING_TOP_BOTTOM = 1;
    private static final int PADDING_LEFT_RIGHT = 5;

    public static void main(String[] args) {
        String msg = args[0];

        List<String> lines = wrap(msg, COLUMNS);
        int textWidth = Integer.MIN_VALUE;
        for (String line : lines) {
            textWidth = Math.max(line.length(), textWidth);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(padLeft("*", '*', textWidth + 2 * PADDING_LEFT_RIGHT + 2)).append('\n');
        for (int i = 0; i < PADDING_TOP_BOTTOM; i++) {
            sb.append("*" + padLeft(" ", ' ', textWidth + 2 * PADDING_LEFT_RIGHT) + "*").append('\n');
        }
        for (String line : lines) {
            sb.append("*" + padLeft(padRight(line, ' ', textWidth + PADDING_LEFT_RIGHT), ' ', textWidth + 2 * PADDING_LEFT_RIGHT) + "*").append('\n');
        }
        for (int i = 0; i < PADDING_TOP_BOTTOM; i++) {
            sb.append("*" + padLeft(" ", ' ', textWidth + 2 * PADDING_LEFT_RIGHT) + "*").append('\n');
        }
        sb.append(padLeft("*", '*', textWidth + 2 * PADDING_LEFT_RIGHT + 2));

        System.out.println(sb);
    }

    private static List<String> wrap(String msg, int wrapWidth) {
        int width = Math.min(msg.length(), wrapWidth);
        List<String> lines = new ArrayList<String>();

        int line = 0;
        int col = 0;
        while (col < msg.length()) {
            if (col == (line + 1) * width) line++;
            if (col == line * width) lines.add("");
            lines.set(line, lines.get(line) + msg.charAt(col));
            col++;
        }

        return lines;
    }

    private static String padLeft(String str, char ch, int width) {
        String result = str;
        while (result.length() < width) result = ch + result;
        return result;
    }

    private static String padRight(String str, char ch, int width) {
        String result = str;
        while (result.length() < width) result += ch;
        return result;
    }
}
