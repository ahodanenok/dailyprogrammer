import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Dailyprogrammer: 21 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/qp4jv/392012_challenge_21_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        Map<String, List<String>> smartness = readSmartness();
        List<String> sortedBySmartness = sortGraph(smartness);
        for (String name : sortedBySmartness) {
            System.out.println(name);
        }
    }

    private static Map<String, List<String>> readSmartness() {
        Map<String, List<String>> smartness = new HashMap<String, List<String>>();

        Scanner scanner = new Scanner(System.in);
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] names = line.split(":");
            String name = names[0].trim();
            String name2 = names[1].trim();
            if (!smartness.containsKey(name)) {
                smartness.put(name, new ArrayList<String>());
            } 

            smartness.get(name).add(name2);
            if (!smartness.containsKey(name2)) {
                smartness.put(name2, new ArrayList<String>());
            }
        }

        return smartness;
    }

    private static boolean isReferenced(String node, Map<String, List<String>> graph) {
        for (String n : graph.keySet()) {
            if (graph.get(n).contains(node)) {
                return true;
            }
        }

        return false;
    }

    private static List<String> sortGraph(Map<String, List<String>> graph) {
        List<String> sortedNodes = new ArrayList<String>(graph.size());
        List<String> topLevelNodes = new ArrayList<String>();
        for (String n : graph.keySet()) {
            if (!isReferenced(n, graph)) {
                topLevelNodes.add(n);
            }
        }

        while (topLevelNodes.size() > 0) {
            String node = topLevelNodes.remove(0);
            sortedNodes.add(node);
            graph.remove(node);
            for (String n: graph.keySet()) {
                if (!isReferenced(n, graph) && !topLevelNodes.contains(n)) {
                    topLevelNodes.add(n);
                }
            }
        }

        return sortedNodes;
    }
}
