import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

/**
 * Dailyprogrammer: 4 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/pm6sq/2122012_challenge_4_intermediate/
 */
public class Intermediate {

    private static final Map<Character, Integer> OP_PRECEDENCE = new HashMap<Character, Integer>();
    static {
        OP_PRECEDENCE.put('+', 2);
        OP_PRECEDENCE.put('-', 2);
        OP_PRECEDENCE.put('/', 1);
        OP_PRECEDENCE.put('*', 1);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Provide an expression to calculate");
            System.exit(-1);
        }

        String expr = args[0].trim();
        System.out.println("result=" + evaluate(expr));
    }

    private static BigDecimal evaluate(String expr) {
        LinkedList<BigDecimal> output = new LinkedList<BigDecimal>();
        LinkedList<Character> stack = new LinkedList<Character>();

        for (int i = 0; i < expr.length(); ) {
            System.out.println(output);
            System.out.println(stack);
        
            char ch = expr.charAt(i);
            if (Character.isWhitespace(ch)) {
                i++;
            } else if (Character.isDigit(ch)) {
                String digit = "";
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    digit += String.valueOf(expr.charAt(i));
                    i++;
                }

                output.addLast(new BigDecimal(digit));
            } else if (ch == '(') {
                stack.addFirst(ch);
                i++;
            } else if (ch == ')') {
                while (stack.size() > 0 && stack.peekFirst() != '(') {
                    stack.removeFirst();
                }

                stack.removeFirst();
                i++;
            } else {
                handleOperator(ch, output, stack);
                i++;
            }
        }

        while (stack.size() > 0) {
            evaluateOperator(stack.removeFirst(), output);
        }

        return output.removeFirst();
    }

    private static void handleOperator(char op, LinkedList<BigDecimal> output, LinkedList<Character> stack) {
        int precedence = OP_PRECEDENCE.get(op);
        while (stack.size() > 0 && stack.peekFirst() != '(') {
            int stackPrecedence = OP_PRECEDENCE.get(stack.peekFirst());
            if (stackPrecedence >= precedence) {
                evaluateOperator(stack.removeFirst(), output);
            } else {
                break;
            }
        }

        stack.addFirst(op);
    }

    private static void evaluateOperator(char op, LinkedList<BigDecimal> output) {
        if (op == '*') {
            BigDecimal b = output.removeLast();
            BigDecimal a = output.removeLast();
            output.addLast(a.multiply(b));
        } else if (op == '/') {
            BigDecimal b = output.removeLast();
            BigDecimal a = output.removeLast();
            output.addLast(a.divide(b));
        } else if (op == '-') {
            BigDecimal b = output.removeLast();
            BigDecimal a = output.removeLast();
            output.addLast(a.subtract(b));
        } else if (op == '+') {
            BigDecimal b = output.removeLast();
            BigDecimal a = output.removeLast();
            output.addLast(a.add(b));
        }
    }
}
