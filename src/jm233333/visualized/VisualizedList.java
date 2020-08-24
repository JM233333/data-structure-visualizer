package jm233333.visualized;

import jm233333.ui.CodeTracker;
import jm233333.visual.VisualList;

/**
 * The {@code VisualizedList} class defines the data structure {@code List} for visualizing.
 * Extended from abstract class {@code VDS}.
 */
public class VisualizedList extends VDS {

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
    public void createVisual() {
        createVisualList(getName());
    }

    private Node getNode(int index) {
        Node p = head;
        if (p != null) getVisualList(getName()).setHighlight(0, true);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        for (int i = 0; i < index; i ++) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            p = p.next;
            if (p == null) {
                System.out.println("GET NODE ERROR");
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                return null;
            }
            getVisualList(getName()).setHighlight(i, false);
            getVisualList(getName()).setHighlight(i + 1, true, true);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackCodeEntrance(getCodeCurrentMethod() + "_loop_begin");
        }
        trackCodeEntrance(getCodeCurrentMethod() + "_loop_end");
        return p;
    }

    public int get(int index) {
        trackCodeMethodBeginning("getNode");
        Node p = getNode(index);
        trackCodeSetCurrentMethod("get");
        trackCodeEntrance(getCodeCurrentMethod() + "_return");
        if (p == null) {
            System.out.println("GET ERROR");
            return 0;
        }
        outputMessage(getCodeCurrentMethod() + " " + p.value);
        return p.value;
    }

    public Node find(int value) {
        Node p = head;
        int index = 0;
        if (p != null) getVisualList(getName()).setHighlight(0, true);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        while (p != null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            if (p.value == value) {
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualList(getName()).setHighlight(index, false);
                outputMessage(getCodeCurrentMethod() + " true");
                return p;
            }
            trackCodeEntrance(getCodeCurrentMethod() + "_if_end");
            p = p.next;
            getVisualList(getName()).setHighlight(index, false);
            if (p != null) getVisualList(getName()).setHighlight(index + 1, true, true);
            index ++;
            trackCodeEntrance(getCodeCurrentMethod() + "_loop_begin");
        }
        trackCodeEntrance(getCodeCurrentMethod() + "_loop_end");
        outputMessage(getCodeCurrentMethod() + " false");
        return null;
    }

    public void pushFront(int value) {
        // create new node
        Node node = new Node(value);
        getVisualList(getName()).setCachedNode(0, value);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        // push front
        node.next = head;
        if (head != null) {
            getVisualList(getName()).setPointerNext(VisualList.CACHED_NODE, 0);
        }
        getVisualList(getName()).moveRestNodes(0, 1);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        head = node;
        getVisualList(getName()).insertCachedNode(0);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }

    public void insert(int index, int value) {
        // special judge
        if (index <= 0) {
            trackCodeMethodBeginning("pushFront");
            pushFront(value);
            return;
        }
        trackCodeEntrance(getCodeCurrentMethod() + "_getNode");
        // get position
        trackCodeMethodBeginning("getNode");
        Node prv = getNode(index - 1);
        trackCodeSetCurrentMethod("insert");
        assert prv != null;
        trackCodeEntrance(getCodeCurrentMethod() + "_getNode");
        trackCodeEntrance(getCodeCurrentMethod() + "_main_begin");
        // create new node
        Node p = new Node(value);
        getVisualList(getName()).setCachedNode(index, value);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        // insert
        p.next = prv.next;
        if (prv.next != null) {
            getVisualList(getName()).setPointerNext(VisualList.CACHED_NODE, index);
        }
        getVisualList(getName()).moveRestNodes(index, 1);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        prv.next = p;
        getVisualList(getName()).setPointerNext(index - 1, VisualList.CACHED_NODE);
        getVisualList(getName()).insertCachedNode(index);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        // clear highlight
        getVisualList(getName()).setHighlight(index - 1, false);
    }

    public void popFront() {
        // special judge
        if (head == null) {
            System.out.println("POP FRONT ERROR");
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        trackCodeEntrance(getCodeCurrentMethod() + "_main_begin");
        // pop front
        getVisualList(getName()).markNode(0);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        head = head.next;
        getVisualList(getName()).moveRestNodes(1, -1);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        getVisualList(getName()).eraseMarkedNode();
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }

    public void erase(int index) {
        // special judge
        if (index <= 0) {
            trackCodeMethodBeginning("popFront");
            popFront();
            return;
        }
        trackCodeEntrance(getCodeCurrentMethod() + "_getNode");
        // get position
        trackCodeMethodBeginning("getNode");
        Node prv = getNode(index - 1);
        trackCodeSetCurrentMethod("erase");
        assert prv != null;
        trackCodeEntrance(getCodeCurrentMethod() + "_getNode");
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        // check validity
        if (prv.next == null) {
            System.out.println("ERASE ERROR");
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        trackCodeEntrance(getCodeCurrentMethod() + "_main_begin");
        // erase
        getVisualList(getName()).markNode(index);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        prv.next = prv.next.next;
        if (prv.next != null) {
            getVisualList(getName()).setPointerNext(index - 1, index + 1);
        } else {
            getVisualList(getName()).setPointerNextToNull(index - 1);
        }
        getVisualList(getName()).moveRestNodes(index + 1, -1);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        getVisualList(getName()).eraseMarkedNode();
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        // clear highlight
        getVisualList(getName()).setHighlight(index - 1, false);
    }
}
