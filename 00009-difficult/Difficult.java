/**
 * Dailyprogrammer: 9 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pu2c0/2172012_challenge_9_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        String sequence = "1";
        for (int i = 0; i < n; i++) {
            System.out.println(sequence);
            sequence = name(sequence);
        }
    }

    private static String name(String sequence) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 1; i < sequence.length(); i++) {
            if (sequence.charAt(i) == sequence.charAt(i - 1)) {
                count++;
            } else {
                sb.append(count).append(sequence.charAt(i - 1));
                count = 1;
            }
        }

        if (count > 0) {
            sb.append(count).append(sequence.charAt(sequence.length() - 1));
        }

        return sb.toString();
    }
}
