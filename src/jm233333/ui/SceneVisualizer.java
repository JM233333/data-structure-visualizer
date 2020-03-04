package jm233333.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import jm233333.Director;
import jm233333.visualized.VisualizedDataStructure;

/**
 * The {@code SceneVisualizer} class maintains a scene graph for Visualizer UI.
 * Extended from JavaFX class {@code Scene}.
 */
public class SceneVisualizer extends Scene {

    private BorderPane root;
    private Monitor monitor;
    private CodeTracker codeTracker;
    private Controller controller;
    private FlowPane menu;

    private VisualizedDataStructure visualDS;

    /**
     * Creates a SceneVisualizer with a specific size and a specific data structure that will be visualized.
     *
     * @param root The root node of the scene graph
     * @param visualDS The data structure that will be visualized
     */
    public SceneVisualizer(BorderPane root, VisualizedDataStructure visualDS) {
        super(root, Color.WHITE);
        this.root = root;
        this.visualDS = visualDS;
        initialize();
    }

    /**
     * Initializes the SceneVisualizer.
     */
    private void initialize() {
        // initialize root
        root.setId("root");
        root.setMaxHeight(768);
        root.getStyleClass().add("bg-info");
        // initialize CSS
        initializeCSS();
        // initialize sub regions
        initializeMonitor();     // center
        initializeCodeTracker(); // right
        initializeController();  // bottom
        initializeMenu();        // top
        // set common style class
        for (Parent p : new Parent[]{controller, monitor, codeTracker, menu}) {
            p.getStyleClass().addAll("panel", "panel-primary");
        }
//        widthProperty().addListener((ov, t, t1) -> {
//            System.out.println("Window Size Change:" + t.toString() + "," + t1.toString());
//        });
    }

    /**
     * Initializes CSS.
     */
    private void initializeCSS() {
        Class cls = this.getClass();
        this.getStylesheets().add(cls.getResource("/css/bootstrapfx.css").toExternalForm());
        this.getStylesheets().add(cls.getResource("/css/_common.css").toExternalForm());
        String fullName = cls.getName();
        String lastName = fullName.substring(fullName.lastIndexOf('.') + 1);
        this.getStylesheets().add(cls.getResource("/css/" + lastName + ".css").toExternalForm());
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
        codeTracker.setId("code-tracker");
        root.setRight(codeTracker);
        visualDS.setCodeTracker(codeTracker);
//        codeTracker.widthProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println(codeTracker.widthProperty().getValue());
//        });
    }
    private void initializeController() {
        // initialize controller
        controller = new Controller(visualDS);
        controller.setId("controller");
        root.setBottom(controller); // root.getChildren().add(controller);
//        controller.setGridLinesVisible(true); // debug
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
            Director.getInstance().forceClearAllAnimation();
            Scene scene = new SceneMenu(new FlowPane(), 1024, 768);
            Director.getInstance().getPrimaryStage().setScene(scene);
        });
        menu.getChildren().add(buttonBack);
    }
}
