import java.util.Map;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Dailyprogrammer: 8 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/psewf/2162012_challenge_8_intermediate/
 */
public class Intermediate {

    private static final Map<Integer, String> SPELLING;
    private static final Map<String, Integer> VALUES;
    static {
        SPELLING = new LinkedHashMap<Integer, String>();
        SPELLING.put(1000000, "million");
        SPELLING.put(1000, "thousand");
        SPELLING.put(100, "hundred");
        SPELLING.put(90, "ninety");
        SPELLING.put(80, "eighty");
        SPELLING.put(70, "seventy");
        SPELLING.put(60, "sixty");
        SPELLING.put(50, "fifty");
        SPELLING.put(40, "forty");
        SPELLING.put(30, "thirty");
        SPELLING.put(20, "twenty");
        SPELLING.put(19, "nineteen");
        SPELLING.put(18, "eighteen");
        SPELLING.put(17, "seventeen");
        SPELLING.put(16, "sixteen");
        SPELLING.put(15, "fifteen");
        SPELLING.put(14, "fourteen");
        SPELLING.put(13, "thirteen");
        SPELLING.put(12, "twelve");
        SPELLING.put(11, "eleven");
        SPELLING.put(10, "ten");
        SPELLING.put(9, "nine");
        SPELLING.put(8, "eight");
        SPELLING.put(7, "seven");
        SPELLING.put(6, "six");
        SPELLING.put(5, "five");
        SPELLING.put(4, "four");
        SPELLING.put(3, "three");
        SPELLING.put(2, "two");
        SPELLING.put(1, "one");
        SPELLING.put(0, "zero");

        VALUES = new LinkedHashMap<String, Integer>();
        for (Map.Entry<Integer, String> entry : SPELLING.entrySet()) {
            VALUES.put(entry.getValue(), entry.getKey());
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            printUsageAndExit();
        }

        if ("toString".equals(args[0])) {
            System.out.println(toString(Integer.parseInt(args[1])));
        } else if ("fromString".equals(args[0])) {
            System.out.println(fromString(args[1]));
        } else {
            printUsageAndExit();
        }
    }

    private static void printUsageAndExit() {
        System.out.println("java Intermediate <toString|fromString> <number>");
        System.exit(-1);
    }

    public static String toString(int number) {
        StringBuilder sb = new StringBuilder();
        toString(number, sb);
        return sb.toString();
    }

    public static void toString(int number, StringBuilder sb) {
        if (number == 0) {
            sb.append(SPELLING.get(number));
            return;
        }

        int currNumber = number;
        while (currNumber > 0) {
            for (int n : SPELLING.keySet()) {
                int d = currNumber / n;
                if (d > 0) { 
                    // prefix with 'one' only 100, 1000, ... , i.e 100 -> 'one hundred'
                    if (d == 1 && n % 100 == 0) {
                        if (sb.length() > 0) sb.append(' ');
                        sb.append(SPELLING.get(d));
                    }

                    if (d > 1) {
                        if (SPELLING.containsKey(d)) {
                            if (sb.length() > 0) sb.append(' ');
                            sb.append(SPELLING.get(d));
                        } else {
                            toString(d, sb);
                        }
                    }

                    if (sb.length() > 0) sb.append(' ');
                    sb.append(SPELLING.get(n));
                    currNumber %= n;
                    break;
                }
            }
        }
    }

    public static int fromString(String str) {
        String[] parts = str.trim().split("\\s+");
        LinkedList<Integer> stack = new LinkedList<Integer>();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (!VALUES.containsKey(part)) {
                throw new IllegalArgumentException("Not a number: " + part);
            }

            int num = VALUES.get(part);
            if (num % 100 == 0) {
                int stackNum = 0;
                while (stack.size() > 0 && stack.peekLast() < num) {
                    stackNum += stack.removeLast();
                }

                stack.add(stackNum * num);
            } else {
                stack.add(num);
            }
        }

        int num = 0;
        while (stack.size() > 0) {
            num += stack.removeLast();
        }

        return num;
    }
}
