import java.util.Map;
import java.util.HashMap;

/**
 * Dailyprogrammer: 25 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/qxuug/3152012_challenge_25_easy/
 */
public class Easy {

    public static void main(String[] args) {
        Map<String, Integer> votes = new HashMap<String, Integer>();
        votes.put("test 1", 10);
        votes.put("test 2", 5);
        votes.put("test 3", 16);
        System.out.println(winner(votes));
    }

    private static String winner(Map<String, Integer> votes) {
        String winner = null;
        int winnerVotes = Integer.MIN_VALUE;
        int totalVotes = 0;

        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            if (entry.getValue() > winnerVotes) {
                winner = entry.getKey();
                winnerVotes = entry.getValue();
            }

            totalVotes += entry.getValue();
        }

        return (winnerVotes > totalVotes / 2.0) ? winner : null;
    }
}
