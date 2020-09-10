package jm233333.dsv.visual;

import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jm233333.dsv.Director;

public class VisualIndex extends Visual {

    private int value;
    private Text text;

    public VisualIndex(String name) {
        this(name, 0);
    }
    public VisualIndex(String name, int value) {
        super(name);
        this.value = value;
        initialize();
    }
    private void initialize() {
        // initialize
        this.getStyleClass().add("visual-index-field");
        // initialize graphics
        text = new Text(getName());
        text.setFont(Font.font(12));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(VisualNode.BOX_SIZE);
        text.setTextOrigin(VPos.TOP);
        this.getChildren().add(text);
    }

    public void setValue(int value) {
        this.value = value;
        Director.getInstance().createAnimation(1.0, this.layoutXProperty(), VisualNode.BOX_SIZE * value);
    }
    public int getValue() {
        return value;
    }
}
