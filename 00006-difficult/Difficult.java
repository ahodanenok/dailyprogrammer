import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Dailyprogrammer: 6 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pp7vo/2142012_challenge_6_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Provide at least one heap");
            System.exit(-1);
        }

        int[] heaps = new int[args.length];
        try {
            for (int i = 0; i < args.length; i++) {
                heaps[i] = Integer.parseInt(args[i]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Heaps are represented by integers");
            System.exit(-1);
        }

        for (int heap : heaps) {
            if (heap <= 0) {
                System.out.println("Heap sizes must be greater than 0");
                System.exit(-1);
            }
        }

        int playerIdx = 0;
        Player active;
        List<Player> players = Arrays.asList(new HumanPlayer("user1"), new AIPlayer("ai1"));
        while (true) {
            active = players.get(playerIdx);
            System.out.println("Heaps: " + Arrays.toString(heaps));
            active.makeMove(heaps);

            int emptyCount = 0;
            for (int heap : heaps) {
                if (heap == 0) {
                    emptyCount++;
                }
            }

            if (emptyCount == heaps.length) {
                break;
            }

            playerIdx = (playerIdx + 1) % players.size();
        }

        for (Player p : players) {
            p.destroy();
        }

        System.out.println(String.format("Player '%s' lost", active.name));
    }

    private static abstract class Player {

        protected String name;

        Player(String name) {
            this.name = name;
        }

        abstract void makeMove(int[] heaps);
        void destroy() { }
    }

    private static class HumanPlayer extends Player {

        private BufferedReader reader;

        HumanPlayer(String name) {
            super(name);
            this.reader = new BufferedReader(new InputStreamReader(System.in));
        }

        @Override
        public void makeMove(int[] heaps) {
            try {
                int[] move = getMove(heaps);
                heaps[move[0]] -= move[1];
            } catch (IOException e) {
                System.out.println("Can't get move: " + e.getMessage());
                System.exit(-1);
            }
        }

        private int[] getMove(int[] heaps) throws IOException {
            int[] move = new int[2];
            while (true) {
                System.out.print(name + ", your move: ");
                String moveStr = reader.readLine();
                String[] parts = moveStr.split("\\s+");
                if (parts.length != 2) {
                    System.out.println("Illegal move: " + moveStr);
                    continue;
                }

                int heap;
                try {
                    heap = Integer.parseInt(parts[0].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Heap number must be integer");
                    continue;
                }

                if (heap <= 0 || heap > heaps.length) {
                    System.out.println("Heap number must greater > 0 and <= heaps count");
                    continue;
                }

                heap--;

                int count;
                try {
                    count = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Items count must integer");
                    continue;
                }

                if (count <= 0) {
                    System.out.println("Count must greater > 0");
                    continue;
                }

                if (heaps[heap] < count) {
                    System.out.println("Not enough items in heap");
                    continue;
                }

                move[0] = heap;
                move[1] = count;
                break;
            }

            return move;
        }

        @Override
        public void destroy() {
            try {
                reader.close();
            } catch (IOException e) { }
        }
    }

    private static class AIPlayer extends Player {

        AIPlayer(String name) {
            super(name);
        }

        @Override
        public void makeMove(int[] heaps) {
            int[] move = getMove(heaps);
            System.out.println(String.format("%s took %d from heap %d", name, move[1], (move[0] + 1)));
            heaps[move[0]] -= move[1];
        }

        private int[] getMove(int[] heaps) {
            int nimSum = heaps[0];
            for (int i = 1; i < heaps.length; i++) {
                nimSum ^= heaps[i];
            }

            System.out.println("ai:debug: heaps=" + Arrays.toString(heaps));
            System.out.println("ai:debug: nimSum=" + nimSum);

            int[] heapNimSum = new int[heaps.length];
            for (int i = 0; i < heaps.length; i++) {
                heapNimSum[i] = heaps[i] ^ nimSum;
            }

            System.out.println("ai:debug: nimSums by heap=" + Arrays.toString(heapNimSum));

            int heap = -1;
            for (int i = 0; i < heaps.length; i++) {
                if (heaps[i] > 0 && heapNimSum[i] < heaps[i]) {
                    heap = i;
                    break;
                }
            }

            if (heap == -1) {
                for (int i = 0; i < heaps.length; i++) {
                    if (heaps[i] > 0 && heapNimSum[i] == heaps[i]) {
                        heap = i;
                        break;
                    }
                }
            }

            assert heap >= 0 && heap < heaps.length;
            System.out.println("ai:debug: selected heap=" + heap);

            if (heapNimSum[heap] == 0) {
                return new int[] { heap, Math.max(heaps[heap] - 1, 1) };
            } else if (heapNimSum[heap] == 1) {
                return new int[] { heap, heaps[heap] };
            } else {
                return new int[] { heap, heaps[heap] - heapNimSum[heap] };
            }
        }
    }
}
