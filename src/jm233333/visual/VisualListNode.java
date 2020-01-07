package jm233333.visual;

import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class VisualListNode extends VisualNode {

    private VisualListNode next;

    private Rectangle boxValue, boxPointer;
    private Text text;
    private Line pointer;

    public VisualListNode() {
        super();
        initialize();
    }

    public VisualListNode(int value) {
        super(value);
        initialize();
    }

    void initialize() {
        next = null;

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

        text = new Text();
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setTextOrigin(VPos.CENTER);
        text.setWrappingWidth(64);
        text.setLayoutY(32);
        this.getChildren().add(text);

        pointer = new Line();
        pointer.setStartX(64 + 16);
        pointer.setStartY(16);
        pointer.setEndX(pointer.getStartX() + 8);
        pointer.setEndY(pointer.getStartY());
        pointer.setStrokeWidth(4);
        pointer.setStrokeLineCap(StrokeLineCap.BUTT);
        this.getChildren().add(pointer);
    }

    public void clear() {
        super.clear();
        setText("");
    }

    public void setValue(int value) {
        super.setValue(value);
        setText(String.valueOf(value));
    }

    public void setPointer(VisualListNode node) {
        Visual.createAnimation(500, pointer.endXProperty(), node.boxValue.getLayoutX());
        Visual.updateAnimation(500, pointer.endYProperty(), node.boxValue.getLayoutY() + 32);
    }

    public double getWidth() {
        return boxValue.getWidth() + boxPointer.getWidth();
    }

    public double getHeight() {
        return boxValue.getHeight() + boxPointer.getHeight();
    }

    public final Text getText() {
        return text;
    }

    public void setText(String str) {
        Visual.createAnimationText(text, str);
    }
}
