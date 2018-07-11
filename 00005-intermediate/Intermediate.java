import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Dailyprogrammer: 5 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/pnhtj/2132012_challenge_5_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String word;
            while ((word = reader.readLine()) != null) {
                words.add(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println("'" + args[0] + "' file not exist");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Can't read file '" + args[0] + "'");
            System.exit(-1);
        }

        Map<String, List<String>> anagrams = new HashMap<>();
        for (String word : words) {
            String signature = signature(word);
            List<String> wordAnagrams = anagrams.get(signature);
            if (wordAnagrams == null) {
                wordAnagrams = new ArrayList<>();
                anagrams.put(signature, wordAnagrams);
            }

            wordAnagrams.add(word);
        }

        for (List<String> anagramWords : anagrams.values()) {
            if (anagramWords.size() > 1) {
                System.out.println(anagramWords);
            }
        }
    }

    private static String signature(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }
}