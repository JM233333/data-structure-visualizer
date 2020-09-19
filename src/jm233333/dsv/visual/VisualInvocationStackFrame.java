package jm233333.dsv.visual;

import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jm233333.dsv.Director;

/**
 * The {@code VisualInvocationStackFrame} class.
 */
public class VisualInvocationStackFrame extends Visual {

    private String methodName;

    private Rectangle box;
    private Text text;

    public VisualInvocationStackFrame() {
        this(null);
    }
    public VisualInvocationStackFrame(String methodName) {
        // initialize
        this.getStyleClass().add("visual-node");
        // initialize box
        box = new Rectangle(128, 48);
        box.setFill(Color.TRANSPARENT);
        box.setStroke(Color.grayRgb(51));
        box.setStrokeType(StrokeType.CENTERED);
        box.setStrokeWidth(4);
        this.getChildren().add(box);
        //
        text = new Text();
        text.setTextAlignment(TextAlignment.CENTER);
        text.setTextOrigin(VPos.CENTER);
        text.setWrappingWidth(box.getWidth());
        text.setLayoutY(box.getHeight() / 2);
        this.getChildren().add(text);
        //
        if (methodName != null) setMethodName(methodName);
    }

    public void clear() {
        methodName = null;
        setText("");
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
        setText(methodName);
    }
    public String getMethodName() {
        return methodName;
    }

    public void setText(String str) {
        Director.getInstance().createAnimationText(text, str);
    }
    public Text getText() {
        return text;
    }

    public double getWidth() {
        return box.getWidth();
    }
    public double getHeight() {
        return box.getHeight();
    }

}
