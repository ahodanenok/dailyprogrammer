/**
 * Dailyprogrammer: 42 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/sobna/4232012_challenge_42_easy/
 */
public class Easy {

    private static final String[] DAYS = new String[] {
        "first",
        "second",
        "third",
        "fourth",
        "fifth",
        "sixth",
        "seventh",
        "eighth",
        "nineth",
        "tenth",
        "eleventh",
        "twelfth"
    };

    private static final String[] GIFTS = new String[] {
        "A partridge in a pear tree",
        "Two turtle doves",
        "Three French hens",
        "Four calling birds",
        "Five gold rings",
        "Six geese a-laying",
        "Seven swans a-swimming",
        "Eight maids a-milking",
        "Nine ladies dancing",
        "Ten lords a-leaping",
        "Eleven pipers piping",
        "Twelve drummers drumming"
    };

    public static void main(String[] args) {
        for (int i = 0; i < 12; i++) {
            System.out.println(String.format("On the %s day of Christmas my true love sent to me", DAYS[i]));
            if (i > 0) {
                for (int j = i; j > 0; j--) {
                    System.out.println(String.format("%s%s", GIFTS[j], i > 1 ? "," : ""));
                }
                System.out.println(String.format("And a %s.", GIFTS[0].toLowerCase()));
            } else {
                System.out.println(String.format("%s.", GIFTS[0]));
            }
            System.out.println();
        }
    }
}
