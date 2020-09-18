package jm233333.dsv.visual;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import jm233333.dsv.Director;

public class VisualBinaryTreeNode extends VisualNode {

    private Circle box;
    private VisualPointer<VisualBinaryTreeNode> left, right;

    private VisualBinaryTreeNode parentNode = null;
    private int depth = 0;

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
        box = new Circle(BOX_SIZE / 2);
        box.setFill(Color.valueOf("#EEE"));
        box.setStroke(Color.grayRgb(51));
        box.setStrokeType(StrokeType.CENTERED);
        box.setStrokeWidth(4);
        box.setLayoutX(BOX_SIZE / 2);
        box.setLayoutY(BOX_SIZE / 2);
        this.getChildren().add(box);
        // add creating animation
        this.setScaleX(0);
        this.setScaleY(0);
        Director.getInstance().createAnimation(1.0, this.scaleXProperty(), 1);
        Director.getInstance().updateAnimation(1.0, this.scaleYProperty(), 1);
    }

    public void setLeft(VisualBinaryTreeNode node) {
        left.setTarget(node);
        if (node != null) {
            node.setParentNode(this);
        }
    }
    public final VisualBinaryTreeNode getLeft() {
        return left.getTarget();
    }
    public final VisualPointer<VisualBinaryTreeNode> getPointerToLeft() {
        return left;
    }

    public void setRight(VisualBinaryTreeNode node) {
        right.setTarget(node);
        if (node != null) {
            node.setParentNode(this);
        }
    }
    public final VisualBinaryTreeNode getRight() {
        return right.getTarget();
    }
    public final VisualPointer<VisualBinaryTreeNode> getPointerToRight() {
        return right;
    }

    public void setParentNode(VisualBinaryTreeNode node) {
        parentNode = node;
        depth = (parentNode == null ? 0 : parentNode.getDepth() + 1);
    }
    public final VisualBinaryTreeNode getParentNode() {
        return parentNode;
    }
    public int getDepth() {
        return depth;
    }

    @Override
    public double getPointedX() {
        return getLayoutX() + box.getLayoutX();
    }

    @Override
    public double getPointedY() {
        return getLayoutY() + box.getLayoutY();
    }

    @Override
    public double getWidth() {
        return box.getRadius();
    }

    @Override
    public double getHeight() {
        return box.getRadius();
    }

    @Override
    public void setColorScheme(ColorScheme colorScheme, boolean sync) {
        // create animation if required
        if (!sync) {
            Director.getInstance().addEmptyTimeline();
        }
        // add animations
        Director.getInstance().updateAnimation(1.0, box.strokeProperty(), colorScheme.getColorBoard());
        Director.getInstance().updateAnimation(1.0, box.fillProperty(), colorScheme.getColorBox());
        Director.getInstance().updateAnimation(1.0, getText().fillProperty(), colorScheme.getColorText());
//        left.setHighlight(flag, true);
//        right.setHighlight(flag, true);
    }
}
