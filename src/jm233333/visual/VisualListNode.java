package jm233333.visual;

import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import jm233333.Director;

public class VisualListNode extends VisualNode {

    private VisualListNode next;
    private ChangeListener<Number> nextListenerX, nextListenerY;

    private Rectangle boxValue, boxPointer;
    private VisualPointer pointer;

    public VisualListNode() {
        super();
        initialize();
        initializeText();
    }

    public VisualListNode(int value) {
        super(value);
        initialize();
        initializeText();
        getText().setText(String.valueOf(value));
    }

    void initialize() {
        next = null;
        nextListenerX = null;
        nextListenerY = null;

        boxValue = new Rectangle(BOX_SIZE, BOX_SIZE);
        boxValue.setLayoutX(0);
        boxPointer = new Rectangle(BOX_SIZE, BOX_SIZE);
        boxPointer.setLayoutX(BOX_SIZE);
        for (Rectangle box : new Rectangle[]{boxValue, boxPointer}) {
            box.setFill(Color.TRANSPARENT);
            box.setStroke(Color.grayRgb(51));
            box.setStrokeType(StrokeType.CENTERED);
            box.setStrokeWidth(4);
            this.getChildren().add(box);
        }

        pointer = new VisualPointer();
        pointer.getBody().setStartX(BOX_SIZE * 3 / 2);
        pointer.getBody().setStartY(BOX_SIZE / 2);
        pointer.getBody().setEndX(pointer.getBody().getStartX() + BOX_SIZE / 4);
        pointer.getBody().setEndY(pointer.getBody().getStartY());
        this.getChildren().add(pointer);

        this.setScaleX(0);
        this.setScaleY(0);
        Director.getInstance().createAnimation(1.0, this.scaleXProperty(), 1);
        Director.getInstance().updateAnimation(1.0, this.scaleYProperty(), 1);
    }

    public void clear() {
        super.clear();
        setText("");
    }

    public void setNext() {
        ;
    }

    public void setPointer(VisualListNode node) {
        //
        if (node != null) {
            Director.getInstance().createAnimation(1.0, pointer.getBody().endXProperty(),
                    node.getLayoutX() + node.boxValue.getLayoutX() - this.getLayoutX());
            Director.getInstance().updateAnimation(1.0, pointer.getBody().endYProperty(),
                    node.getLayoutY() + node.boxValue.getLayoutY() - this.getLayoutY() + BOX_SIZE / 2);
        } else {
            Director.getInstance().createAnimation(1.0, pointer.getBody().endXProperty(),
                    pointer.getBody().getStartX() + BOX_SIZE / 4);
            Director.getInstance().updateAnimation(1.0, pointer.getBody().endYProperty(),
                    pointer.getBody().getStartY());
        }
        //
        final VisualListNode thisNode = this;
        Director.getInstance().getLastTimeline().setOnFinished((event) -> {
            if (next != null) {
                next.layoutXProperty().removeListener(nextListenerX);
                thisNode.layoutXProperty().removeListener(nextListenerX);
                next.layoutYProperty().removeListener(nextListenerY);
                thisNode.layoutYProperty().removeListener(nextListenerY);
            }
            if (node != null) {
                nextListenerX = (observable, oldValue, newValue) -> {
                    pointer.getBody().setEndX(node.getLayoutX() + node.boxValue.getLayoutX() - thisNode.getLayoutX());
                };
                nextListenerY = (observable, oldValue, newValue) -> {
                    pointer.getBody().setEndY(node.getLayoutY() + node.boxValue.getLayoutY() - thisNode.getLayoutY() + BOX_SIZE / 2);
                };
                node.layoutXProperty().addListener(nextListenerX);
                thisNode.layoutXProperty().addListener(nextListenerX);
                node.layoutYProperty().addListener(nextListenerY);
                thisNode.layoutYProperty().addListener(nextListenerY);
            }
            next = node;
        });
    }
    public final VisualPointer getPointer() {
        return pointer;
    }

    public double getWidth() {
        return boxValue.getWidth() + boxPointer.getWidth();
    }

    public double getHeight() {
        return boxValue.getHeight() + boxPointer.getHeight();
    }
}
