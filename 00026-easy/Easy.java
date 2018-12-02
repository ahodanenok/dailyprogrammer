/**
 * Dailyprogrammer: 26 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/qzil1/3162012_challenge_26_easy/
 */
public class Easy {

    public static void main(String[] args) {
        String str = args[0];
        String result = "";
        String duplicates = "";

        int i = 0;
        while (i < str.length()) {
            char ch = str.charAt(i);
            result += ch;
            i++;

            String d = "";
            while (i < str.length() && ch == str.charAt(i)) {
                d += str.charAt(i);
                i++;
            }

            if (d.length() > 0) {
                duplicates += d;
            }
        }

        System.out.println(String.format("(%s, %s)", result, duplicates));
    }
}
