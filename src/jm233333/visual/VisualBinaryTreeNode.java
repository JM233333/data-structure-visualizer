package jm233333.visual;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import jm233333.Director;

public class VisualBinaryTreeNode extends VisualNode {

    private Circle box;
    private VisualPointer<VisualBinaryTreeNode> left, right;

    public VisualBinaryTreeNode() {
        super();
        initialize();
        initializeText();
    }

    public VisualBinaryTreeNode(int value) {
        super(value);
        initialize();
        initializeText();
        getText().setText(String.valueOf(value));
    }

    private void initialize() {
        // initialize pointer
        left = new VisualPointer<>(this);
        right = new VisualPointer<>(this);
        for (VisualPointer pointer : new VisualPointer[]{left, right}) {
            pointer.getBody().setStartX(BOX_SIZE / 2);
            pointer.getBody().setStartY(BOX_SIZE / 2);
            pointer.getBody().setEndX(pointer.getBody().getStartX());
            pointer.getBody().setEndY(pointer.getBody().getStartY() + BOX_SIZE / 4);
            this.getChildren().add(pointer);
        }
        // initialize box
        box = new Circle(BOX_SIZE);
        box.setFill(Color.TRANSPARENT);
        box.setStroke(Color.grayRgb(51));
        box.setStrokeType(StrokeType.CENTERED);
        box.setStrokeWidth(4);
        this.getChildren().add(box);
        // add creating animation
        this.setScaleX(0);
        this.setScaleY(0);
        Director.getInstance().createAnimation(1.0, this.scaleXProperty(), 1);
        Director.getInstance().updateAnimation(1.0, this.scaleYProperty(), 1);
    }

    @Override
    public double getWidth() {
        return box.getRadius();
    }

    @Override
    public double getHeight() {
        return box.getRadius();
    }
}
