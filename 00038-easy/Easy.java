/**
 * Dailyprogrammer: 38 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/s2no2/4102012_challenge_38_easy/
 */
public class Easy {

    public static void main(String[] args) {
        int[][] graph = {
            {-1, 2, 1, -1, 4},
            {2, -1, -1, 3, -1},
            {1, -1, -1, 2, 3},
            {-1, 3, 2, -1, 7},
            {4, -1, 3, 2, -1}
        };
        boolean[] visited = new boolean[graph.length];
        int[] distances = new int[graph.length];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE;
        }

        int currNode = 1;
        int destNode = 4;

        distances[currNode] = 0;

        search:
        while (true) {
            visited[currNode] = true;

            int next = -1;
            int minDistance = Integer.MAX_VALUE;
            for (int i = 0; i < graph[currNode].length; i++) {
                if (graph[currNode][i] != -1 && !visited[i]) {
                    int distanceToNode = distances[currNode] + graph[currNode][i];                    
                    if (distanceToNode < distances[i]) {
                        distances[i] = distanceToNode;
                    }

                    if (i == destNode) {
                        break search;
                    }

                    if (distances[i] < minDistance) {
                        minDistance = distances[i];
                        next = i;
                    }
                }
            }

            if (next == -1) break;
            currNode = next;
        }

        System.out.println(java.util.Arrays.toString(distances));
        System.out.println(distances[destNode]);
    }
}
