import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Arrays;

/**
 * Dailyprogrammer: 14 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/q2v2k/2232012_challenge_14_easy/
 */
public class Easy {

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int[] elements = new int[n];
        for (int i = 0; i < n; i++) {
            String line = in.readLine();
            if (line == null) throw new IllegalStateException(
                String.format("%d must be provided, %d was provided", n, i + 1));
            elements[i] = Integer.parseInt(line.trim());
        }

        for (int i = 0; i < elements.length; i += k) {
            int ck = i + k < elements.length ? k : elements.length - i;
            for (int j = 0; j < ck / 2; j++) {
                int tmp = elements[i + j];
                elements[i + j] = elements[i + ck - j - 1];
                elements[i + ck - j - 1] = tmp;
            }
        }

        System.out.println(Arrays.toString(elements));
    }
}
