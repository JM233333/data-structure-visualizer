package custom.visualized;

import javafx.util.Pair;

import jm233333.dsv.Director;
import jm233333.dsv.ui.CodeTracker;
import jm233333.dsv.visualized.VDS;

/**
 * The {@code VisualizedDeque} class defines the data structure {@code Queue} for visualizing.
 * Extended from abstract class {@code VDS}.
 */
@SuppressWarnings("unchecked")
public class VisualizedDeque extends VDS {

    private int[] data;
    private int front, back;

    public VisualizedDeque(int n) {
        this(n, 0);
    }
    public VisualizedDeque(int n, int mode) {
        super();
        data = new int[n];
        front = 0;
        back = 0;
//        this.mode = mode;
    }

    @Override
    public void createVisual() {
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
