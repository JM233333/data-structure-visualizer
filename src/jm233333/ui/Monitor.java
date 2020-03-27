package jm233333.ui;

import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import jm233333.visual.*;

/**
 * The {@code Monitor} class is responsible for displaying the visualized data structure.
 * Extended from JavaFX class {@code Pane} only for UI layout.
 */
public class Monitor extends ScrollPane {

    /**
     * Displays the graphic components of the visualized data structure.
     */
    private final Pane canvas;

    /**
     * Records all instances of {@code VisualArray}.
     *
     * key: name of the array
     * value: reference to the corresponding visual array
     */
    private HashMap<String, Visual> mapVisual;

    /**
     * Records all relationships of (index field, array).
     *
     * key: name of the index field
     * value: name of the array that the index field points to
     */
    private HashMap<String, String> mapIndexFieldConnection;

    /**
     * Creates a Monitor.
     */
    public Monitor() {
        // initialize
        this.setId("monitor");
        // initialize canvas
        canvas = new Pane();
        canvas.setBackground(new Background(new BackgroundFill(Color.rgb(160, 160, 255), null, null)));
        canvas.setPadding(new Insets(32));
        this.setContent(canvas);
//        this.getChildren().add(canvas);
        // initialize data
        mapVisual = new HashMap<>();
        mapIndexFieldConnection = new HashMap<>();
    }
//    public <T> void updateField(String name, Class type, T value) {
//        fieldMap.computeIfAbsent(type, k -> new HashMap<>());
//        fieldMap.get(type).put(name, value);
//    }

    /**
     * Gets the visual component by name.
     *
     * @param name name of the visual component
     */
    public final Visual getVisual(String name) {
        return mapVisual.get(name);
    }

    /**
     * Gets the visual array that a specified index field points to.
     *
     * @param name name of the index field
     */
    public final Visual getVisualByIndexFieldName(String name) {
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
    public void createVisualBST(String name) {
        // create visual
        VisualBinarySearchTree visualBST = new VisualBinarySearchTree(name);
        visualBST.setLayoutX(VisualBinarySearchTree.PADDING);
        visualBST.setLayoutY(VisualBinarySearchTree.PADDING);
        canvas.getChildren().add(visualBST);
        mapVisual.put(name, visualBST);
    }
}
