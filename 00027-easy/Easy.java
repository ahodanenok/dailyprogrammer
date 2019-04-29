/**
 * Dailyprogrammer: 27 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/r0r3h/3172012_challenge_27_easy/
 */
public class Easy {

    public static void main(String[] args) {
        int year = Integer.parseInt(args[0]);
        System.out.println("Century:   " + ((year / 100) + 1));
        System.out.println("Leap year: " + (isLeapYear(year) ? "yes" : "no"));
    }

    private static boolean isLeapYear(int year) {
        if (year % 4 != 0) return false;
        if (year % 100 != 0) return true;
        if (year % 400 != 0) return false;
        return true;
    }
}
