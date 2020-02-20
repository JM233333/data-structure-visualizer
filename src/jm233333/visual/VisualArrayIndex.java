package jm233333.visual;

import javafx.scene.Group;
import javafx.scene.text.*;
import jm233333.Director;

public class VisualArrayIndex extends Group {
    private String name;
    private int value;
    private Text text;
    public VisualArrayIndex(String name) {
        this(name, 0);
    }
    public VisualArrayIndex(String name, int value) {
        this.name = name;
        this.value = value;
        initialize();
    }
    private void initialize() {
        this.setLayoutX(VisualNode.BOX_SIZE * value);
        this.getChildren().add(new VisualEmptyBox(VisualNode.BOX_SIZE, VisualNode.BOX_SIZE));

        text = new Text(name);
        text.setFont(Font.font(20));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(VisualNode.BOX_SIZE);
        text.setLayoutY(VisualNode.BOX_SIZE / 2);
        this.getChildren().add(text);
    }

    public void setValue(int value) {
        this.value = value;
        Director.getInstance().createAnimation(1.0, this.layoutXProperty(), VisualNode.BOX_SIZE * value);
    }
    public int getValue() {
        return value;
    }
}
