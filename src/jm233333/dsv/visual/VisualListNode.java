package jm233333.dsv.visual;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import jm233333.dsv.Director;

public class VisualListNode extends VisualNode {

    private Rectangle boxValue, boxPointer;
    private VisualPointer<VisualListNode> next;

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

    private void initialize() {
        // initialize box
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
        // initialize pointer
        next = new VisualPointer<>(this);
        next.getBody().setStartX(BOX_SIZE * 3 / 2);
        next.getBody().setStartY(BOX_SIZE / 2);
        next.getBody().setEndX(next.getBody().getStartX() + BOX_SIZE / 4);
        next.getBody().setEndY(next.getBody().getStartY());
        this.getChildren().add(next);
        // add creating animation
        this.setScaleX(0);
        this.setScaleY(0);
        Director.getInstance().createAnimation(1.0, this.scaleXProperty(), 1);
        Director.getInstance().updateAnimation(1.0, this.scaleYProperty(), 1);
    }

    public void setNext(VisualListNode node) {
        next.setTarget(node);
    }
    public final VisualListNode getNext() {
        return next.getTarget();
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
        return boxValue.getWidth() + boxPointer.getWidth();
    }
    @Override
    public double getHeight() {
        return boxValue.getHeight();
    }

//    @Override
    public void setHighlightColorBoard() {
        ;
    }

    @Override
    public void setHighlight(boolean flag, boolean sync) {
        // get color
        Color colorBoard, colorBox, colorText;
        if (flag) {
            colorBoard = Color.WHITE;
            colorBox = Color.ORANGE;
            colorText = Color.WHITE;
        } else {
            colorBoard = Color.BLACK;
            colorBox = Color.TRANSPARENT;
            colorText = Color.BLACK;
        }
        // create animation if required
        if (!sync) {
            Director.getInstance().addEmptyTimeline();
        }
        // add animations
        for (Rectangle box : new Rectangle[]{boxValue, boxPointer}) {
            Director.getInstance().updateAnimation(1.0, box.strokeProperty(), colorBoard);
            Director.getInstance().updateAnimation(1.0, box.fillProperty(), colorBox);
        }
        Director.getInstance().updateAnimation(1.0, getText().fillProperty(), colorText);
        next.setHighlight(flag, true);
    }
}
