package jm233333.visual;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class VisualArrayNode extends VisualNode {

    private Rectangle box;

    public VisualArrayNode() {
        super();
        initialize();
    }
    public VisualArrayNode(int value) {
        super(value);
        initialize();
    }

    void initialize() {
        box = new Rectangle(64, 64);
        box.setFill(Color.rgb(255, 255, 255));
        box.setStroke(Color.BLACK);
        box.setStrokeType(StrokeType.INSIDE);
        box.setStrokeWidth(2);
        this.getChildren().add(box);

        initializeText();
    }

    public double getWidth() {
        return box.getWidth();
    }
    public double getHeight() {
        return box.getHeight();
    }
}
