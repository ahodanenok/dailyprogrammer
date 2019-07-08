import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Dailyprogrammer: 36 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/rujav/452012_challenge_36_difficult/
 */
public class Difficult {

    public static void main(String[] args) throws Exception {
        playGame(randomWord());
    }

    private static String randomWord() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
        String word;
        List<String> words = new ArrayList<String>();
        while ((word = reader.readLine()) != null) {
            if (word.trim().length() == 5) {
                words.add(word);
            }
        }
        reader.close();

        return words.get((int) Math.floor(Math.random() * words.size())).toLowerCase();
    }

    private static void playGame(String word) {
        Scanner scanner = new Scanner(System.in);
        String[] lines = new String[5];
        System.out.println(highlightLetters(word.charAt(0) + word.substring(1).replaceAll(".", "."), word));
        int tries = 0;

        while (true) {
            System.out.println();
            System.out.print("Your guess: ");
            if (!scanner.hasNext()) break;
            String guess = scanner.next();
            if (guess.length() != 5) {
                System.out.println("Game over, your guess must be a 5 letter word");
                break;
            }

            if (word.equals(guess)) {
                System.out.println("Correct!");
                break;
            }

            tries++;

            lines[tries - 1] = highlightLetters(guess, word);
            for (int i = 0; i < tries; i++) {
                System.out.println(lines[i]);
            }

            if (tries == 5) {
                System.out.println(String.format("Game over, the word is '%s'", word));
                break;
            }
        }
    }

    private static String highlightLetters(String guess, String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < guess.length(); i++) {
            char ch = guess.charAt(i);
            if (ch == word.charAt(i)) {
                sb.append('[').append(ch).append(']');
            } else if (word.indexOf(ch) >= 0) {
                sb.append('(').append(ch).append(')');
            } else {
                sb.append(' ').append(ch).append(' ');
            }
        }

        return sb.toString();
    }
}
