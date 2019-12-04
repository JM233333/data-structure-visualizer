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
    private VisualDataStructure visualDS;
    private GridPane controller;
    private Monitor canvas;

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
        initializeCanvas();
    }
    private void initializeCSS() {
        Class cls = this.getClass();
        String name = cls.getName();
        this.getStylesheets().add(cls.getResource(name.substring(name.lastIndexOf('.') + 1) + ".css").toExternalForm());
    }
    private void initializeController() {
        // initialize controller
        controller = new GridPane();
        controller.setId("controller");
        root.setBottom(controller); // root.getChildren().add(controller);
        controller.setGridLinesVisible(true); // debug
        // initialize method triggers in the controller
        final MethodTrigger trigger1 = new MethodTrigger("push", "value");
        GridPane.setConstraints(trigger1, 0, 0);
        controller.getChildren().addAll(trigger1);
        // initialize event listener for method triggers
        trigger1.getButton().setOnAction((event) -> {
            // get method name and parameters
            String name = trigger1.getName();
            ArrayList<Integer> parameters = trigger1.getArguments();
            StringBuilder s = new StringBuilder(name);
            for (int parameter : parameters) {
                s.append(" ").append(parameter);
            }
            System.out.println(s);
            // invoke method
            try {
                Class<?>[] parameterTypes = new Class<?>[parameters.size()];
                for (int i = 0; i < parameters.size(); i ++) {
                    Class cls = parameters.get(i).getClass();
                    try {
                        Field field = cls.getDeclaredField("TYPE");
                        parameterTypes[i] = (Class<?>) field.get(parameters.get(i));
                    } catch (NoSuchFieldException e) {
                        parameterTypes[i] = cls;
                    }
                }
                Method method = visualDS.getClass().getDeclaredMethod(name, parameterTypes);
                method.invoke(visualDS, parameters.toArray());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
    private void initializeCanvas() {
        // initialize canvas
        canvas = new Monitor();
        canvas.setId("canvas");
        root.setCenter(canvas); // root.getChildren().add(canvas);
        //
    }
}
