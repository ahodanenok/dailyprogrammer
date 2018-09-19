import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.HashSet;

/**
 * Dailyprogrammer: 10 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/pv8zm/2182012_challenge_10_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int from = Integer.parseInt(args[0]);
        int to = Integer.parseInt(args[1]);

        Set<Pair> seen = new HashSet<Pair>();
        for (int i = from; i <= to; i++) {
            for (int j = i + 1; j <= to; j++) {
                if (i == j) continue;

                seen.add(new Pair(i, j));
                seen.add(new Pair(j, i));

                int sum = i + j;
                String digits = "" + i + j;
                Permutations permutations = new Permutations(digits);
                while (permutations.hasNext()) {
                    String p = permutations.next();
                    for (int k = (int) Math.ceil(digits.length() / 2); k <= digits.length() - 1; k++) {
                        if (p.charAt(0) != '0' && p.charAt(k) != '0') {
                            int a = Integer.parseInt(p.substring(0, k));
                            int b = Integer.parseInt(p.substring(k, digits.length()));
                            if (seen.contains(new Pair(a, b))) continue;
                            seen.add(new Pair(a, b));
                            seen.add(new Pair(b, a));
                            if (a != i && a != j && a + b == sum && isAnagram(toString(i) + toString(j), toString(a) + toString(b))) {
                                System.out.println(String.format("%1$d + %2$d = %5$d  <-> %3$d + %4$d = %5$d", i, j, a, b, sum));
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean isAnagram(String a, String b) {
        char[] charsA = a.toCharArray();
        Arrays.sort(charsA);

        char[] charsB = b.toCharArray();
        Arrays.sort(charsB);

        return Arrays.equals(charsA, charsB);
    }

    private static class Pair {

        final int a;
        final int b;

        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int hashCode() {
            return 31 * a + b;
        }

        @Override
        public boolean equals(Object other) {
            return a == ((Pair) other).a && b == ((Pair) other).b;
        }
    }

    private static class Permutations implements Iterator<String> {

        private String digits;
        private int[] indices;

        Permutations(String digits) {
            this.digits = digits;
            this.indices = new int[digits.length()];
            for (int i = 0; i < indices.length; i++) indices[i] = i;
        }

        public boolean hasNext() {
            return indices != null;
        }

        public String next() {
            String p = "";
            for (int i = 0; i < indices.length; i++) {
                p += digits.charAt(indices[i]);
            }

            increment();
            return p;
        }

        private void increment() {
            int idx = indices.length - 1;
            while (idx >= 0) {
                int n = indices[idx];
                while (contains(n)) n++;

                indices[idx] = n;
                if (indices[idx] >= digits.length()) {
                    indices[idx] = -1;
                } else {
                    break;
                }

                idx--;
            }

            if (idx >= 0) {
                int n = 0;
                for (int i = 0; i < indices.length; i++) {
                    if (indices[i] == -1) {
                        while (contains(n)) n++;
                        indices[i] = n;
                    }
                }
            } else {
                indices = null;
            }
        }

        private boolean contains(int n) {
            for (int i = 0; i < indices.length; i++) {
                if (indices[i] == n) {
                    return true;
                }
            }

            return false;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public static String toString(int number) {
        StringBuilder sb = new StringBuilder();
        toString(number, sb);
        return sb.toString();
    }

    private static final Map<Integer, String> SPELLING;
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
}
