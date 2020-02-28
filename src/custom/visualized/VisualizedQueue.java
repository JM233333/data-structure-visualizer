package jm233333.visualized;

import javafx.util.Pair;
import jm233333.Director;
import jm233333.ui.CodeTracker;

/**
 * The {@code VisualizedQueue} class defines the data structure {@code Queue} for visualizing.
 * Extended from abstract class {@code VisualizedDataStructure}.
 */
@SuppressWarnings("unchecked")
public class VisualizedQueue extends VisualizedDataStructure {

    private int[] data;
    private int front, back;

    public VisualizedQueue(int n) {
        this(n, 0);
    }
    public VisualizedQueue(int n, int mode) {
        super();
        data = new int[n];
        front = 0;
        back = 0;
//        this.mode = mode;
    }

    @Override
    void createVisual() {
        createVisualArray(getName(), data.length, new Pair<>("front", 0), new Pair<>("back", 0));
    }

    public void push(int value) {
//        if (_isFull()) {
//            trackCodeEntrance(CodeTracker.NEXT_LINE);
//            Director.getInstance().playAnimation();
//            return;
//        }
//        trackCodeEntrance(getCodeCurrentMethod() + "_main_begin");
        data[back] = value;
        getVisualArray(getName()).updateElement(back, value);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        back ++;
        getVisualArray(getName()).updateIndexField("back", back);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }
}
