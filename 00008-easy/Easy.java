/**
 * Dailyprogrammer: 8 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/pserp/2162012_challenge_8_easy/
 */
public class Easy {

    private static final String VERSE_START = "%1$s of beer on the wall, %1$s of beer.";
    private static final String VERSE_END = "Take one down and pass it around, %s of beer on the wall.";

    private static final String FINAL_VERSE_START = "No more bottles of beer on the wall, no more bottles of beer.";
    private static final String FINAL_VERSE_END = "Go to the store and buy some more, %s of beer on the wall.";

    public static void main(String[] args) {
        boolean singleLine = true;
        int originalBottles = 99;
        int bottles = originalBottles;

        while (bottles > 0) {
            print(String.format(VERSE_START, pronounceBottles(bottles)), singleLine);
            print(String.format(VERSE_END, pronounceBottles(--bottles)), singleLine);
            print("", singleLine);
        }

        print(FINAL_VERSE_START, singleLine);
        print(String.format(FINAL_VERSE_END, pronounceBottles(originalBottles)), singleLine);
    }

    private static String pronounceBottles(int bottles) {
        if (bottles == 0) {
            return "no more bottles";
        } else {
            return bottles + (bottles == 1 ? " bottle" : " bottles");
        }
    }

    private static void print(String str, boolean singleLine) {
        if (singleLine) {
            System.out.print(str);
        } else {
            System.out.println(str);
        }
    }
}
