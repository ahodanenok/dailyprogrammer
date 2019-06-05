import java.util.Arrays;
import java.util.Random;

/**
 * Dailyprogrammer: 34 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/rmmqx/3312012_challenge_34_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int[] arr;

        arr = generateArray(n);
        System.out.println("Original: " + Arrays.toString(arr));
        System.out.println("Bogosort: " + Arrays.toString(bogosort(arr)));

        System.out.println();

        arr = generateArray(n);
        System.out.println("Original:   " + Arrays.toString(arr));
        System.out.println("Stoogesort: " + Arrays.toString(stoogesort(arr, 0, arr.length - 1)));
    }

    private static int[] generateArray(int n) {
        int[] arr = new int[n];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(arr.length);
        }

        return arr;
    }

    private static int[] bogosort(int[] arr) {
        Random r = new Random(System.currentTimeMillis());
        while (true) {
            boolean sorted = true;
            for (int i = 1; i < arr.length; i++) {
                if (arr[i - 1] > arr[i]) {
                    sorted = false;
                    break;
                }
            }

            if (sorted) {
                break;
            }

            for (int i = 0; i < arr.length; i++) {
                int j = i + r.nextInt(arr.length - i);
                int tmp = arr[j];
                arr[j] = arr[i];
                arr[i] = tmp;
            }
        }

        return arr;
    }

    private static int[] stoogesort(int[] arr, int start, int end) {
        if (arr[start] > arr[end]) {
            int tmp = arr[start];
            arr[start] = arr[end];
            arr[end] = tmp;
        }

        if ((end - start + 1) > 2) {
            int third = (end - start + 1) / 3;
            stoogesort(arr, start, end - third);
            stoogesort(arr, start + third, end);
            stoogesort(arr, start, end - third);
        }

        return arr;
    }
}
