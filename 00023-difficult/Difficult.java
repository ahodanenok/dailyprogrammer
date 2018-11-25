import java.util.Scanner;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Dailyprogrammer: 23 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/qum19/3132012_challenge_23_difficult/
 */
public class Difficult {

    private static class SequenceComparator {

        int compare(char a, char b) {
            return (int) a - (int) b;
        }

        public boolean contains(char a) {
            return true;
        }
    }

    public static void main(String[] args) {
        Map<String, SequenceComparator> comparators = new HashMap<String, SequenceComparator>();
        comparators.put("ASCII-order", new SequenceComparator());
        comparators.put("ASCII-order-ignore-case", new SequenceComparator() {
            public int compare(char a, char b) {
                return super.compare(Character.toLowerCase(a), Character.toLowerCase(b));
            }
        });
        comparators.put("reverse-ASCII-order", new SequenceComparator() {
            public int compare(char a, char b) {
                return super.compare(b, a);
            }
        });
        comparators.put("reverse-ASCII-order-ignore-case", new SequenceComparator() {
            public int compare(char a, char b) {
                return super.compare(Character.toLowerCase(b), Character.toLowerCase(a));
            }
        });
        comparators.put("a-z", new SequenceComparator() {
            public boolean contains(char a) {
                return a >= 'a' && a <= 'z';
            }
        });
        comparators.put("A-Z", new SequenceComparator() {
            public boolean contains(char a) {
                return a >= 'A' && a <= 'Z';
            }
        });
        comparators.put("0-9", new SequenceComparator() {
            public boolean contains(char a) {
                return a >= '0' && a <= '9';
            }
        });

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<List<SequenceComparator>> sequences = new ArrayList<List<SequenceComparator>>(n);
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String[] defs = scanner.nextLine().split(" ");
            List<SequenceComparator> sequence = new ArrayList<SequenceComparator>();
            for (String def : defs) {
                sequence.add(comparators.get(def));
            }

            sequences.add(sequence);
        }

        List<String> strings = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            strings.add(scanner.nextLine());
        }

        Collections.sort(strings, new Comparator<String>() {
            public int compare(String a, String b) {
                int length = Math.min(a.length(), b.length());
                for (int i = 0; i < length; i++) {
                    char aChar = a.charAt(i);
                    char bChar = b.charAt(i);
                    if (aChar == bChar) continue;

                    List<SequenceComparator> sequence = sequences.get(i % sequences.size());
                    int aPos = -1;
                    int bPos = -1;

                    for (int j = 0; j < sequence.size(); j++) {
                        if (aPos == -1 && sequence.get(j).contains(aChar)) aPos = j;
                        if (bPos == -1 && sequence.get(j).contains(bChar)) bPos = j;
                    }

                    if (aPos == bPos) {
                        int result = sequence.get(aPos).compare(aChar, bChar);
                        if (result != 0) return result;
                    } else {
                        return aPos - bPos;
                    }
                }

                return a.length() - b.length();
            }
        });

        for (String str : strings) {
            System.out.println(str);
        }
    }
}
