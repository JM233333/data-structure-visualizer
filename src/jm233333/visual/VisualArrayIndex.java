package jm233333.visual;

import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import jm233333.Direction;
import jm233333.Director;

public class VisualArrayIndex extends Group {
    private String name;
    private int value;
    private Text text;
    private Line line;
    private Direction direction;
    public VisualArrayIndex(String name) {
        this(name, 0);
    }
    public VisualArrayIndex(String name, int value) {
        this.name = name;
        this.value = value;
        initialize();
    }
    private void initialize() {
        this.setLayoutX(64 * value);
        this.getChildren().add(new VisualEmptyBox(64, 64));

        text = new Text(name);
        text.setFont(Font.font(16));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(64);
        text.setLayoutY(56);

        line = new Line(32, 8, 32, 40);
        line.setStrokeWidth(4);
        direction = Direction.UP;
        this.getChildren().addAll(text, line);
    }

    public void setValue(int value) {
        this.value = value;
        Director.getInstance().createAnimation(1.0, this.layoutXProperty(), 64 * value);
    }
    public int getValue() {
        return value;
    }
}
