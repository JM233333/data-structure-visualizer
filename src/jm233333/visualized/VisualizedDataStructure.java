package jm233333.visualized;

import com.sun.org.apache.bcel.internal.classfile.Code;
import javafx.util.Pair;

import jm233333.ui.CodeTracker;
import jm233333.ui.Monitor;
import jm233333.visual.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * The {@code VisualizedDataStructure} class provides common properties for all types of visualized data structures.
 */
public abstract class VisualizedDataStructure {

    /**
     * The name of the visualized data structure.
     */
    private String name = "";

    /**
     * The reference to the monitor in which the visualized data structure is displayed.
     */
    private Monitor monitor = null;

    /**
     * The reference to the code tracker that helps displaying the visualized data structure.
     */
    private CodeTracker codeTracker = null;

    /**
     * Records all relationships of (index field -> array).
     *
     * key: name of the index field
     * value: reference to the array that the index field points to
     */
    //private HashMap<String, int[]> mapAssociatedArray;

    public VisualizedDataStructure() {
        //mapAssociatedArray = new HashMap<>();
    }
    public VisualizedDataStructure(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }
    public final String getName() {
        return name;
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
    public void setCodeTracker(CodeTracker codeTracker) {
        codeTracker.readFile(name);
        this.codeTracker = codeTracker;
    }

    /**
     * Tracks to the beginning line of the specified method of the visualized data structure.
     */
    public void trackCodeMethodBeginning(String name) {
        codeTracker.setCurrentMethod(name);
    }
    /**
     * Tracks to the code line that the specified entrance represents.
     */
    public void trackCodeEntrance(String name) {
        codeTracker.gotoEntrance(name);
    }

    /**
     * Gets the current method name of the code tracker.
     */
    public final String getCodeCurrentMethod() {
        return codeTracker.getCurrentMethod();
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
    void createVisualList(String name) {//, Pair<String, Integer>... indexFields) {
        monitor.createVisualList(name);
    }

    final VisualArray getVisualArray(String name) {
        return (VisualArray)monitor.getVisual(name);
    }
    final VisualArray getVisualArrayByIndexField(String name) {
        return (VisualArray)monitor.getVisualByIndexFieldName(name);
    }
    final VisualList getVisualList(String name) {
        return (VisualList)monitor.getVisual(name);
    }
}
