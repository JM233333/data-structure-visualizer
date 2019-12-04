package jm233333.ui;

import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * The {@code SceneVisualizer} class maintains a scene graph for Visualizer UI.
 */
public class SceneVisualizer extends Scene {

    private Group root;

    /**
     * Creates a SceneVisualizer with a specific size.
     *
     * @param root The root node of the scene graph
     * @param width The width of the scene
     * @param height The height of the scene
     */
    public SceneVisualizer(Group root, double width, double height) {
        super(root, width, height, Color.WHITE);
        this.root = root;
        initialize();
    }

    /**
     * Initializes the SceneVisualizer.
     */
    private void initialize() {
        // clear
        root.getChildren().clear();
        // initialize controller
        GridPane controller = new GridPane();
        controller.setPadding(new Insets(16));
        controller.setVgap(16);
        controller.setHgap(16);
        root.getChildren().add(controller);
        controller.setLayoutX(64);
        controller.setLayoutY(64);
        controller.setGridLinesVisible(true);
        // initialize function triggers
        FunctionTrigger trigger1 = new FunctionTrigger("push", "value");
        FunctionTrigger trigger2 = new FunctionTrigger("pop");
        GridPane.setConstraints(trigger1, 0, 0);
        GridPane.setConstraints(trigger2, 0, 1);
        controller.getChildren().addAll(trigger1, trigger2);
    }
}
