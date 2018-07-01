import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Dailyprogrammer: 3 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pkwgf/2112012_challenge_3_difficult/
 */
public class Difficult {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("wordlist.txt"));
        List<String> wordlist = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            wordlist.add(line.trim());
        }

        List<String> scrambledWords = new ArrayList<String>();
        scrambledWords.add("mkeart");
        scrambledWords.add("sleewa");
        scrambledWords.add("edcudls");
        scrambledWords.add("iragoge");
        scrambledWords.add("usrlsle");
        scrambledWords.add("amrhat");
        scrambledWords.add("iferkna");

        for (String scrambledWord : scrambledWords) {
            String word = findWord(scrambledWord, wordlist);
            if (word != null) {
                System.out.println(scrambledWord + " -> " + word);
            } else {
                System.out.println(scrambledWord + " -> " + scrambledWord);
            }
        }
    }

    private static String findWord(String scrambledWord, List<String> wordlist) {
        List<Character> chars = new ArrayList<Character>(scrambledWord.length());
        for (char ch : scrambledWord.toCharArray()) {
            chars.add(ch);
        }

        return findWord(scrambledWord, new StringBuilder(), chars, wordlist);
    }

    private static String findWord(String scrambledWord, StringBuilder word, List<Character> availableChars, List<String> wordlist) {
        if (word.length() == scrambledWord.length() && wordlist.contains(word.toString())) {
            return word.toString();
        }

        if (availableChars.size() == 0) {
            return null;
        }

        for (int i = 0; i < availableChars.size(); i++) {
            char ch = availableChars.remove(i);
            word.append(ch);
            String result = findWord(scrambledWord, word, availableChars, wordlist);
            if (result != null) {
                return result;
            }

            word.deleteCharAt(word.length() - 1);
            availableChars.add(i, ch);
        }

        return null;
    }
}
