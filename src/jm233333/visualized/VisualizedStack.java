package jm233333.visualized;

import javafx.util.Pair;
import jm233333.Director;
import jm233333.visual.*;

/**
 * The {@code VisualizedStack} class defines the data structure {@code Stack} for visualizing.
 * Extended from abstract class {@code VisualizedDataStructure}.
 */
public class VisualizedStack extends VisualizedDataStructure {

    private int[] data;
    private int top;
    private ModeStack mode;

    public VisualizedStack(int n) {
        this(n, ModeStack.TOP_TO_UPON);
    }
    public VisualizedStack(int n, ModeStack mode) {
        super();
        data = new int[n];
        top = 0;
        this.mode = mode;
    }

    @Override
    void createVisual() {
        createVisualArray("data", data.length, new Pair<>("top", 0));
    }

    public void push(int value) {
        if (mode == ModeStack.TOP_TO_UPON) {
            updateArrayElement("data", top, value);
            updateIndexField("top", top + 1);
        }
        else {
            updateIndexField("top", top + 1);
            updateArrayElement("data", top, value);
        }
        Director.getInstance().playAnimation();
    }

    public void pop() {
        if (isEmpty()) {
            System.out.println("POP EMPTY");
            return;
        }
        if (mode == ModeStack.TOP_TO_UPON) {
            updateIndexField("top", top - 1);
            eraseArrayElement("data", top);
        } else {
            eraseArrayElement("data", top);
            updateIndexField("top", top - 1);
        }
        Director.getInstance().playAnimation();
    }

    public int top() {
        if (isEmpty()) {
            return 0;
        }
        if (mode == ModeStack.TOP_TO_UPON) {
            return data[top - 1];
        } else {
            return data[top];
        }
    }

    public boolean isEmpty() {
        return (top == 0);
    }

    public void setMode(ModeStack mode) {
        if (this.mode == mode) {
            return;
        }
        if (this.mode == ModeStack.TOP_TO_UPON) {
            for (int i = top; i >= 1; i --) {
                updateArrayElement("data", i, data[i - 1]);
            }
            eraseArrayElement("data", 0);
        } else {
            for (int i = 0; i < top; i ++) {
                updateArrayElement("data", i, data[i + 1]);
            }
            eraseArrayElement("data", top);
        }
        this.mode = mode;
        Director.getInstance().playAnimation();
    }
}
