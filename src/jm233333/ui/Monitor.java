package jm233333.ui;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.util.Pair;
import jm233333.visual.VisualArray;

import java.util.HashMap;

/**
 * The {@code Monitor} class is responsible for displaying the visualized data structure.
 * Extended from JavaFX class {@code Pane} only for UI layout.
 */
public class Monitor extends Pane {

    /**
     * Maintains all the graphic components displayed on the monitor.
     */
    private final Group canvas;

    /**
     * Records all instances of {@code VisualArray}.
     *
     * key: name of the array
     * value: reference to the corresponding visual array
     */
    private HashMap<String, VisualArray> mapVisualArray;

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
        super();
        canvas = new Group();
        this.getChildren().add(canvas);
        mapVisualArray = new HashMap<>();
        mapIndexFieldConnection = new HashMap<>();
    }
//    public <T> void updateField(String name, Class type, T value) {
//        fieldMap.computeIfAbsent(type, k -> new HashMap<>());
//        fieldMap.get(type).put(name, value);
//    }

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
        mapVisualArray.put(name, visualArray);
    }

    /**
     * Adds an index field and binds it to a specific array.
     * Directly jumps into {@code addIndexField(nameArray, data.name, data.value)} in reality.
     *
     * @param nameArray name of the array
     * @param indexField data of the index field
     */
    public void addIndexField(String nameArray, Pair<String, Integer> indexField) {
        addIndexField(nameArray, indexField.getKey(), indexField.getValue());
    }

    /**
     * Adds an index field and binds it to a specific array.
     *
     * @param nameArray name of the array
     * @param name name of the index field
     * @param value initial value of the index field
     */
    public void addIndexField(String nameArray, String name, int value) {
        VisualArray visualArray = mapVisualArray.get(nameArray);
        visualArray.addIndexField(name, value);
        mapIndexFieldConnection.put(name, nameArray);
    }

    public void updateIndexField(String name, int value) {
        String nameArray = mapIndexFieldConnection.get(name);
        VisualArray visualArray = mapVisualArray.get(nameArray);
        visualArray.updateIndexField(name, value);
    }

    public void updateArrayElement(String name, int index, int value) {
        VisualArray visualArray = mapVisualArray.get(name);
        visualArray.updateArrayElement(index, value);
    }

    public void eraseArrayElement(String name, int index) {
        VisualArray visualArray = mapVisualArray.get(name);
        visualArray.eraseArrayElement(index);
    }
}
