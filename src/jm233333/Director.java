package jm233333;

import javafx.stage.Stage;

/**
 * The {@code Director} class is a singleton class that maintains global data and controls the overall program.
 */
class Director {
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
    private Stage privateStage;
    public void setPrivateStage(Stage privateStage) {
        this.privateStage = privateStage;
    }
    public Stage getPrivateStage() {
        return privateStage;
    }
    /**
     * Initialize the unique instance of Director.
     */
    public void initialize(Stage privateStage) {
        setPrivateStage(privateStage);
    }
}
