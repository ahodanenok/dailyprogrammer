import java.util.Calendar;
import java.util.Locale;

/**
 * Dailyprogrammer: 27 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/r0r46/3172012_challenge_27_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int year = Integer.parseInt(args[0]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.MARCH);
        calendar.set(Calendar.DAY_OF_MONTH, 3);
        System.out.println(String.format("In %d St. Patrick's Day is on %s",
            year, calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)));

        int centuryStartYear = (year / 100) * 100;
        int centuryEndYear = centuryStartYear + 100;
        int patrickOnSaturdayCount = 0;
        for (int y = centuryStartYear; y < centuryEndYear; y++) {
            calendar.set(Calendar.YEAR, y);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                patrickOnSaturdayCount++;
            }
        }

        int century = (year / 100) + 1;
        System.out.println(String.format(
            "Number of times St. Patrick's Day falls on saturday in %d century: %d",
            century, patrickOnSaturdayCount));
    }
}
