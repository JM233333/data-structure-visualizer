package jm233333.dsv.visual;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * The {@code VisualArrayNode} class defines graphic nodes in {@code VisualArray}.
 */
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

    @Override
    public double getPointedX() {
        return getLayoutX();
    }
    @Override
    public double getPointedY() {
        return getLayoutY() + BOX_SIZE / 2;
    }

    @Override
    public double getWidth() {
        return box.getWidth();
    }
    @Override
    public double getHeight() {
        return box.getHeight();
    }

    @Override
    public void setColorScheme(ColorScheme colorScheme, boolean sync) {
        ;
    }
}
