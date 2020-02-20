package jm233333.visual;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

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

    private void initialize() {
        // initialize
        this.getStyleClass().add("visual-array-node");
        // initialize graphics
        box = new Rectangle(BOX_SIZE, BOX_SIZE);
        box.setFill(Color.TRANSPARENT);
        box.setStroke(Color.grayRgb(51));
        box.setStrokeType(StrokeType.CENTERED);
        box.setStrokeWidth(4);
        this.getChildren().add(box);
        // initialize text
        initializeText();
    }

    public double getWidth() {
        return box.getWidth();
    }
    public double getHeight() {
        return box.getHeight();
    }
}
