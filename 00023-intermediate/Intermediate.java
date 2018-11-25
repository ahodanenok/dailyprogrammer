/**
 * Dailyprogrammer: 23 - intermediate
 * 
 */
public class Intermediate {

    public static void main(String[] args) {
        for (int n = 1; n < 44; n++) {
            if (!isMcNugget(n)) {
                System.out.print(n + " ");
            }
        }
    }

    private static boolean isMcNugget(int n) {
        if (n < 0) return false;
        else if (n == 0) return true;
        else return isMcNugget(n - 6) || isMcNugget(n - 9) || isMcNugget(n - 20);
    }
}
