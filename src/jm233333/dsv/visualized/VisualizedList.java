package jm233333.dsv.visualized;

import jm233333.dsv.ui.CodeTracker;
import jm233333.dsv.visual.ColorScheme;
import jm233333.dsv.visual.VisualList;

/**
 * Class {@code VisualizedList} defines the data structure {@code List} for visualizing.
 * Extended from abstract class {@link VDS}.
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
        super("List");
    }

    @Override
    public void createVisual() {
        createVisualList(getName());
    }

    private Node getNode(int index) {
        // initialize pointer
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        Node p = head;
        if (p != null) getVisualList(getName()).setColorScheme(0, ColorScheme.HIGHLIGHT);
        // loop
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        for (int i = 0; i < index; i ++) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            p = p.next;
            if (p == null) {
                getVisualList(getName()).setColorScheme(i, ColorScheme.DEFAULT);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                return null;
            }
            getVisualList(getName()).setColorScheme(i, ColorScheme.DEFAULT);
            getVisualList(getName()).setColorScheme(i + 1, ColorScheme.HIGHLIGHT, true);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackCodeEntrance(getCurrentMethod() + "_loop_begin");
        }
        trackCodeEntrance(getCurrentMethod() + "_loop_end");
        return p;
    }

    public int get(int index) {
        // get node
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        trackMethodCall("getNode");
        Node p = getNode(index);
        trackMethodReturn();
        // check if out-of-bound
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Out of bound.");
            return 0xffffffff;
        }
        // return
        trackCodeEntrance(getCurrentMethod() + "_return");
        outputMessageReturn(p.value);
        getVisualList(getName()).setColorScheme(index, ColorScheme.DEFAULT);
        return p.value;
    }

    public Node find(int value) {
        Node p = head;
        int index = 0;
        if (p != null) getVisualList(getName()).setColorScheme(0, ColorScheme.HIGHLIGHT);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        while (p != null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            if (p.value == value) {
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualList(getName()).setColorScheme(index, ColorScheme.DEFAULT);
                outputMessageReturn("true (Node)(value = " + p.value + ")");
                return p;
            }
            trackCodeEntrance(getCurrentMethod() + "_if_end");
            p = p.next;
            getVisualList(getName()).setColorScheme(index, ColorScheme.DEFAULT);
            if (p != null) getVisualList(getName()).setColorScheme(index + 1, ColorScheme.HIGHLIGHT, true);
            index ++;
            trackCodeEntrance(getCurrentMethod() + "_loop_begin");
        }
        trackCodeEntrance(getCurrentMethod() + "_loop_end");
        outputMessageReturn("false");
        return null;
    }

    public void pushFront(int value) {
        // create new node
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        Node node = new Node(value);
        getVisualList(getName()).setCachedNode(0, value);
        // push front (st.1)
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        node.next = head;
        if (head != null) {
            getVisualList(getName()).setPointerNext(VisualList.CACHED_NODE, 0);
        }
        getVisualList(getName()).moveRestNodes(0, 1);
        // push front (st.2)
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        head = node;
        getVisualList(getName()).insertCachedNode(0);
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }

    public void insert(int index, int value) {
        // special judge
        if (index <= 0) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackMethodCall("pushFront");
            pushFront(value);
            trackMethodReturn();
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        trackCodeEntrance(getCurrentMethod() + "_getNode");
        // get position
        trackMethodCall("getNode");
        Node prv = getNode(index - 1);
        trackMethodReturn();
        // check validity
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        if (prv == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Out of bound.");
            return;
        }
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
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
        getVisualList(getName()).setColorScheme(index - 1, ColorScheme.DEFAULT);
    }

    public void popFront() {
        // special judge
        if (head == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Empty list.");
            return;
        }
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
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
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackMethodCall("popFront");
            popFront();
            trackMethodReturn();
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        trackCodeEntrance(getCurrentMethod() + "_getNode");
        // get position
        trackMethodCall("getNode");
        Node prv = getNode(index - 1);
        trackMethodReturn();
        // check validity
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        if (prv == null || prv.next == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Out of bound.");
            return;
        }
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
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
        getVisualList(getName()).setColorScheme(index - 1, ColorScheme.DEFAULT);
    }
}
