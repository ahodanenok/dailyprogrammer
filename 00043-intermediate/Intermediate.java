/**
 * Dailyprogrammer: 43 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/sq3q0/4242012_challenge_43_intermediate/
 */
public class Intermediate {

    private static final int SUNDAY = 0;
    private static final int MONDAY = 1;
    private static final int TUESDAY = 2;
    private static final int WEDNESDAY = 3;
    private static final int THURSDAY = 4;
    private static final int FRIDAY = 5;
    private static final int SATURDAY = 6;

    private static final String[] DAY_NAMES = new String[] {
        "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    private static int[] DAYS_IN_MONTH = new int[] { 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 30 };
    private static int[] DAYS_IN_MONTH_LEAP = new int[] { 30, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 30 };

    public static void main(String[] args) {
        int day = Integer.parseInt(args[0]);
        int month = Integer.parseInt(args[1]);
        int year = Integer.parseInt(args[2]);

        int days = diff(day, month, 9, 5, year);
        int doomsday = calcDoomsday(year);
        System.out.println(DAY_NAMES[(days + doomsday) % 7]);
    }

    private static int calcDoomsday(int year) {
        return (TUESDAY + 5 * (year % 4) + 4 * (year % 100) + 6 * (year % 400)) % 7;
    }

    private static int diff(int dayFrom, int monthFrom, int dayTo, int monthTo, int year) {
        int daysFrom = dayOfYear(dayFrom, monthFrom, year);
        int daysTo = dayOfYear(dayTo, monthTo, year);
        return (int) Math.abs(daysTo - daysFrom);
    }

    private static int dayOfYear(int day, int month, int year) {
        int[] daysInMonth;
        if (isLeap(year)) {
            daysInMonth = DAYS_IN_MONTH_LEAP;
        } else {
            daysInMonth = DAYS_IN_MONTH;
        }

        int days = 0;
        for (int i = 0; i < month - 1; i++) {
            days += daysInMonth[i];
        }

        return days + day;
    }

    private static boolean isLeap(int year) {
        if (year % 4 != 0) return false;
        else if (year % 100 != 0) return true;
        else if (year % 400 != 0) return false;
        else return true;
    }
}
