package jm233333.visualized;

import javafx.util.Pair;
import jm233333.ui.Monitor;
import jm233333.visual.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.security.acl.Group;
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
     * Creates graphic components of the visualized data structure for displaying.
     */
    abstract void createVisual();

    void createVisualArray(String name, int n, Pair<String, Integer>... indexFields) {
        monitor.createVisualArray(name, n);
        for (Pair<String, Integer> indexField : indexFields) {
            createVisualIndexField(name, indexField);
        }
    }
    void createVisualIndexField(String nameArray, Pair<String, Integer> indexField) {
        getVisualArray(nameArray).addIndexField(indexField.getKey(), indexField.getValue());
        monitor.addIndexFieldConnection(indexField.getKey(), nameArray);
    }
    void createVisualList(String name, Pair<String, Integer>... indexFields) {
        monitor.createVisualList(name);
    }

    final VisualArray getVisualArray(String name) {
        return (VisualArray)monitor.getVisual(name);
    }
    final VisualList getVisualList(String name) {
        return (VisualList)monitor.getVisual(name);
    }

    void updateArrayElement(String name, int index, int value) {
        try {
            // get the field
            Field field = this.getClass().getDeclaredField(name);
            // update data of array[index] itself
            field.setAccessible(true);
            int[] array = (int[])field.get(this);
            Array.set(array, index, value);
            field.setAccessible(false);
            // update data of array[index] in the corresponding visual array
            getVisualArray(name).updateElement(index, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    void eraseArrayElement(String name, int index) {
        // lazy-erase data of array[index] in the corresponding visual array
        getVisualArray(name).eraseElement(index);
    }
    void updateIndexField(String name, int value) {
        try {
            // get the field
            Field field = this.getClass().getDeclaredField(name);
            // update data of the index field itself
            field.setAccessible(true);
            field.set(this, value);
            field.setAccessible(false);
            // update data of the index field in the corresponding visual array
            ((VisualArray)monitor.getVisualByIndexFieldName(name)).updateIndexField(name, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    void createListNode(int value) {
        ;
    }
    void pushFrontListNode(String name, int value) {
        getVisualList(name).pushFrontNode(value);
    }
}
