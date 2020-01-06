package jm233333.visualized;

/**
 * The {@code VisualizedList} class defines the data structure {@code List} for visualizing.
 * Extended from abstract class {@code VisualizedDataStructure}.
 */
public class VisualizedList extends VisualizedDataStructure {

    static class Node {
        int value;
        Node next;
        Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
        Node(int value) {
            this(value, null);
        }
    }

    private Node head = null;

    @Override
    void createVisual() {
        ;
    }

    public void push_front(int value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
        } else {
            node.next = head.next;
            head.next = node;
        }
    }

    public void insert(int index, int value) {
        ;
    }

    private void insert(Node p, int value) {
        ;
    }
}
