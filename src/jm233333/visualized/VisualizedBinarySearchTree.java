package jm233333.visualized;

/**
 * The {@code VisualizedBinarySearchTree} class defines the data structure {@code BinarySearchTree} for visualizing.
 * Extended from abstract class {@code VisualizedDataStructure}.
 */
public class VisualizedBinarySearchTree extends VisualizedDataStructure {

    static class Node {
        int value;
        Node left, right;
        Node(int value) {
            this.value = value;
            left = null;
            right = null;
        }
    }

    private Node root = null;

    public VisualizedBinarySearchTree() {
        super();
    }

    @Override
    void createVisual() {
//        createVisualBinarySearchTree();
    }

    public Node find(int value) {
        return null;
    }

    public void insert(int value) {
        if (root == null) {
            root = new Node(value);
            return;
        }
        insert(root, value);
    }
    private void insert(Node p, int value) {
        // assert
        assert (p != null);
        // deal with duplication
        if (value == p.value) {
            return;
        }
        // discussion
        if (value < p.value) {
            if (p.left == null) {
                p.left = new Node(value);
            } else {
                insert(p.left, value);
            }
        } else {
            if (p.right == null) {
                p.right = new Node(value);
            } else {
                insert(p.right, value);
            }
        }
    }
}
