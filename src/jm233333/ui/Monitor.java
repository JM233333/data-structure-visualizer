package jm233333.ui;

import java.util.HashMap;

import javafx.scene.*;
import javafx.scene.layout.*;

import jm233333.visual.*;

/**
 * The {@code Monitor} class is responsible for displaying the visualized data structure.
 * Extended from JavaFX class {@code Pane} only for UI layout.
 */
public class Monitor extends Pane {

    /**
     * Displays the graphic components of the visualized data structure.
     */
    private final Group canvas;

    /**
     * Records all instances of {@code VisualArray}.
     *
     * key: name of the array
     * value: reference to the corresponding visual array
     */
    private HashMap<String, Group> mapVisual;

    /**
     * Records all relationships of (index field -> array).
     *
     * key: name of the index field
     * value: name of the array that the index field points to
     */
    private HashMap<String, String> mapIndexFieldConnection;

    /**
     * Creates a Monitor.
     */
    public Monitor() {
        // super
        super();
        // initialize CSS
        initializeCSS();
        // initialize canvas
        canvas = new Group();
        this.getChildren().add(canvas);
        // initialize data
        mapVisual = new HashMap<>();
        mapIndexFieldConnection = new HashMap<>();
    }
//    public <T> void updateField(String name, Class type, T value) {
//        fieldMap.computeIfAbsent(type, k -> new HashMap<>());
//        fieldMap.get(type).put(name, value);
//    }

    /**
     * Initializes CSS.
     */
    private void initializeCSS() {
        Class cls = this.getClass();
        String fullName = cls.getName();
        String lastName = fullName.substring(fullName.lastIndexOf('.') + 1);
        this.getStylesheets().add(cls.getResource("/jm233333/css/" + lastName + ".css").toExternalForm());
    }

    /**
     * Gets the visual component by name.
     *
     * @param name name of the visual component
     */
    public final Group getVisual(String name) {
        return mapVisual.get(name);
    }

    /**
     * Gets the visual array that a specified index field points to.
     *
     * @param name name of the index field
     */
    public final Group getVisualByIndexFieldName(String name) {
        String nameVisual = mapIndexFieldConnection.get(name);
        System.out.println("NAME VISUAL " + nameVisual);
        return mapVisual.get(nameVisual);
    }

    /**
     * Creates graphic components that represent an array.
     *
     * @param name name of the array
     * @param n length of the array
     */
    public void createVisualArray(String name, int n) {
        VisualArray visualArray = new VisualArray(name, n);
        visualArray.setLayoutX(64);
        visualArray.setLayoutY(64);
        canvas.getChildren().add(visualArray);
        mapVisual.put(name, visualArray);
    }

    /**
     * Adds a connection from an index field to the array that it belongs to.
     *
     * @param name name of the index field
     * @param nameArray name of the array
     */
    public void addIndexFieldConnection(String name, String nameArray) {
        mapIndexFieldConnection.put(name, nameArray);
    }

    /**
     * Creates graphic components that represent a list.
     *
     * @param name name of the list
     */
    public void createVisualList(String name) {
        VisualList visualList = new VisualList(name);
        visualList.setLayoutX(64);
        visualList.setLayoutY(64);
        canvas.getChildren().add(visualList);
        mapVisual.put(name, visualList);
    }

    /**
     * Creates graphic components that represent a binary tree.
     *
     * @param name name of the binary tree
     */
    public void createVisualBinaryTree(String name) {
        ;
    }
}
