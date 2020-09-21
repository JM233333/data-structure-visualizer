package custom.visualized;

import javafx.util.Pair;

import jm233333.dsv.ui.CodeTracker;
import jm233333.dsv.visualized.VDS;
import jm233333.util.Direction;

/**
 * The {@code VisualizedCircularQueue} class defines the data structure {@code CircularQueue} for visualizing.
 * Extended from abstract class {@code VDS}.
 */
@SuppressWarnings("unchecked")
public class VisualizedCircularQueue extends VDS {

    private final int[] data;
    private int front;
    private int back;

    public VisualizedCircularQueue(int n) {
        super("CircularQueue");
        data = new int[n];
        front = 0;
        back = 0;
    }

    @Override
    public void createVisual() {
        createVisualArray(getName(), Direction.RIGHT, data.length, new Pair<>("front", 0), new Pair<>("back", 0));
    }

    public void push(int value) {
        // check if full
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (_isFull()) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Full queue.");
            return;
        }
        // push (st.1)
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
        data[back] = value;
        getVisualArray(getName()).updateElement(back, value);
        // push (st.2)
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        back = (back + 1) % data.length;
        getVisualArray(getName()).updateIndexField("back", back);
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }

    public void pop() {
        // check if empty
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (_isEmpty()) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Empty queue.");
            return;
        }
        // pop
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
        int prvFront = front;
        front = (front + 1) % data.length;
        getVisualArray(getName()).updateIndexField("front", front);
        getVisualArray(getName()).eraseElement(prvFront);
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }

    public int front() {
        // check if empty
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (_isEmpty()) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Empty queue.");
            return 0;
        }
        // return
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
        outputMessageReturn(data[front]);
        return data[front];
    }

    public boolean isEmpty() {
        // return
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        boolean flag = (front == back);
        outputMessageReturn(flag ? "true" : "false");
        return flag;
    }
    public boolean isFull() {
        // temp calc
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        int nback = (back + 1) % data.length;
        // return
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        boolean flag = (nback == front);
        outputMessageReturn(flag ? "true" : "false");
        return flag;
    }

    private boolean _isEmpty() {
        return (front == back);
    }
    private boolean _isFull() {
        int nback = (back + 1) % data.length;
        return (nback == front);
    }

}
