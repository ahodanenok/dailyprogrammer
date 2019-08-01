import java.math.BigInteger;
import java.util.Random;

/**
 * Dailyprogrammer: 38 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/s2mxz/4102012_challenge_38_difficult/
 */
public class Difficult {

    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static void main(String[] args) {
        //BigInteger n = randomInteger(TWO, TWO.pow(32), true);
        BigInteger n = new BigInteger("2074722246773485207821695222107608587480996474721117292752992589912196684750549658310084416732550077");
        System.out.println(n);
        System.out.println(probablyPrime(n, 10) ? "probably prime" : "not prime");
        System.out.println(n.isProbablePrime(10) ? "probably prime" : "not prime");
    }

    private static BigInteger randomInteger(BigInteger from, BigInteger to, boolean onlyOdd) {
        BigInteger upper = to.subtract(from);
        BigInteger n;
        while (true) {
            n = new BigInteger(upper.bitCount(), RANDOM);
            n = n.mod(upper);
            n = from.add(n);

            if (onlyOdd && n.remainder(TWO).compareTo(ZERO) == 0) {
                continue;
            } else {
                break;
            }
        }

        return n;
    }

    private static boolean probablyPrime(BigInteger n, int k) {
        int r = 0;
        BigInteger d = n.subtract(BigInteger.ONE);
        while (d.remainder(TWO).compareTo(ZERO) == 0) {
            d = d.divide(TWO);
            r++;
        }

        loop:
        for (int i = 0; i < k; i++) {
            BigInteger x = randomInteger(TWO, n.subtract(BigInteger.ONE), false).modPow(d, n);
            if (x.compareTo(BigInteger.ONE) == 0
                    || x.compareTo(n.subtract(BigInteger.ONE)) == 0) {
                continue loop;
            }

            for (int j = 0; j < r - 1; j++) {
                x = x.multiply(x).mod(n);
                if (x.compareTo(n.subtract(BigInteger.ONE)) == 0) {
                    continue loop;
                }
            }

            return false;
        }

        return true;
    }
}
