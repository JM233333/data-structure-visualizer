package jm233333.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import jm233333.Director;
import jm233333.visualized.VisualizedDataStructure;

//import org.kordamp.bootstrapfx.scene.layout.Panel;

/**
 * The {@code SceneVisualizer} class maintains a scene graph for Visualizer UI.
 * Extended from JavaFX class {@code Scene}.
 */
public class SceneVisualizer extends Scene {

    private BorderPane root;
    private Controller controller;
    private Monitor monitor;
    private CodeTracker codeTracker;
    private FlowPane menu;

    private VisualizedDataStructure visualDS;

    /**
     * Creates a SceneVisualizer with a specific size and a specific data structure that will be visualized.
     *
     * @param root The root node of the scene graph
     * @param width The width of the scene
     * @param height The height of the scene
     * @param visualDS The data structure that will be visualized
     */
    public SceneVisualizer(BorderPane root, double width, double height, VisualizedDataStructure visualDS) {
        super(root, width, height, Color.WHITE);
        this.root = root;
        this.visualDS = visualDS;
        initialize();
    }

    /**
     * Initializes the SceneVisualizer.
     */
    private void initialize() {
        //
        root.setId("root");
        root.setMaxHeight(768);
        //
        initializeCSS();
        initializeMonitor();     // center
        initializeCodeTracker(); // right
        initializeController();  // bottom
        initializeMenu();        // top
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
        visualDS.setOutputBox(controller.getOutputBox());
//        controller.setGridLinesVisible(true); // debug
    }
    private void initializeMonitor() {
        // initialize monitor
        monitor = new Monitor();
        monitor.setId("monitor");
        root.setCenter(monitor);
        visualDS.setMonitor(monitor);
    }
    private void initializeCodeTracker() {
        // initialize code tracker
        codeTracker = new CodeTracker();
        codeTracker.setId("codeTracker");
        root.setRight(codeTracker);
        visualDS.setCodeTracker(codeTracker);
    }
    private void initializeMenu() {
        // initialize menu
        menu = new FlowPane();
        menu.setMaxHeight(64);
        menu.setAlignment(Pos.TOP_CENTER);
        menu.setPadding(new Insets(16, 16, 16, 16));
        root.setTop(menu);
        // initialize buttons
        Button buttonBack = new Button("Return Menu");
        buttonBack.setOnAction((event) -> {
            Scene scene = new SceneMenu(new FlowPane(), 1024, 768);
            Director.getInstance().getPrimaryStage().setScene(scene);
        });
        menu.getChildren().add(buttonBack);
    }
}
