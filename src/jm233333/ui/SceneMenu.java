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
import java.util.Arrays;

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
            while (in.ready()) {
                String[] args = in.readLine().split(" ");
                assert (args.length > 0);
                addButton(args);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addButton(String... args) {
        // get class data
        String className = "Visualized" + args[0];
        int argc = args.length - 1;
        Class<?>[] parameterTypes = new Class<?>[argc];
        Object[] parameters = new Object[argc];
        for (int i = 0; i < argc; i ++) {
            parameterTypes[i] = int.class;
            parameters[i] = Integer.parseInt(args[i + 1]);
        }
        // initialize buttons linked to corresponding SceneVisualizer
        try {
            // get class type
            Class<?> classType = Class.forName("jm233333.visualized." + className);
            // get needed constructor
            //Class<?>[] parameterTypes = {int.class};
            //Object[] parameters = {10};
            Constructor constructor = classType.getConstructor(parameterTypes);
            // create a new instance
            final VisualizedDataStructure visualDS = (VisualizedDataStructure)constructor.newInstance(parameters);
            // initialize button
            String name = className + " " + Arrays.toString(parameters);
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
