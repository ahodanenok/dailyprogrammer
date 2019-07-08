/**
 * Dailyprogrammer: 36 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/ruiob/452012_challenge_36_easy/
 */
public class Easy {

    private static final int LOCKERS_COUNT = 1000;
    private static final int STUDENTS_COUNT = 1000;

    public static void main(String[] args) {
        boolean[] lockers = new boolean[LOCKERS_COUNT];
        for (int i = 0; i < STUDENTS_COUNT; i++) {
            for (int j = 0; j < lockers.length; j = j + i + 1) {
                lockers[j] = !lockers[j];
            }
        }

        int openedCount = 0;
        for (boolean opened : lockers) {
            if (opened) {
                openedCount++;
            }
        }

        System.out.println("Number of opened lockers: " + openedCount);
        System.out.print("Opened lockers: ");
        int printedCount = 0;
        for (int i = 0; i < lockers.length; i++) {
            if (lockers[i]) {
                System.out.print(i);
                printedCount++;
                if (printedCount < openedCount) {
                    System.out.print(", ");
                }
            }
        }
    }
}
