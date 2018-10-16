/**
 * Dailyprogrammer: 13 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pzo4w/2212012_challenge_13_easy/
 */
public class Easy {

    private static final int[] DAYS_IN_MONTHS =      { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static final int[] DAYS_IN_MONTHS_LEAP = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    public static void main(String[] args) {
        int day = Integer.parseInt(args[0]);
        int month = Integer.parseInt(args[1]);
        int year = Integer.parseInt(args[2]);
        System.out.println(dayInYear(day, month, year));
    }

    private static int dayInYear(int day, int month, int year) {
        int[] daysInMonths;
        if (isLeap(year)) {
            daysInMonths = DAYS_IN_MONTHS_LEAP;
        } else {
            daysInMonths = DAYS_IN_MONTHS;
        }

        month--;

        int dayNum = 0;
        for (int m = 0; m < month; m++) {
            dayNum += daysInMonths[m];
        }

        dayNum += day;
        return dayNum;
    }

    private static boolean isLeap(int year) {
        return (year % 4 == 0 
                && (year % 100 != 0
                    || (year % 100 == 0 && year % 400 == 0)));
    }
}
