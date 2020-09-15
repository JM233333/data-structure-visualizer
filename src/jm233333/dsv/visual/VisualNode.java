package jm233333.dsv.visual;

import javafx.geometry.VPos;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import jm233333.dsv.Director;

/**
 * The {@code VisualNode} abstract class provides common properties for graphic nodes.
 */
public abstract class VisualNode extends Visual {

    private static int nextId = 0;

    /**
     * The width and height of every graphic nodes.
     */
    public static final double BOX_SIZE = 48;

    private boolean isFilled;
    private int value;

    private Text text;

    public VisualNode() {
        resetId();
        this.getStyleClass().add("visual-node");
        isFilled = false;
        value = 0;
    }
    public VisualNode(int value) {
        resetId();
        this.getStyleClass().add("visual-node");
        isFilled = true;
        this.value = value;
    }

    private void resetId() {
        this.setId(String.valueOf(nextId));
        nextId ++;
        this.getChildren().clear();
    }

    void initializeText() {
        text = new Text();
        text.setTextAlignment(TextAlignment.CENTER);
        text.setTextOrigin(VPos.CENTER);
        text.setWrappingWidth(BOX_SIZE);
        text.setLayoutY(VisualNode.BOX_SIZE / 2);
        this.getChildren().add(text);
    }

    public void clear() {
        isFilled = false;
        value = 0;
        setText("");
    }
    public boolean isFilled() {
        return isFilled;
    }

    public void setValue(int value) {
        isFilled = true;
        this.value = value;
        setText(String.valueOf(value));
    }
    public int getValue() {
        return value;
    }

    public void setText(String str) {
        Director.getInstance().createAnimationText(text, str);
    }
    public final Text getText() {
        return text;
    }

    public abstract double getPointedX();
    public abstract double getPointedY();

    public abstract double getWidth();
    public abstract double getHeight();

    public void setHighlight(boolean flag) {
        setHighlight(flag, false);
    }
    public abstract void setHighlight(boolean flag, boolean sync);

}
