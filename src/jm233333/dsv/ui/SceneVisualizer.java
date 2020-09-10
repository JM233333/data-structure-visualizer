package jm233333.dsv.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

import jm233333.dsv.Director;
import jm233333.dsv.Global;
import jm233333.dsv.visualized.VDS;

/**
 * Class {@code SceneVisualizer} maintains a scene graph for visualizer UI.
 * Extended from JavaFX class {@link Scene}.
 */
public class SceneVisualizer extends Scene {

    private BorderPane root;
    private Monitor monitor;
    private CodeTracker codeTracker;
    private Controller controller;
    private FlowPane menu;

    private VDS vds;

    /**
     * Creates a {@code SceneVisualizer} with a specific size and a specific {@link VDS}.
     *
     * @param root The root node of the scene graph
     * @param vds The data structure that will be visualized
     */
    public SceneVisualizer(BorderPane root, VDS vds) {
        super(root, Color.WHITE);
        this.root = root;
        this.vds = vds;
        initialize();
    }

    /**
     * Initializes the {@code SceneVisualizer}.
     */
    private void initialize() {
        // initialize root
        root.setId("root");
        root.getStyleClass().add("bg-info");
        root.setPrefHeight(Math.min(800.0, Global.getScreenHeight()));
        // initialize CSS
        initializeCSS();
        // initialize sub regions
        initializeMonitor();     // center
        initializeCodeTracker(); // right
        initializeController();  // bottom
        initializeMenu();        // top
        // set clip of the monitor
//        final Rectangle clip = new Rectangle(Director.getInstance().getScreenWidth(), codeTracker.getHeight() + 4);
//        monitor.setClip(clip);
//        Director.getInstance().getPrimaryStage().heightProperty().addListener((observable, oldValue, newValue) -> {
//            clip.setHeight(codeTracker.getHeight() + 4);
//        });
        // set common style class
        for (Parent p : new Parent[]{controller, monitor, codeTracker, menu}) {
            p.getStyleClass().addAll("panel", "panel-primary");
        }
//        widthProperty().addListener((ov, t, t1) -> {
//            System.out.println("Window Size Change:" + t.toString() + "," + t1.toString());
//        });
    }
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
        vds.setMonitor(monitor);
    }
    private void initializeCodeTracker() {
        // initialize code tracker
        codeTracker = new CodeTracker();
        codeTracker.setId("code-tracker");
        root.setLeft(codeTracker);
        vds.setCodeTracker(codeTracker);
//        codeTracker.widthProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println(codeTracker.widthProperty().getValue());
//        });
    }
    private void initializeController() {
        // initialize controller
        controller = new Controller(vds);
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
            Global.getPrimaryStage().setScene(scene);
        });
        menu.getChildren().add(buttonBack);
    }
}
