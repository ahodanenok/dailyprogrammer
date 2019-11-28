import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

/**
 * Dailyprogrammer: 42 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/socdy/4232012_challenge_42_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        UF uf = new UF(50);
        uf.connect(0, 22);
        uf.connect(0, 39);
        uf.connect(0, 47);
        uf.connect(1, 5);
        uf.connect(2, 38);
        uf.connect(2, 39);
        uf.connect(4, 23);
        uf.connect(4, 33);
        uf.connect(5, 19);
        uf.connect(6, 17);
        uf.connect(6, 35);
        uf.connect(6, 45);
        uf.connect(7, 49);
        uf.connect(8, 24);
        uf.connect(8, 40);
        uf.connect(9, 25);
        uf.connect(7, 10);
        uf.connect(10, 11);
        uf.connect(10, 21);
        uf.connect(11, 30);
        uf.connect(12, 32);
        uf.connect(13, 27);
        uf.connect(14, 33);
        uf.connect(14, 36);
        uf.connect(15, 49);
        uf.connect(16, 48);
        uf.connect(18, 37);
        uf.connect(18, 45);
        uf.connect(19, 24);
        uf.connect(19, 40);
        uf.connect(20, 39);
        uf.connect(21, 34);
        uf.connect(25, 36);
        uf.connect(26, 27);
        uf.connect(26, 31);
        uf.connect(26, 32);
        uf.connect(28, 48);
        uf.connect(29, 36);
        uf.connect(29, 41);
        uf.connect(35, 43);
        uf.connect(38, 42);
        uf.connect(39, 44);
        uf.connect(43, 46);
        uf.connect(48, 49);

        System.out.println("Islands count: " + uf.getIslandsCount());
        for (Map.Entry<Integer, List<Integer>> entry : uf.getVerticesByIsland().entrySet()) {
            System.out.println(String.format("island %s: %s", entry.getKey(), entry.getValue()));
        }


        int n = 1000000;
        UF ufLarge = new UF(n);
        Set<Edge> addedEdges = new HashSet<Edge>();
        Random random = new Random(System.currentTimeMillis());
        while (ufLarge.getIslandsCount() > 1) {
            Edge edge;
            do {
                edge = new Edge(random.nextInt(n), random.nextInt(n));
            } while (edge.from == edge.to || addedEdges.contains(edge));

            ufLarge.connect(edge.from, edge.to);
            addedEdges.add(edge);
            addedEdges.add(new Edge(edge.to, edge.from));
        }

        System.out.println(addedEdges.size());
    }

    private static class Edge {

        private final int from;
        private final int to;

        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object obj) {
            Edge other = (Edge) obj;
            return from == other.from && to == other.to;
        }

        @Override
        public int hashCode() {
            return 31 * from + to;
        }
    }

    private static class UF {

        private int islandsCount;
        private int[] vertices;
        private int[] height;

        UF(int n) {
            this.vertices = new int[n];
            for (int i = 0; i < this.vertices.length; i++) {
                this.vertices[i] = i;
            }
            this.islandsCount = n;
        }

        private int getIsland(int a) {
            while (vertices[a] != a) {
                a = vertices[a];
            }

            return a;
        }

        Map<Integer, List<Integer>> getVerticesByIsland() {
            Map<Integer, List<Integer>> islands = new HashMap<Integer, List<Integer>>();
            for (int i = 0; i < vertices.length; i++) {
                int island = getIsland(i);
                List<Integer> verticesInIsland = islands.get(island);
                if (verticesInIsland == null) {
                    verticesInIsland = new ArrayList<Integer>();
                    islands.put(island, verticesInIsland);
                }

                verticesInIsland.add(i);
            }

            return islands;
        }

        int getIslandsCount() {
            return islandsCount;
        }

        boolean isConnected(int a, int b) {
            return getIsland(a) == getIsland(b);
        }

        void connect(int a, int b) {
            int islandA = getIsland(a);
            int islandB = getIsland(b);
            if (islandA == islandB) return;

            if (islandA < islandB) {
                vertices[islandA] = islandB;
            } else {
                vertices[islandB] = islandA;
            }

            islandsCount--;
        }
    }
}
