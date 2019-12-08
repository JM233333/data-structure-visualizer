package jm233333.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import jm233333.Director;
import jm233333.visualized.VisualizedDataStructure;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * The {@code SceneMenu} class maintains a scene graph for the main menu of the application.
 * Extended from JavaFX class {@code Scene}.
 */
public class SceneMenu extends Scene {

    private FlowPane root;
    private ScrollBar scrollBar;

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
        scrollBar = new ScrollBar();
        root.getChildren().add(scrollBar);
        initializeList();
    }
    private void initializeList() {
//        File file = new File(".");
//        for(String fileNames : file.list()) System.out.println(fileNames);
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/data/menu.txt"));
            String str;
            while (in.ready()) {
                str = "Visualized" + in.readLine();
                System.out.println(str);
                // add button
                addButton(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addButton(String name) {
        // initialize buttons linked to corresponding SceneVisualizer
        try {
            // get class type
            Class<?> classType = Class.forName("jm233333.visualized." + name);
            // get needed constructor
            Class<?>[] parameterTypes = {int.class};
            Constructor constructor = classType.getConstructor(parameterTypes);
            Object[] parameters = {10};
            // create a new instance
            final VisualizedDataStructure visualDS = (VisualizedDataStructure)constructor.newInstance(parameters);
            // initialize button
            Button button = new Button(name);
            root.getChildren().add(button);
            // set listener
            button.setOnAction((event) -> {
                Scene scene = new SceneVisualizer(new BorderPane(), 1024, 768, visualDS);
                Director.getInstance().getPrimaryStage().setScene(scene);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
