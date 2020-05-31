import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Dailyprogrammer: 43 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/sq3r7/4242012_challenge_43_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        Map<Integer, Integer> links = new HashMap<Integer, Integer>();
        links.put(1, 38);
        links.put(4, 14);
        links.put(9, 31);
        links.put(16, 6);
        links.put(21, 42);
        links.put(28, 84);
        links.put(36, 44);
        links.put(47, 26);
        links.put(49, 11);
        links.put(51, 67);
        links.put(56, 53);
        links.put(62, 19);
        links.put(64, 60);
        links.put(71, 91);
        links.put(80, 100);
        links.put(87, 24);
        links.put(93, 73);
        links.put(95, 75);
        links.put(98, 78);

        List<Integer> rolls = minRolls(links);
        System.out.println("Rolls for fastest route: " + rolls);
        System.out.println("Average number of rolls for single player: " + averageRolls(links, 1, 1000000));
        System.out.println("Average number of rolls for two players: " + averageRolls(links, 2, 1000000));
        System.out.println("Average number of rolls for three players: " + averageRolls(links, 3, 1000000));
    }

    private static List<Integer> minRolls(Map<Integer, Integer> links) {
        List<Integer> rolls = new ArrayList<Integer>();

        int pos = 0;
        while (pos != 100) {
            int roll = 0;
            int maxPos = Integer.MIN_VALUE;
            for (int r = 1; r < 7; r++) {
                int nextPos = move(pos, r, links);
                if (nextPos > maxPos) {
                    maxPos = nextPos;
                    roll = r;
                }
            }

            rolls.add(roll);
            pos = maxPos;
        }

        return rolls;
    }

    private static double averageRolls(Map<Integer, Integer> links, int playersCount, int experiments) {
        int[] results = new int[experiments];
        for (int i = 0; i < experiments; i++) {
            int[] playersPos = new int[playersCount];
            int rollsCount = 0;

            game:
            while (true) {
                for (int p = 0; p < playersPos.length; p++) {
                    playersPos[p] = move(playersPos[p], 1 + (int) Math.floor(Math.random() * 7), links);
                    rollsCount++;
                    if (playersPos[p] == 100) {
                        break game;
                    }
                }
            }

            results[i] = rollsCount;
        }

        int sum = 0;
        for (int result : results) {
            sum += result;
        }

        return sum / (double) results.length;
    }

    private static int move(int pos, int roll, Map<Integer, Integer> links) {
        int nextPos = pos + roll;
        if (links.containsKey(nextPos)) {
            nextPos = links.get(nextPos);
        }

        if (nextPos > 100) {
            return pos;
        } else {
            return nextPos;
        }
    }
}
