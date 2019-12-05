package jm233333.ui;

import java.lang.reflect.*;
import java.util.*;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import jm233333.visual.VisualDataStructure;

/**
 * The {@code SceneVisualizer} class maintains a scene graph for Visualizer UI.
 */
public class SceneVisualizer extends Scene {

    private BorderPane root;
    private Controller controller;
    private VisualDataStructure visualDS;
    private Monitor monitor;

    /**
     * Creates a SceneVisualizer with a specific size.
     *
     * @param root The root node of the scene graph
     * @param width The width of the scene
     * @param height The height of the scene
     * @param visualDS The data structure that is to be visualized
     */
    public SceneVisualizer(BorderPane root, double width, double height, VisualDataStructure visualDS) {
        super(root, width, height, Color.WHITE);
        this.root = root;
        this.visualDS = visualDS;
        initialize();
    }

    /**
     * Initializes the SceneVisualizer.
     */
    private void initialize() {
        initializeCSS();
        initializeController();
        initializeMonitor();
    }
    private void initializeCSS() {
        Class cls = this.getClass();
        String name = cls.getName();
        this.getStylesheets().add(cls.getResource(name.substring(name.lastIndexOf('.') + 1) + ".css").toExternalForm());
    }
    private void initializeController() {
        // initialize controller
        controller = new Controller(visualDS);
        controller.setId("controller");
        root.setBottom(controller); // root.getChildren().add(controller);
        controller.setGridLinesVisible(true); // debug
    }
    private void initializeMonitor() {
        // initialize monitor
        monitor = new Monitor();
        monitor.setId("monitor");
        root.setCenter(monitor); // root.getChildren().add(canvas);
        visualDS.setMonitor(monitor);
        //
    }
}
