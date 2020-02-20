package jm233333.visual;

import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jm233333.Director;

public abstract class VisualNode extends Visual {

    private static int nextId = 0;

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
        text.setWrappingWidth(64);
        text.setLayoutY(32);
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
}
