import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Arrays;

/**
 * Dailyprogrammer: 42 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/sobuc/4232012_challenge_42_intermediate/
 */
public class Intermediate {

    private static final String NULLA = "N";

    private static final Map<String, Integer> VALUES = new HashMap<String, Integer>();
    private static final Map<String, String> UP_CONVERSIONS = new HashMap<String, String>();
    private static final Map<String, String> DOWN_CONVERSIONS = new HashMap<String, String>();
    static {
        VALUES.put("I", 1);
        VALUES.put("V", 5);
        VALUES.put("X", 10);
        VALUES.put("L", 50);
        VALUES.put("C", 100);
        VALUES.put("D", 500);
        VALUES.put("M", 1000);

        UP_CONVERSIONS.put("IIIII", "V");
        UP_CONVERSIONS.put("VV", "X");
        UP_CONVERSIONS.put("XXXXX", "L");
        UP_CONVERSIONS.put("LL", "C");
        UP_CONVERSIONS.put("CCCCC", "D");
        UP_CONVERSIONS.put("DD", "M");

        DOWN_CONVERSIONS.put("V", "IIIII");
        DOWN_CONVERSIONS.put("X", "VV");
        DOWN_CONVERSIONS.put("L", "XXXXX");
        DOWN_CONVERSIONS.put("C", "LL");
        DOWN_CONVERSIONS.put("D", "CCCCC");
        DOWN_CONVERSIONS.put("M", "DD");
    }

    public static void main(String[] args) {
        String a = args[0];
        String b = args[1];
        System.out.println(String.format("%s + %s = %s", a, b, romanAddition(a, b)));
        System.out.println(String.format("%s - %s = %s", a, b, romanSubtraction(a, b)));
        System.out.println(String.format("%s * %s = %s", a, b, romanMultiplication(a, b)));
        System.out.println(String.format("%s / %s = %s", a, b, romanDivision(a, b)));
    }

    private static String romanAddition(String a, String b) {
        if (NULLA.equals(a)) return b;
        if (NULLA.equals(b)) return a;

        String mix = a + b;

        Character[] digits = new Character[mix.length()];
        for (int i = 0; i < mix.length(); i++) {
            digits[i] = mix.charAt(i);
        }

        Arrays.sort(digits, new Comparator<Character>() {
            public int compare(Character c1, Character c2) {
                return -Integer.compare(VALUES.get(String.valueOf(c1)), VALUES.get(String.valueOf(c2)));
            }
        });

        StringBuilder result = new StringBuilder();
        for (Character d : digits) {
            result.append(d);
        }

        return normalize(result.toString());
    }

    private static String romanSubtraction(String a, String b) {
        if (NULLA.equals(a) && NULLA.equals(b)) return NULLA;
        if (NULLA.equals(a)) return "-" + b;
        if (NULLA.equals(b)) return a;

        StringBuilder digitsA = new StringBuilder(a);
        StringBuilder digitsB = new StringBuilder(b);
        boolean negativeResult = false;

        while (true) {
            for (int i = digitsB.length() - 1; i >= 0; i--) {
                int idx = digitsA.indexOf(String.valueOf(digitsB.charAt(i)));
                if (idx >= 0) {
                    digitsA.deleteCharAt(idx);
                    digitsB.deleteCharAt(i);
                }
            }

            if (digitsB.length() == 0) break;
            if (digitsA.length() == 0) {
                digitsA = digitsB;
                negativeResult = true;
                break;
            }

            boolean expanded = false;
            String lastDigitB = String.valueOf(digitsB.charAt(digitsB.length() - 1));
            for (int i = digitsA.length() - 1; i >= 0; i--) {
                String digitA = String.valueOf(digitsA.charAt(i));
                if (VALUES.get(digitA) > VALUES.get(lastDigitB)) {
                    digitsA.replace(i, i + 1, DOWN_CONVERSIONS.get(digitA));
                    expanded = true;
                    break;
                }
            }

            // first number is smaller than the second
            if (!expanded) {
                StringBuilder tmp = digitsA;
                digitsA = digitsB;
                digitsB = tmp;
                negativeResult = true;
            }
        }

        if (digitsA.length() > 0) {
            return (negativeResult ? "-" : "") + normalize(digitsA.toString());
        } else {
            return NULLA;
        }
    }

    private static String romanMultiplication(String a, String b) {
        if (NULLA.equals(a) || NULLA.equals(b)) return NULLA;

        String result = a;
        String times = b;
        while (!(times = romanSubtraction(times, "I")).equals(NULLA)) {
            result = romanAddition(result, a);
        }

        return result;
    }

    private static String romanDivision(String a, String b) {
        if (NULLA.equals(b)) throw new ArithmeticException("Division by zero");
        if (NULLA.equals(a)) return NULLA;

        String result = a;
        String times = "";
        while (!result.equals(NULLA) && !isRomanNegative(result) && !isRomanNegative(romanSubtraction(result, b))) {
            result = romanSubtraction(result, b);
            times = romanAddition(times, "I");
        }

        return times.length() > 0 ? times : NULLA;
    }

    private static boolean isRomanNegative(String n) {
        return n.startsWith("-");
    }

    private static String normalize(String n) {
        String result = n;
        for (Map.Entry<String, String> entry : UP_CONVERSIONS.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }

        if (result.equals(n)) {
            return result;
        } else {
            return normalize(result);
        }
    }
}
