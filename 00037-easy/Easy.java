import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Dailyprogrammer: 37 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/rzdwq/482012_challenge_37_easy/
 */
public class Easy {

    public static void main(String[] args) throws Exception {
        String filePath = args[0];

        int linesCount = 0;
        int wordsCount = 0;

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            linesCount++;
            wordsCount += line.split("\\S+").length;
        }
        reader.close();

        System.out.println("Lines count: " + linesCount);
        System.out.println("Words count: " + wordsCount);
    }
}
