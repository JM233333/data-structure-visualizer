package jm233333.ui;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import jm233333.Director;
import jm233333.visualized.VDSInstantiation;
import jm233333.visualized.VisualizedDataStructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The {@code SceneMenu} class maintains a scene graph for the main menu of the application.
 * Extended from JavaFX class {@code Scene}.
 */
public class SceneMenu extends Scene {

    private FlowPane root;

    /**
     * Creates a SceneMenu with a specific size.
     *
     * @param root The root node of the scene graph
     * @param width The width of the scene
     * @param height The height of the scene
     */
    public SceneMenu(FlowPane root, double width, double height) {
        super(root, width, height, Color.WHITE);
        this.root = root;
        initialize();
    }

    /**
     * Initializes the SceneMenu.
     */
    private void initialize() {
        // initialize
        this.getStylesheets().add(this.getClass().getResource("/css/bootstrapfx.css").toExternalForm());
        root.setOrientation(Orientation.VERTICAL);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setHgap(16);
        root.setVgap(16);
        // initialize menu list
        ArrayList<String> menuItems = Director.getInstance().getMenuItems();
        for (String menuItem : menuItems) {
            addButton(Director.getInstance().getVDSInstantiationMap().get(menuItem));
        }
    }

    private void addButton(final VDSInstantiation vdsInstantiation) {
        // get data
        final String name = vdsInstantiation.getName();
        final Constructor constructor = vdsInstantiation.getConstructor();
        final Object[] arguments = vdsInstantiation.getArgumentList().toArray();
        // instantiate VDS
        final VisualizedDataStructure visualDS;
        try {
            visualDS = (VisualizedDataStructure)constructor.newInstance(arguments);
            visualDS.setName(name);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println(e.getMessage());
            return;
        }
        // initialize button
        Button button = new Button(name + " " + Arrays.toString(arguments));
        button.getStyleClass().setAll("btn", "btn-primary");
        root.getChildren().add(button);
        // set listener
        button.setOnAction((event) -> {
            Scene scene = new SceneVisualizer(new BorderPane(), visualDS);
            Director.getInstance().getPrimaryStage().setScene(scene);
        });
    }
}
