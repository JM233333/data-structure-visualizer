package jm233333.visualized;

import jm233333.Director;
import jm233333.ui.CodeTracker;
import jm233333.visual.VisualList;

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
            next = null;
        }
    }

    private Node head = null;

    public VisualizedList() {
        super();
    }

    @Override
    void createVisual() {
        createVisualList(getName());
    }

    public void pushFront(int value) {
        // create new node
        Node node = new Node(value);
        getVisualList(getName()).cacheNode(0, value);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        // push front
        if (head == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            head = node;
        } else {
            trackCodeEntrance(getCodeCurrentMethod() + "_if/else");
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            node.next = head.next;
            getVisualList(getName()).setPointerNext(VisualList.CACHED_NODE, 0);
            getVisualList(getName()).moveRestNodes(0, 1);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            head.next = node;
        }
        getVisualList(getName()).insertCachedNode(0);
        trackCodeEntrance(getCodeCurrentMethod() + "_end");
        Director.getInstance().playAnimation();
    }

    public void insert(int index, int value) {
        // special judge
        if (index <= 0) {
            trackCodeMethodBeginning("pushFront");
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
        getVisualList("List").insertNode(index, value);
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
        getVisualList("List").popFrontNode();
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
        getVisualList("List").eraseNode(index);
    }
}
