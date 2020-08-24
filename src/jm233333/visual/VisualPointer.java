package jm233333.visual;

import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import jm233333.Director;

/**
 * The {@code VisualPointer} generic class defines a graphic pointer that is displayed on the monitor.
 */
public class VisualPointer <T extends VisualNode> extends Visual {

    private final T holder;
    private T target;
    private ChangeListener<Number> targetListenerX, targetListenerY;

    private Line body;
    private Line hatLeft, hatRight;

    public VisualPointer(T holder) {
        // initialize
        this.getStyleClass().add("visual-pointer");
        // initialize data
        this.holder = holder;
        target = null;
        targetListenerX = null;
        targetListenerY = null;
        // initialize graphics
        body = new Line();
        hatLeft = new Line();
        hatRight = new Line();
        for (Line line : new Line[]{body, hatLeft, hatRight}) {
            line.setStrokeWidth(3);
            line.setStrokeLineCap(StrokeLineCap.ROUND);
            this.getChildren().add(line);
        }
        // set listener
        body.startXProperty().addListener((observable, oldValue, newValue) -> updateDirection());
        body.startYProperty().addListener((observable, oldValue, newValue) -> updateDirection());
        body.endXProperty().addListener((observable, oldValue, newValue) -> updateDirection());
        body.endYProperty().addListener((observable, oldValue, newValue) -> updateDirection());
    }

    public final T getHolder() {
        return holder;
    }

    public void setTarget(final T node) {
        //
        if (node != null) {
            Director.getInstance().createAnimation(1.0, body.endXProperty(),
                    node.getPointedX() - holder.getLayoutX());
            Director.getInstance().updateAnimation(1.0, body.endYProperty(),
                    node.getPointedY() - holder.getLayoutY());
        } else {
            Director.getInstance().createAnimation(1.0, body.endXProperty(),
                    body.getStartX() + VisualNode.BOX_SIZE / 4);
            Director.getInstance().updateAnimation(1.0, body.endYProperty(),
                    body.getStartY());
        }
        //
        final T finalTarget = target;
        target = node;
        Director.getInstance().getLastTimeline().setOnFinished((event) -> {
            if (finalTarget != null) {
                holder.layoutXProperty().removeListener(targetListenerX);
                finalTarget.layoutXProperty().removeListener(targetListenerX);
                holder.layoutYProperty().removeListener(targetListenerY);
                finalTarget.layoutYProperty().removeListener(targetListenerY);
            }
            if (node != null) {
                targetListenerX = (observable, oldValue, newValue) -> {
                    body.setEndX(node.getPointedX() - holder.getLayoutX());
                };
                targetListenerY = (observable, oldValue, newValue) -> {
                    body.setEndY(node.getPointedY() - holder.getLayoutY());
                };
                holder.layoutXProperty().addListener(targetListenerX);
                node.layoutXProperty().addListener(targetListenerX);
                holder.layoutYProperty().addListener(targetListenerY);
                node.layoutYProperty().addListener(targetListenerY);
            }
        });
    }
    public final T getTarget() {
        return target;
    }

    public final Line getBody() {
        return body;
    }

    private void updateDirection() {
        double sx = body.getStartX() - body.getEndX();
        double sy = body.getStartY() - body.getEndY();
        double angle = Math.asin(sy / Math.sqrt(sx*sx + sy*sy));
        double bodyAngle = (sx > 0 ? angle : Math.PI - angle);
        double hatLength = 8;
        for (Line line : new Line[]{hatLeft, hatRight}) {
            int direction = (line == hatLeft ? 1 : -1);
            double hatAngle = bodyAngle + (direction * Math.PI / 4);
            double hx = hatLength * Math.cos(hatAngle);
            double hy = hatLength * Math.sin(hatAngle);
            line.setStartX(body.getEndX());
            line.setStartY(body.getEndY());
            line.setEndX(body.getEndX() + hx);
            line.setEndY(body.getEndY() + hy);
        }
    }


    public void setHighlight(boolean flag) {
        setHighlight(flag, false);
    }
    public void setHighlight(boolean flag, boolean sync) {
        // get color
        Color colorLine;
        if (flag) {
            colorLine = Color.WHITE;
        } else {
            colorLine = Color.BLACK;
        }
        // create animation if required
        if (!sync) {
            Director.getInstance().addEmptyTimeline();
        }
        // add animations
        for (Shape shape : new Shape[]{body, hatLeft, hatRight}) {
            Director.getInstance().updateAnimation(1.0, shape.strokeProperty(), colorLine);
        }
    }
}
