import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * Dailyprogrammer: 16 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/q8ays/2272012_challenge_16_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        int to = Integer.parseInt(args[0]);
        Map<Integer, List<Integer>> sieve = new HashMap<Integer, List<Integer>>();
        for (int n = 2; n < to; n++) {
            List<Integer> list = sieve.get(n);
            if (list != null) {
                sieve.remove(n);
                for (int prime : list) {
                    if (sieve.containsKey(n + prime)) {
                        sieve.get(n + prime).add(prime);
                    } else {
                        List<Integer> primeList = new ArrayList<Integer>();
                        primeList.add(prime);
                        sieve.put(n + prime, primeList);
                    }
                }
            } else {
                list = new ArrayList<Integer>();
                list.add(n);
                sieve.put(n * n, list);
            }
        }

        List<Integer> primes = new ArrayList<Integer>(sieve.size());
        for (List<Integer> numbers : sieve.values()) {
            primes.addAll(numbers);
        }
                            
        Collections.sort(primes);
        for (int prime : primes) {
            System.out.println(prime);
        }
    }
}
