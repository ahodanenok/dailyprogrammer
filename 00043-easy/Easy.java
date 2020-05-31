/**
 * Dailyprogrammer 43 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/sq3p9/4242012_challenge_43_easy/
 */
public class Easy {

    public static void main(String[] args) {
        BinaryTree g = new BinaryTree(1, null);
        BinaryTree f = new BinaryTree(2, g);
        BinaryTree e = new BinaryTree(3, g);
        BinaryTree d = new BinaryTree(4, e);
        BinaryTree c = new BinaryTree(5, d);
        BinaryTree b = new BinaryTree(6, d);
        BinaryTree a = new BinaryTree(7, e);

        BinaryTree m = a;
        BinaryTree n = b;

        BinaryTree ancestor = null;
        while (n.parent != null) {
            if (isAncestor(m, n)) {
                ancestor = n;
                break;
            }

            n = n.parent;
        }

        System.out.println(String.format("First common ancestor: %d", ancestor.value));
    }

    private static boolean isAncestor(BinaryTree a, BinaryTree b) {
        while (a.parent != null && a.parent != b) a = a.parent;
        return a.parent == b;
    }

    private static class BinaryTree {

        private final BinaryTree parent;
        private final int value;

        BinaryTree(int value, BinaryTree parent) {
            this.value = value;
            this.parent = parent;
        }
    }
}
