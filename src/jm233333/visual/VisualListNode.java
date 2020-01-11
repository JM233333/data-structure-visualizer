package jm233333.visual;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import jm233333.Director;

import java.util.Objects;

public class VisualListNode extends VisualNode {

    private VisualListNode next;
    private ChangeListener<Number> nextListenerX, nextListenerY;

    private Rectangle boxValue, boxPointer;
    private Line pointer;

    public VisualListNode() {
        super();
        initialize();
        initializeText();
    }

    public VisualListNode(int value) {
        super(value);
        initialize();
        initializeText(value);
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

        pointer = new Line();
        pointer.setStartX(64 + 16);
        pointer.setStartY(32);
        pointer.setEndX(pointer.getStartX() + 8);
        pointer.setEndY(pointer.getStartY());
        pointer.setStrokeWidth(4);
        pointer.setStrokeLineCap(StrokeLineCap.ROUND);
        this.getChildren().add(pointer);
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
        Visual.createAnimation(500, pointer.endXProperty(),
                node.getLayoutX() + node.boxValue.getLayoutX() - this.getLayoutX());
        Visual.updateAnimation(500, pointer.endYProperty(),
                node.getLayoutY() + node.boxValue.getLayoutY() - this.getLayoutY() + 32);
        //
        final VisualListNode thisNode = this;
        Director.getInstance().getLastTimeline().setOnFinished((event) -> {
            if (nextListenerX != null) {
                node.layoutXProperty().removeListener(nextListenerX);
                thisNode.layoutXProperty().removeListener(nextListenerX);
            }
            if (nextListenerY != null) {
                node.layoutYProperty().removeListener(nextListenerY);
                thisNode.layoutYProperty().removeListener(nextListenerY);
            }
            nextListenerX = (observable, oldX, newX) -> {
                pointer.setEndX(node.getLayoutX() + node.boxValue.getLayoutX() - thisNode.getLayoutX());
            };
            nextListenerY = (observable, oldY, newY) -> {
                pointer.setEndY(node.getLayoutY() + node.boxValue.getLayoutY() - thisNode.getLayoutY() + 32);
            };
            node.layoutXProperty().addListener(nextListenerX);
            thisNode.layoutXProperty().addListener(nextListenerX);
            node.layoutYProperty().addListener(nextListenerY);
            thisNode.layoutYProperty().addListener(nextListenerY);
        });
    }
    public final Line getPointer() {
        return pointer;
    }

    public double getWidth() {
        return boxValue.getWidth() + boxPointer.getWidth();
    }

    public double getHeight() {
        return boxValue.getHeight() + boxPointer.getHeight();
    }
}
