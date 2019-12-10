package jm233333.visual;

import javafx.scene.shape.*;
import javafx.scene.text.*;
import jm233333.Direction;

public class VisualArrayIndex extends Visual {
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
        setValue(value);
        initialize();
    }
    private void initialize() {
        this.getChildren().add(new VisualEmptyBox(64, 64));
        text = new Text(name);
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
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
        createAnimation(500, this.layoutXProperty(), 64 * value);
    }
    public int getValue() {
        return value;
    }
}
