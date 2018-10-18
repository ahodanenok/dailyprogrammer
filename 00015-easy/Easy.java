import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

/**
 * Dailyprogrammer: 15 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/q4c34/2242012_challenge_15_easy/
 */
public class Easy {

    enum Mode {
        LEFT {
            @Override
            String justify(String line, int lineWidth) {
                String str = line.trim();
                while (str.length() < line.length()) str += ' ';
                return str;
            }
        },
        RIGHT {
            @Override
            String justify(String line, int lineWidth) {
                String str = line.trim();
                while (str.length() < lineWidth) str = ' ' + str;
                return str;
            }
        };

        abstract String justify(String line, int lineWidth);
    }

    public static void main(String[] args) {
        String mode = args[0];
        String fileName = args[1];

        if ("left".equals(mode)) {
            justify(fileName, Mode.LEFT);
        } else if ("right".equals(mode)) {
            justify(fileName, Mode.RIGHT);
        } else {
            System.out.println("valid values for mode are 'left' and 'right'");
        }
    }

    public static void justify(String fileName, Mode mode) {
        int lineWidth = 0;
        List<String> lines = new ArrayList<String>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                lineWidth = Math.max(line.length(), lineWidth);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) { }
            }
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            for (String line : lines) {
                writer.write(mode.justify(line, lineWidth) + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) { }
            }
        }
    }
}
