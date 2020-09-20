package jm233333.dsv.visualized;

import javafx.util.Pair;

import jm233333.dsv.ui.CodeTracker;
import jm233333.util.Direction;

/**
 * Class {@code VisualizedStack} defines the data structure {@code Stack} for visualizing.
 * Extended from abstract class {@link VDS}.
 */
public class VisualizedStack extends VDS {

    private final int[] data;
    private int top;
    private ModeStack mode;

    public VisualizedStack(int n) {
        this(n, ModeStack.TOP_TO_UPON);
    }
    public VisualizedStack(int n, ModeStack mode) {
        super("Stack");
        data = new int[n];
        top = 0;
        this.mode = mode;
    }

    @Override
    public void createVisual() {
        createVisualArray(getName(), Direction.RIGHT, data.length, new Pair<>("top", 0));
    }

    public void push(int value) {
        // check if full
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (_isFull()) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Full stack.");
            return;
        }
        // push (st.1)
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
        data[top] = value;
        getVisualArray(getName()).updateElement(top, value);
        // push (st.2)
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        top ++;
        getVisualArray(getName()).updateIndexField("top", top);
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }

    public void pop() {
        // check if empty
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (_isEmpty()) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Empty stack.");
            return;
        }
        // pop
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
        top --;
        getVisualArray(getName()).updateIndexField("top", top);
        getVisualArray(getName()).eraseElement(top);
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }

    public int top() {
        // check if empty
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (_isEmpty()) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageError("Empty stack.");
            return 0;
        }
        // return
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
        outputMessageReturn(data[top - 1]);
        return data[top - 1];
    }

    public boolean isEmpty() {
        // return
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        boolean flag = (top == 0);
        outputMessageReturn(flag ? "true" : "false");
        return flag;
    }
    public boolean isFull() {
        // return
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        boolean flag = (top == data.length);
        outputMessageReturn(flag ? "true" : "false");
        return flag;
    }

    private boolean _isEmpty() {
        return (top == 0);
    }
    private boolean _isFull() {
        return (top == data.length);
    }

//    public void setMode(ModeStack mode) {
//        if (this.mode == mode) {
//            return;
//        }
//        if (this.mode == ModeStack.TOP_TO_UPON) {
//            for (int i = top; i >= 1; i --) {
//                updateArrayElement("data", i, data[i - 1]);
//            }
//            eraseArrayElement("data", 0);
//        } else {
//            for (int i = 0; i < top; i ++) {
//                updateArrayElement("data", i, data[i + 1]);
//            }
//            eraseArrayElement("data", top);
//        }
//        this.mode = mode;
//        Director.getInstance().playAnimation();
//    }
}
