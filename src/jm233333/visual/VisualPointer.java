package jm233333.visual;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * The {@code VisualPointer} class defines the graphic components of a pointer that is displayed on the monitor.
 * Used in subclasses of {@code VisualizedDataStructure}.
 * Extended from JavaFX class {@code Group} only for UI layout.
 */
public class VisualPointer extends Group {

    private Line body;
    private Line hatLeft, hatRight;

    public VisualPointer() {
        // initialize
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

    public final Line getBody() {
        return body;
    }

    private void updateDirection() {
        double sx = getBody().getStartX() - getBody().getEndX();
        double sy = getBody().getStartY() - getBody().getEndY();
        double bodyLength = Math.sqrt(sx*sx + sy*sy);
        double bodyAngle = Math.acos(sx / bodyLength);
        double hatLength = 8;
        for (Line line : new Line[]{hatLeft, hatRight}) {
            int direction = (line == hatLeft ? 1 : -1);
            double hatAngle = bodyAngle + (direction * Math.PI / 4);
            double ex = hatLength * Math.cos(hatAngle);
            double ey = hatLength * Math.sin(hatAngle);
            line.setStartX(getBody().getEndX());
            line.setStartY(getBody().getEndY());
            line.setEndX(line.getStartX() + ex);
            line.setEndY(line.getStartY() + ey);
        }
    }
}
