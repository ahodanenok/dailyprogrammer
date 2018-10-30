import java.util.Map;
import java.util.HashMap;

/**
 * Dailyprogrammer: 18 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/qit0h/352012_challenge_18_easy/
 */
public class Easy {

    private static final Map<Character, Integer> LETTERS_TO_NUMBERS;
    static {
        LETTERS_TO_NUMBERS = new HashMap<Character, Integer>();
        LETTERS_TO_NUMBERS.put('A', 2);
        LETTERS_TO_NUMBERS.put('B', 2);
        LETTERS_TO_NUMBERS.put('C', 2);
        LETTERS_TO_NUMBERS.put('D', 3);
        LETTERS_TO_NUMBERS.put('E', 3);
        LETTERS_TO_NUMBERS.put('F', 3);
        LETTERS_TO_NUMBERS.put('G', 4);
        LETTERS_TO_NUMBERS.put('H', 4);
        LETTERS_TO_NUMBERS.put('I', 4);
        LETTERS_TO_NUMBERS.put('J', 5);
        LETTERS_TO_NUMBERS.put('K', 5);
        LETTERS_TO_NUMBERS.put('L', 5);
        LETTERS_TO_NUMBERS.put('M', 6);
        LETTERS_TO_NUMBERS.put('N', 6);
        LETTERS_TO_NUMBERS.put('O', 6);
        LETTERS_TO_NUMBERS.put('P', 7);
        LETTERS_TO_NUMBERS.put('Q', 7);
        LETTERS_TO_NUMBERS.put('R', 7);
        LETTERS_TO_NUMBERS.put('S', 7);
        LETTERS_TO_NUMBERS.put('T', 8);
        LETTERS_TO_NUMBERS.put('U', 8);
        LETTERS_TO_NUMBERS.put('V', 8);
        LETTERS_TO_NUMBERS.put('W', 9);
        LETTERS_TO_NUMBERS.put('X', 9);
        LETTERS_TO_NUMBERS.put('Y', 9);
        LETTERS_TO_NUMBERS.put('Z', 9);
    }

    public static void main(String[] args) {
        String phoneWithLetters = args[0];

        StringBuilder phone = new StringBuilder();
        for (int i = 0; i < phoneWithLetters.length(); i++) {
            char ch = phoneWithLetters.charAt(i);
            if (LETTERS_TO_NUMBERS.containsKey(ch)) {
                phone.append(LETTERS_TO_NUMBERS.get(ch));
            } else {
                phone.append(ch);
            }
        }

        phone.insert(phone.length() - 4, '-');
        System.out.println(phone);
    }
}
