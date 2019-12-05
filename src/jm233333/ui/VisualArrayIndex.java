package jm233333.ui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import jm233333.Direction;

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
        text = new Text(name);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        line = new Line(0, 32, 0, 64);
        direction = Direction.UP;
        this.getChildren().addAll(text, line);
    }
}
