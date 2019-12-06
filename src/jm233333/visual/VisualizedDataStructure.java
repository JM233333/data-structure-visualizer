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
     * the reference to the monitor in which the visualized data structure is displayed.
     */
    private Monitor monitor = null;

    /**
     * maintains the array associated with each index : pairs of (key: index field name, value: array).
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

    protected void createVisualArray(String name, int n, Pair<String, Integer>... indexFields) {
        monitor.createVisualArray(name, n, indexFields);
    }
}
