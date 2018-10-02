/**
 * Dailyprogrammer: 11 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pwons/2192012_challenge_11_easy/
 */
public class Easy {

    public static void main(String[] args) {
        int day = Integer.parseInt(args[0]);
        int month = Integer.parseInt(args[1]);
        int year = Integer.parseInt(args[2]);

        int dw = dayOfTheWeek(day, month, year);
        switch (dw) {
            case 0: System.out.println("Saturday"); break;
            case 1: System.out.println("Sunday"); break;
            case 2: System.out.println("Monday"); break;
            case 3: System.out.println("Tuesday"); break;
            case 4: System.out.println("Wednesday"); break;
            case 5: System.out.println("Thursday"); break;
            case 6: System.out.println("Friday"); break;
            default: throw new IllegalStateException("dw=" + dw);
        }
    }

    private static int dayOfTheWeek(int day, int month, int year) {
        int yy = month < 3 ? year - 1 : year;
        int m = month < 3 ? month + 12 : month;
        int c = yy / 100;
        int y = yy % 100;

        System.out.println(String.format("yy=%d, m=%d, c=%d, y=%d", yy, m, c, y));
        int r = day + (int) ((m + 1) * 2.6) + y + (y / 4) + (c / 4) - (2 * c);
        return (7 + r) % 7;
    }
}
