package jm233333.visual;

import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class VisualArrayNode extends VisualNode {
    private Text text;
    private Line line;
    public VisualArrayNode() {
        super();
        initialize();
    }
    public VisualArrayNode(int value) {
        super(value);
        initialize();
    }
    void initialize() {
        this.getChildren().clear();

        Rectangle box = new Rectangle(64, 64);
        box.setFill(Color.rgb(255, 255, 255));
        box.setStroke(Color.BLACK);
        box.setStrokeType(StrokeType.INSIDE);
        box.setStrokeWidth(2);
        this.getChildren().add(box);

        text = new Text();
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setTextOrigin(VPos.CENTER);
        text.setWrappingWidth(64);
        text.setLayoutY(32);
        this.getChildren().add(text);
    }

    public void clear() {
        super.clear();
        text.setText("");
    }
    public void setValue(int value) {
        super.setValue(value);
        text.setText(String.valueOf(value));
    }
}
