package jm233333.visualized;

/**
 * The {@code VisualizedBinarySearchTree} class defines the data structure {@code BinarySearchTree} for visualizing.
 * Extended from abstract class {@code VDS}.
 */
public class VisualizedBinarySearchTree extends VDS {

    static class Node {
        int value;
        Node left, right;
        int depth;
        Node(int value) {
            this.value = value;
            left = null;
            right = null;
            depth = 0;
        }
    }

    private static final int MAX_DEPTH = 4;

    private Node root = null;

    public VisualizedBinarySearchTree() {
        super();
    }

    @Override
    public void createVisual() {
        createVisualBST(getName());
    }

    public Node find(int value) {
        return null;
    }

    public void insert(int value) {
        if (root == null) {
            root = new Node(value);
            root.depth = 0;
            getVisualBST(getName()).insertNode(value);
            return;
        }
        insert(root, value);
    }
    private void insert(Node p, int value) {
        // assert
        assert (p != null);
        if (p.depth == MAX_DEPTH) {
            System.out.printf("Sorry, depth cannot be higher than %d.\n", MAX_DEPTH);
            return;
        }
        // deal with duplication
        if (value == p.value) {
            return;
        }
        // discussion
        if (value < p.value) {
            if (p.left == null) {
                p.left = new Node(value);
                p.left.depth = p.depth + 1;
                getVisualBST(getName()).insertNode(value);
            } else {
                insert(p.left, value);
            }
        } else {
            if (p.right == null) {
                p.right = new Node(value);
                p.right.depth = p.depth + 1;
                getVisualBST(getName()).insertNode(value);
            } else {
                insert(p.right, value);
            }
        }
    }
}
