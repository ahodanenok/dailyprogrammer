import java.util.List;
import java.util.ArrayList;

/**
 * Dailyprogrammer: 41 - intermedaite
 * https://www.reddit.com/r/dailyprogrammer/comments/shpiq/4192012_challenge_41_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        String expr = args[0];
        List<Binomial> binomials = parseExpression(expr);
        System.out.println(binomials.get(0).multiply(binomials.get(1)));
    }

    private static List<Binomial> parseExpression(String expr) {
        List<Binomial> binomials = new ArrayList<Binomial>();

        for (String binomialsExpr : expr.split("\\*")) {
            binomialsExpr = binomialsExpr.trim().substring(1, binomialsExpr.length() - 1);

            List<Term> terms = new ArrayList<Term>();
            for (String termExpr : binomialsExpr.split("\\+")) {
                terms.add(parseTerm(termExpr));
            }

            if (terms.size() == 2) {
                binomials.add(new Binomial(terms.get(0), terms.get(1)));
            } else {
                throw new IllegalArgumentException("Only binomials are supported");
            }
        }

        return binomials;
    }

    private static Term parseTerm(String expr) {
        String[] parts = expr.trim().split("x");
        if (parts.length == 2) {
            return new Term(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        } else if (parts.length == 1) {
            return new Term(Integer.parseInt(parts[0]), (expr.contains("x") ? 1 : 0));
        } else {
            throw new IllegalArgumentException("Illegal term expression: " + expr);
        }
    }

    private static class Binomial {

        private Term a;
        private Term b;

        Binomial(Term a, Term b) {
            this.a = a;
            this.b = b;
        }

        String multiply(Binomial other) {
            List<Term> terms = new ArrayList<Term>();
            terms.add(a.multiply(other.a));
            terms.add(a.multiply(other.b));
            terms.add(b.multiply(other.a));
            terms.add(b.multiply(other.b));

            for (int i = 0; i < terms.size(); i++) {
                Term c = terms.get(i);
                for (int j = i + 1; j < terms.size(); ) {
                    Term d = terms.get(j);
                    if (c.power == d.power) {
                        terms.set(i, new Term(c.coefficient + d.coefficient, c.power));
                        terms.remove(d);
                    } else {
                        j++;
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < terms.size(); i++) {
                sb.append(terms.get(i).displayValue());
                if (i < terms.size() - 1) {
                    sb.append(" + ");
                }
            }

            return sb.toString();
        }
    }

    private static class Term  {

        private final int coefficient;
        private final int power;

        Term(int coefficient, int power) {
            this.coefficient = coefficient;
            this.power = power;
        }

        Term multiply(Term term) {
            return new Term(coefficient * term.coefficient, power + term.power);
        }

        public String displayValue() {
            if (coefficient == 0) {
                return "";
            } else if (power == 0) {
                return coefficient + "";
            } else if (power == 1) {
                return coefficient + "x";
            } else {
                return String.format("%dx%d", coefficient, power);
            }
        }
    }
}
