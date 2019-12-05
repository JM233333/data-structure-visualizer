package jm233333.ui;


import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class VisualArrayNode extends VisualNode {
    public VisualArrayNode() {
        super();
        initialize();
    }
    public VisualArrayNode(int value) {
        super(value);
        initialize();
    }
    void initialize() {
        Rectangle box = new Rectangle(64, 64);
        box.setFill(Color.rgb(255, 255, 255));
        box.setStroke(Color.BLACK);
        box.setStrokeType(StrokeType.INSIDE);
        box.setStrokeWidth(2);
        this.getChildren().add(box);
    }
}
