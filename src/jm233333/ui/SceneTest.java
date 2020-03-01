package jm233333.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import jm233333.Director;

public class SceneTest extends Scene {
    public SceneTest(FlowPane root, double width, double height) {
        super(root, width, height);
        Button btn = new Button("GOGOGO");
        btn.setOnAction((event) -> {
            Scene scene = new SceneMenu(new FlowPane(), 1200, 800);
            Director.getInstance().getPrimaryStage().setScene(scene);
        });
        root.getChildren().add(btn);
    }
}
