import java.util.Arrays;
import java.util.Random;

/**
 * Dailyprogrammer: 14 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/q2kbt/2232012_challenge_14_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int f = Integer.parseInt(args[1]);
        int[] numbers = new int[n];
        int[] numbers2 = new int[n];

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < n; i++) {
            numbers[i] = random.nextInt(n);
            numbers2[i] = numbers[i];
        }

        long time = System.currentTimeMillis();
        sortM(numbers, f);
        System.out.println(f * 2 + " threads: " + ((System.currentTimeMillis() - time) / 1000.0) + "s");

        time = System.currentTimeMillis();
        sort(numbers2);
        System.out.println("single threaded: " + ((System.currentTimeMillis() - time) / 1000.0) + "s");
    }

    private static void sortM(int[] arr, int forks) {
        sortM(arr, new int[arr.length], 0, arr.length, arr.length / forks);
    }

    private static void sortM(int[] arr, int[] tmp, int from, int to, int forkSize) {
        int size = to - from;
        if (size < 2) return;

        int middle = from + size / 2;
        if (size <= forkSize) {
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    sort(arr, tmp, from, middle);
                }
            });
            Thread t2 = new Thread(new Runnable() {
                public void run() {
                    sort(arr, tmp, middle, to);
                }
            });

            try {
                t1.start();
                t2.start();

                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        } else {
            sortM(arr, tmp, from, middle, forkSize);
            sortM(arr, tmp, middle, to, forkSize);
        }

        merge(arr, tmp, from, middle, to);
    }

    private static void sort(int[] arr) {
        sort(arr, new int[arr.length], 0, arr.length);
    }

    private static void sort(int[] arr, int[] tmp, int from, int to) {
        int size = to - from;
        if (size < 2) return;

        int middle = from + size / 2;
        sort(arr, tmp, from, middle);
        sort(arr, tmp, middle, to);
        merge(arr, tmp, from, middle, to);
    }

    private static void merge(int[] arr, int[] tmp, int from, int middle, int to) {
        int i = from;
        int j = middle;
        for (int k = from; k < to; k++) {
            if (i >= middle) tmp[k] = arr[j++];
            else if (j >= to) tmp[k] = arr[i++];
            else if (arr[i] <= arr[j]) tmp[k] = arr[i++];
            else if (arr[i] > arr[j]) tmp[k] = arr[j++];
            else throw new IllegalStateException(
                String.format("k=%d, i=%d, j=%d, from=%d, middle=%d, to=%d", k, i, j, from, middle, to));
        }

        for (int k = from; k < to; k++) {
            arr[k] = tmp[k];
        }
    }
}
