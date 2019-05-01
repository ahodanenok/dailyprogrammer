import java.util.Calendar;
import java.util.Scanner;
import java.text.SimpleDateFormat;

/**
 * Dailyprogrammer: 27 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/r0r4v/3172012_challenge_27_difficult/
 */
public class Difficult {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss");

    public static void main(String[] args) {
        String isoDate = args[0];

        Calendar calendar = parseIsoDate(isoDate);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">");
            String cmd = scanner.nextLine();
            System.out.println(cmd);
            String[] parts = cmd.split(" ");

            int value = Integer.parseInt(parts[0].trim());
            String unit = parts[1].trim();
            if ("year".equals(unit)) {
                calendar.add(Calendar.YEAR, value);
            } else if ("month".equals(unit)) {
                calendar.add(Calendar.MONTH, value);
            } else if ("week".equals(unit)) {
                calendar.add(Calendar.WEEK_OF_YEAR, value);
            } else if ("day".equals(unit)) {
                calendar.add(Calendar.DAY_OF_MONTH, value);
            } else if ("hour".equals(unit)) {
                calendar.add(Calendar.HOUR, value);
            } else if ("minute".equals(unit)) {
                calendar.add(Calendar.MINUTE, value);
            } else if ("second".equals(unit)) {
                calendar.add(Calendar.SECOND, value);
            } else {
                throw new IllegalStateException("Unknown unit: " + unit);
            }

            System.out.println(DATE_FORMAT.format(calendar.getTime()));
        }
    }

    private static Calendar parseIsoDate(String isoDate) {
        String[] dateTime = isoDate.split("T");
        String[] dateParts = dateTime[0].split("-");
        String[] timeParts = dateTime[1].split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[2]));
        calendar.set(Calendar.HOUR, Integer.parseInt(timeParts[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(timeParts[2]));

        return calendar;
    }
}
