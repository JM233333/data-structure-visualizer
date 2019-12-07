package jm233333.visual;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import jm233333.ui.Monitor;

import java.lang.reflect.*;
import java.util.HashMap;

/**
 * The {@code VisualizedDataStructure} class provides common properties for all types of visualized data structures.
 */
public abstract class VisualizedDataStructure {

    /**
     * The reference to the monitor in which the visualized data structure is displayed.
     */
    private Monitor monitor = null;

    /**
     * Records all relationships of (index field -> array).
     *
     * key: name of the index field
     * value: reference to the array that the index field points to
     */
    private HashMap<String, int[]> mapAssociatedArray;

    public VisualizedDataStructure() {
        mapAssociatedArray = new HashMap<>();
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
        createVisual();
//        Field[] fs = this.getClass().getDeclaredFields();
//        for (Field field : fs) {
//            Class fieldType = field.getType();
//            String typeName = fieldType.getName();
//            String fieldName = field.getName();
//            System.out.println(typeName + " " + fieldName);
//        }
    }
    public final Monitor getMonitor() {
        return monitor;
    }

    /**
     * Returns an empty box which will be used as a placeholder for displaying.
     */
    public static Rectangle getEmptyBox(double width, double height) {
        return new Rectangle(width, height, Color.TRANSPARENT);
    }

    /**
     * Creates graphic components of the visualized data structure for displaying.
     */
    abstract void createVisual();

    void createVisualArray(String name, int n, Pair<String, Integer>... indexFields) {
        monitor.createVisualArray(name, n);
        for (Pair<String, Integer> indexField : indexFields) {
            monitor.addIndexField(name, indexField);
        }
    }

    protected void updateIndexField(String name, int value) {
        try {
            // get the field
            Field field = this.getClass().getDeclaredField(name);
            // store the old value
            field.setAccessible(true);
            int oldValue = (int)field.get(this);
            // update data of the index field itself
            field.set(this, value);
            field.setAccessible(false);
            // update data of the index field in the corresponding visual array
            monitor.updateIndexField(name, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}