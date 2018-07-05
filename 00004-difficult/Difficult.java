import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Dailyprogrammer: 4 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pm7g7/2122012_challange_4_difficult/
 */
public class Difficult {

    private static final char[] OP = {'+', '-', '*', '/'};

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("java Difficult <integers>");
            System.exit(-1);
            return;
        }

        int[] numbers = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            numbers[i] = Integer.parseInt(args[i]);
        }

        Set<Relation> relations = new LinkedHashSet<>();
        collectRelations(numbers, relations);
        for (Relation r : relations) {
            System.out.println(String.format("%d %s %d = %d", r.a, r.op, r.b, r.result));
        }
    }

    private static void collectRelations(int[] series, Set<Relation> relations) {
        for (int i = 0; i < series.length; i++) {
            for (int j = 0; j < series.length; j++) {
                if (i == j) continue;
                for (char op : OP) {
                    if (op == '+') {
                        next(series, i, j, series[i] + series[j], op, relations);
                    } else if (op == '-') {
                        next(series, i, j, series[i] - series[j], op, relations);
                    } else if (op == '*') {
                        next(series, i, j, series[i] * series[j], op, relations);
                    } else if (op == '/') {
                        if (series[j] > 0 && series[i] % series[j] == 0) {
                            next(series, i, j, series[i] / series[j], op, relations);
                        }
                    } else {
                        throw new IllegalArgumentException(op + "");
                    }
                }
            }
        }
    }

    private static void next(int[] series, int i, int j, int value, char op, Set<Relation> relations) {
        int idx = indexOf(series, value, i, j);
        if (idx > -1) {
            relations.add(new Relation(series[i], op, series[j], value));
        }

        if (series.length > 2 && value > 1) {
            int[] newSeries = arrayWithout(series, Math.max(i, j));
            newSeries[Math.min(i, j)] = value;
            //System.out.println(Arrays.toString(series) + " -> " + Arrays.toString(newSeries));
            collectRelations(newSeries, relations);
        }
    }

    private static int indexOf(int[] arr, int value, int... excludeIndices) {
        next:
        for (int i = 0; i < arr.length; i++) {
            for (int excludeIdx : excludeIndices) {
                if (i == excludeIdx) continue next;
            }

            if (arr[i] == value) {
                return i;
            }
        }

        return -1;
    }

    private static int[] arrayWithout(int[] arr, int i) {
        int[] copy = new int[arr.length - 1];
        for (int j = 0; j < i; j++) {
            copy[j] = arr[j];
        }

        for (int j = i + 1; j < arr.length; j++) {
            copy[j - 1] = arr[j];
        }

        return copy;
    }

    private static class Relation {

        private int a;
        private int b;
        private char op;
        private int result;

        public Relation(int a, char op, int b, int result) {
            this.op = op;
            this.result = result;
            if (op == '+' || op == '*') {
                this.a = Math.min(a, b);
                this.b = Math.max(a, b);
            } else {
                this.a = a;
                this.b = b;
            }
        }

        @Override
        public int hashCode() {
            return 31 * (31 * (31 * (31 * a) + b) + (int) op) + result;
        }

        @Override
        public boolean equals(Object obj) {
            Relation other = (Relation) obj;
            return this.a == other.a && this.b == other.b && this.op == other.op && this.result == other.result;
        }
    }
}
