package jm233333.ui;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.util.Pair;
import jm233333.visual.*;

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
        super();
        canvas = new Group();
        this.getChildren().add(canvas);
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
    public final Group getVisual(String name) {
        return mapVisual.get(name);
    }
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

//    public void updateIndexField(String name, int value) {
//        String nameArray = mapIndexFieldConnection.get(name);
//        VisualArray visualArray = (VisualArray)mapVisual.get(nameArray);
//        visualArray.updateIndexField(name, value);
//    }
//
//    public void updateArrayElement(String name, int index, int value) {
//        VisualArray visualArray = (VisualArray)mapVisual.get(name);
//        visualArray.updateElement(index, value);
//    }
//
//    public void eraseArrayElement(String name, int index) {
//        VisualArray visualArray = (VisualArray)mapVisual.get(name);
//        visualArray.eraseElement(index);
//    }


    public void createVisualList(String name) {
        VisualList visualList = new VisualList(name);
        visualList.setLayoutX(64);
        visualList.setLayoutY(64);
        canvas.getChildren().add(visualList);
        mapVisual.put(name, visualList);
    }

//    public void pushFrontListNode(String nameList, int value) {
//        VisualList visualList = (VisualList)mapVisual.get(nameList);
//        visualList.pushFrontNode(value);
//    }
}
