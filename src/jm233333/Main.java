package jm233333;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import jm233333.ui.SceneMenu;
import jm233333.ui.SceneVisualizer;
import jm233333.visual.VisualizedStack;
import jm233333.visual.VisualizedDataStructure;

/**
 * The {@code Main} class includes the entrance and the main process of the application.
 */
public class Main extends Application {
    /**
     * Initialization of the application.
     * @param primaryStage the stage of the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // initialize director
        Director.getInstance().setPrimaryStage(primaryStage);
        // initialize data structure
//        VisualizedDataStructure visualDS = new VisualizedStack(10);
        // initialize scene
//        BorderPane root = new BorderPane();
//        Scene scene = new SceneVisualizer(root, 1024, 768, visualDS);
        FlowPane root = new FlowPane();
        Scene scene = new SceneMenu(root, 1024, 768);
        // initialize stage
        primaryStage.setTitle("Data Structure Visualizer (under development)");
        primaryStage.setScene(scene);
        primaryStage.show();
        //
        scene.widthProperty().addListener((ov, t, t1) -> {
            System.out.println("Window Size Change:" + t.toString() + "," + t1.toString());
            System.out.println(".");
        });
    }
    /**
     * The main function of the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
