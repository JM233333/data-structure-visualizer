package jm233333.visual;

import javafx.scene.Group;

public abstract class VisualNode extends Group {

    private static int nextId = 0;

    private boolean isFilled;
    private int value;

    public VisualNode() {
        resetId();
        isFilled = false;
        value = 0;
    }
    public VisualNode(int value) {
        resetId();
        isFilled = true;
        this.value = value;
    }

    private void resetId() {
        this.setId(String.valueOf(nextId));
        nextId ++;
        this.getChildren().clear();
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
