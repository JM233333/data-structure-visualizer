package jm233333.dsv.visualized;

import jm233333.dsv.Global;
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
            return Global.INFINITY;
        }
        // return
        trackCodeEntrance(getCurrentMethod() + "_return");
        outputMessageReturn(p.value);
        getVisualList(getName()).setColorScheme(index, ColorScheme.DEFAULT);
        return p.value;
    }

    public Node find(int value) {
        // initialize pointer
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        Node p = head;
        if (p != null) getVisualList(getName()).setColorScheme(0, ColorScheme.HIGHLIGHT);
        // loop
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        int index = 0;
        while (p != null) {
            // if found
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            if (p.value == value) {
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualList(getName()).setColorScheme(index, ColorScheme.DEFAULT);
                outputMessageReturn("(Node)(value = " + p.value + ")");
                return p;
            }
            // go next
            trackCodeEntrance(getCurrentMethod() + "_if_end");
            p = p.next;
            getVisualList(getName()).setColorScheme(index, ColorScheme.DEFAULT);
            if (p != null) getVisualList(getName()).setColorScheme(index + 1, ColorScheme.HIGHLIGHT, true);
            index ++;
            // re-loop
            trackCodeEntrance(getCurrentMethod() + "_loop_begin");
        }
        // return
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
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (index <= 0) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackMethodCall("pushFront");
            pushFront(value);
            trackMethodReturn();
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        // get prev node
        trackCodeEntrance(getCurrentMethod() + "_getNode");
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
        // create new node
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
        Node p = new Node(value);
        getVisualList(getName()).setCachedNode(index, value);
        // insert (st.1)
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        p.next = prv.next;
        if (prv.next != null) {
            getVisualList(getName()).setPointerNext(VisualList.CACHED_NODE, index);
        }
        getVisualList(getName()).moveRestNodes(index, 1);
        // insert (st.2)
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        prv.next = p;
        getVisualList(getName()).setPointerNext(index - 1, VisualList.CACHED_NODE);
        getVisualList(getName()).insertCachedNode(index);
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        getVisualList(getName()).setColorScheme(index - 1, ColorScheme.DEFAULT);
    }

    public void popFront() {
        // check if empty
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (head == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Empty list.");
            return;
        }
        // pop front (st.1)
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
        getVisualList(getName()).markNode(0);
        // pop front (st.2)
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        head = head.next;
        getVisualList(getName()).moveRestNodes(1, -1);
        // delete
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        getVisualList(getName()).eraseMarkedNode();
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }

    public void erase(int index) {
        // special judge
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (index <= 0) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackMethodCall("popFront");
            popFront();
            trackMethodReturn();
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        // get node
        trackCodeEntrance(getCurrentMethod() + "_getNode");
        trackMethodCall("getNode");
        Node prv = getNode(index - 1);
        trackMethodReturn();
        // check validity
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (prv == null || prv.next == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Out of bound.");
            return;
        }
        // erase (st.1)
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
        getVisualList(getName()).markNode(index);
        // erase (st.2)
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        prv.next = prv.next.next;
        if (prv.next != null) {
            getVisualList(getName()).setPointerNext(index - 1, index + 1);
        } else {
            getVisualList(getName()).setPointerNextToNull(index - 1);
        }
        getVisualList(getName()).moveRestNodes(index + 1, -1);
        // erase (st.3)
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        getVisualList(getName()).eraseMarkedNode();
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        getVisualList(getName()).setColorScheme(index - 1, ColorScheme.DEFAULT);
    }
}
