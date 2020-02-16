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

        boxValue = new Rectangle(64, 64);
        boxValue.setLayoutX(0);
        boxPointer = new Rectangle(32, 64);
        boxPointer.setLayoutX(64);
        for (Rectangle box : new Rectangle[]{boxValue, boxPointer}) {
            box.setFill(Color.rgb(255, 255, 255));
            box.setStroke(Color.BLACK);
            box.setStrokeType(StrokeType.INSIDE);
            box.setStrokeWidth(2);
            this.getChildren().add(box);
        }

        pointer = new VisualPointer();
        pointer.getBody().setStartX(64 + 16);
        pointer.getBody().setStartY(32);
        pointer.getBody().setEndX(pointer.getBody().getStartX() + 8);
        pointer.getBody().setEndY(pointer.getBody().getStartY());
        this.getChildren().add(pointer);

        this.setScaleX(0);
        this.setScaleY(0);
        Visual.createAnimation(1.0, this.scaleXProperty(), 1);
        Visual.updateAnimation(1.0, this.scaleYProperty(), 1);
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
            Visual.createAnimation(1.0, pointer.getBody().endXProperty(),
                    node.getLayoutX() + node.boxValue.getLayoutX() - this.getLayoutX());
            Visual.updateAnimation(1.0, pointer.getBody().endYProperty(),
                    node.getLayoutY() + node.boxValue.getLayoutY() - this.getLayoutY() + 32);
        } else {
            Visual.createAnimation(1.0, pointer.getBody().endXProperty(),
                    pointer.getBody().getStartX() + 8);
            Visual.updateAnimation(1.0, pointer.getBody().endYProperty(),
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
                    pointer.getBody().setEndY(node.getLayoutY() + node.boxValue.getLayoutY() - thisNode.getLayoutY() + 32);
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
