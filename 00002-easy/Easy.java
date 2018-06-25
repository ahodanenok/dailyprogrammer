import java.util.Scanner;

/**
 * Dailyprogrammer: 2 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pjbj8/easy_challenge_2/
 */
public class Easy {

    // F = M * A;
    public static void main(String[] args) {
        System.out.print("calculate (f, m, a):");

        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        if ("f".equals(answer)) {
            System.out.print("m=");
            double m = scanner.nextDouble();
            System.out.print("a=");
            double a = scanner.nextDouble();
            System.out.println("f=" + f(m ,a));
        } else if ("m".equals(answer)) {
            System.out.print("f=");
            double f = scanner.nextDouble();
            System.out.print("a=");
            double a = scanner.nextDouble();
            System.out.println("m=" + m(f ,a));
        } else if ("a".equals(answer)) {
            System.out.print("f=");
            double f = scanner.nextDouble();
            System.out.print("m=");
            double m = scanner.nextDouble();
            System.out.println("a=" + a(f ,m));
        } else {
            System.out.println("unknown answer: " + answer);
            System.exit(-1);
        }
    }

    private static double f(double m, double a) {
        return m * a;
    }

    private static double m(double f, double a) {
        return f / a;
    }

    private static double a(double f, double m) {
        return f / m;
    }
}
