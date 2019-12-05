package jm233333.ui;


import javafx.scene.shape.*;

public class VisualArrayNode extends VisualNode {
    public VisualArrayNode() {
        super();
        initialize();
    }
    public VisualArrayNode(int value) {
        super(value);
        initialize();
    }
    void initialize() {
        Rectangle box = new Rectangle(32, 32);
        this.getChildren().add(box);
    }
}
