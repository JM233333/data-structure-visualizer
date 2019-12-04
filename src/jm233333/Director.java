package jm233333;

import javafx.stage.Stage;

/**
 * The {@code Director} class is a singleton class that maintains global data and controls the overall program.
 */
public class Director {
    /**
     * Defines the unique instance of Director.
     */
    private static Director instance = new Director();
    /**
     * Creates the unique instance of Director.
     */
    private Director() {}
    /**
     * Gets the unique instance of Director.
     * @return reference to the unique instance of Director
     */
    public static Director getInstance() {
        return instance;
    }
    /**
     * The stage of the application.
     */
    private Stage primaryStage;
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public final Stage getPrimaryStage() {
        return primaryStage;
    }
    /**
     * Initialize the unique instance of Director.
     */
    public void initialize(Stage primaryStage) {
        setPrimaryStage(primaryStage);
    }
}
