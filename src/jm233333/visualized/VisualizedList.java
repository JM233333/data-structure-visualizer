package jm233333.visualized;

/**
 * The {@code VisualizedList} class defines the data structure {@code List} for visualizing.
 * Extended from abstract class {@code VisualizedDataStructure}.
 */
public class VisualizedList extends VisualizedDataStructure {

    static class Node {
        int value;
        Node next;
        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head = null;

    @Override
    void createVisual() {
        createVisualList("list");
    }

    public void pushFront(int value) {
        // create new node
        Node node = new Node(value);
        // push front
        if (head == null) {
            head = node;
        } else {
            node.next = head.next;
            head.next = node;
        }
        // play animation
        getVisualList("list").pushFrontNode(value);
    }

    public void insert(int index, int value) {
        // special judge
        if (index <= 0) {
            pushFront(value);
            return;
        }
        if (head == null) {
            System.out.println("INSERT ERROR");
            return;
        }
        // get position
        Node p = head;
        for (int i = 0; i < index - 1; i ++) {
            p = p.next;
            if (p == null) {
                System.out.println("INSERT ERROR");
                return;
            }
        }
        // create new node
        Node node = new Node(value);
        // insert
        node.next = p.next;
        p.next = node;
        // play animation
        getVisualList("list").insertNode(index, value);
    }

    public void popFront() {
        // special judge
        if (head == null) {
            System.out.println("POP FRONT ERROR");
            return;
        }
        // pop front
        head = head.next;
        // play animation
        getVisualList("list").popFrontNode();
    }

    public void erase(int index) {
        // special judge
        if (index <= 0) {
            popFront();
            return;
        }
        if (head == null || head.next == null) {
            System.out.println("ERASE ERROR");
            return;
        }
        // get position
        Node p = head;
        for (int i = 0; i < index - 1; i ++) {
            p = p.next;
            if (p == null || p.next == null) {
                System.out.println("ERASE ERROR");
                return;
            }
        }
        // erase
        p.next = p.next.next;
        // play animation
        getVisualList("list").eraseNode(index);
    }
}
