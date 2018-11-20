/**
 * Dailyprogrammer: 20 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/qnkpp/382012_challenge_20_intermediate/
 */
public class Intermediate {

    private static final int MONTHS_IN_YEAR = 12;
    private static final int DAYS_IN_YEAR = 365;
    private static final int HOURS_IN_YEAR = DAYS_IN_YEAR * 24;
    private static final int MINUTES_IN_YEAR = HOURS_IN_YEAR * 60;

    public static void main(String[] args) {
        int age = Integer.parseInt(args[0]);
        System.out.println(String.format("months: %d, days: %d, hours: %d, and minutes: %d",
            age * MONTHS_IN_YEAR,
            age * DAYS_IN_YEAR,
            age * HOURS_IN_YEAR,
            age * MINUTES_IN_YEAR));
    }
}
