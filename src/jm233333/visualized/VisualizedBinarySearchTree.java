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
        ;
    }
}
