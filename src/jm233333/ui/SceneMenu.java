package jm233333.ui;

import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * The {@code SceneMenu} class maintains a scene graph for the main menu of the application.
 * Extended from JavaFX class {@code Scene}.
 */
public class SceneMenu extends Scene {

    private BorderPane root;
    private ScrollBar scrollBar;

    /**
     * Creates a SceneMenu with a specific size.
     *
     * @param root The root node of the scene graph
     * @param width The width of the scene
     * @param height The height of the scene
     */
    public SceneMenu(BorderPane root, double width, double height) {
        super(root, width, height, Color.WHITE);
        this.root = root;
        initialize();
    }

    /**
     * Initializes the SceneMenu.
     */
    private void initialize() {
        scrollBar = new ScrollBar();
        root.getChildren().add(scrollBar);
    }
}
