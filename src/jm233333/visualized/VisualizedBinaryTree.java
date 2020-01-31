package jm233333.visualized;

/**
 * The {@code VisualizedList} class defines the data structure {@code List} for visualizing.
 * Extended from abstract class {@code VisualizedDataStructure}.
 */
public class VisualizedBinaryTree extends VisualizedDataStructure {

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

    @Override
    void createVisual() {
        //createVisualList("list");
    }
}