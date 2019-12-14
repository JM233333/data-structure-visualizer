package jm233333.visual;

import javafx.scene.Group;

public abstract class VisualNode extends Group {

    private boolean isFilled;
    private int value;

    public VisualNode() {
        isFilled = false;
        value = 0;
    }
    public VisualNode(int value) {
        setValue(value);
    }

    abstract void initialize();

    public void clear() {
        isFilled = false;
        value = 0;
    }
    public boolean isFilled() {
        return isFilled;
    }

    public void setValue(int value) {
        isFilled = true;
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
