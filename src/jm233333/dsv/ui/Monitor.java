package jm233333.dsv.ui;

import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import jm233333.dsv.visual.*;

/**
 * Class {@code Monitor} is responsible for displaying the visualized data structure.
 * Extended from JavaFX class {@link ScrollPane} only for UI layout.
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
     * Creates a {@code Monitor}.
     */
    public Monitor() {
        // initialize
        this.setId("monitor");
        // initialize canvas
        canvas = new Pane();
//        canvas.setBackground(new Background(new BackgroundFill(Color.rgb(160, 160, 255), null, null)));
        canvas.setPadding(new Insets(32));
        this.setContent(canvas);
        // initialize data
        mapVisual = new HashMap<>();
        mapIndexFieldConnection = new HashMap<>();
    }

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
     * Creates a {@link VisualArray} in the {@code Monitor}.
     *
     * @param name name of the visual array
     * @param n length of the visual array
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
     * Creates a {@link VisualList} in the {@code Monitor}.
     *
     * @param name name of the visual list
     */
    public void createVisualList(String name) {
        VisualList visualList = new VisualList(name);
        visualList.setLayoutX(64);
        visualList.setLayoutY(64);
        canvas.getChildren().add(visualList);
        mapVisual.put(name, visualList);
    }

    /**
     * Creates a {@link VisualBinarySearchTree} in the {@code Monitor}.
     *
     * @param name name of the visual binary search tree
     */
    public void createVisualBST(String name) {
        // create visual
        VisualBinarySearchTree visualBST = new VisualBinarySearchTree(name);
        visualBST.setLayoutX(VisualBinarySearchTree.PADDING);
        visualBST.setLayoutY(VisualBinarySearchTree.PADDING);
        canvas.getChildren().add(visualBST);
        mapVisual.put(name, visualBST);
    }


    /**
     * Creates a {@link VisualInvocationStack} in the {@code Monitor}.
     */
    public void createVisualInvocationStack() {
        if (!mapVisual.containsKey(VisualInvocationStack.BUILTIN_NAME)) {
            VisualInvocationStack visualInvocationStack = new VisualInvocationStack(VisualInvocationStack.BUILTIN_NAME, 7);
            visualInvocationStack.setLayoutX(this.getWidth() - 128);
            visualInvocationStack.setLayoutY(64);
            canvas.getChildren().add(visualInvocationStack);
            mapVisual.put(VisualInvocationStack.BUILTIN_NAME, visualInvocationStack);
        }
    }
}
