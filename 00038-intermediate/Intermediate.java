import java.util.LinkedList;

/**
 * Dailyprogrammer: 38 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/s2na8/4102012_challenge_38_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        String expr = args[0];
        System.out.println(toRPN(expr));
    }

    private static String toRPN(String expr) {
        StringBuilder rpn = new StringBuilder();
        LinkedList<Character> stack = new LinkedList<Character>();
        char ch;
        int pos = 0;

        while (pos < expr.length()) {
            ch = expr.charAt(pos);
            if (Character.isWhitespace(ch)) {
                pos++;
                continue;
            }

            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (stack.peek() != '(' && !stack.isEmpty()) {
                    rpn.append(stack.pop());
                }

                if (stack.isEmpty())  throw new IllegalArgumentException("Illegal expression: mismatched parenthesis");
                stack.pop();
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    rpn.append(stack.pop());
                }

                stack.push(ch);
            } else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                rpn.append(ch);
            } else {
                throw new IllegalArgumentException(
                    String.format("Illegal expression, at %d '%s'", pos, expr.substring(pos)));
            }

            pos++;
        }

        while (!stack.isEmpty()) {
            rpn.append(stack.pop());
        }

        return rpn.toString();
    }
}
