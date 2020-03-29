package jm233333.visualized;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;

import jm233333.Director;
import jm233333.ui.CodeTracker;
import jm233333.ui.Monitor;
import jm233333.visual.*;

/**
 * The {@code VDS} abstract class provides common properties for all types of VDSs.
 * VDS is the abbreviation of Visualized Data Structure (or Algorithm).
 */
public abstract class VDS {

    private String name = "";

    private Mode mode = null;

    private Monitor monitor = null;
    private CodeTracker codeTracker = null;
    private TextFlow outputBox = null;

    /**
     * Records all relationships of (index field, array).
     *
     * key: name of the index field
     * value: reference to the array that the index field points to
     */
    //private HashMap<String, int[]> mapAssociatedArray;

    public VDS() {
        //mapAssociatedArray = new HashMap<>();
    }

    public VDS(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }
    public final String getName() {
        return name;
    }

    public void setMode(Mode mode) {
        System.out.println(name + " setMode " + mode.toString());
        this.mode = mode;
    }
    public final Mode getMode() {
        return mode;
    }
    public final Class<? extends Mode> getModeClass() {
        String fullName = this.getClass().getName();
        String lastName = fullName.substring(fullName.lastIndexOf('.') + 1);
        String dsName = lastName.split("Visualized")[1];
        try {
            Class<? extends Mode> modeClass = Class.forName("jm233333.visualized.Mode" + dsName).asSubclass(Mode.class);
            return modeClass;
        } catch (ClassNotFoundException e) {
            return null;
        }
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
        codeTracker.processCodeList(name);
        this.codeTracker = codeTracker;
    }
    public void setOutputBox(TextFlow outputBox) {
        this.outputBox = outputBox;
    }

    /**
     * Sets the current method in code-tracking.
     */
    public void trackCodeSetCurrentMethod(String name) {
        codeTracker.setCurrentMethod(name);
    }

    /**
     * Tracks to the beginning code line of the specified method.
     */
    public void trackCodeMethodBeginning(String name) {
        codeTracker.setCurrentMethod(name);
        codeTracker.gotoEntrance(name);
        codeTracker.gotoEntrance(CodeTracker.NEXT_LINE);
    }

    /**
     * Tracks to the code line that the specified entrance represents, and sets a step point on the current timeline.
     *
     * @param name name of the code entrance
     */
    public void trackCodeEntrance(String name) {
        trackCodeEntrance(name, true);
    }

    /**
     * Tracks to the code line that the specified entrance represents.
     *
     * @param name name of the code entrance
     * @param isStepPoint whether exists a step point on the current timeline or not
     */
    public void trackCodeEntrance(String name, boolean isStepPoint) {
        if (isStepPoint) {
            Director.getInstance().addStepPoint();
        }
        codeTracker.gotoEntrance(name);
    }

    /**
     * Gets the current method name of the code tracker.
     */
    public final String getCodeCurrentMethod() {
        return codeTracker.getCurrentMethod();
    }

    /**
     * Outputs message to the output box.
     */
    public void outputMessage(String message) {
        Text text = new Text(message + "\n");
        text.setFont(Font.font(18));
        text.setOpacity(0);
        outputBox.getChildren().add(text);
        Director.getInstance().createAnimation(1.0, text.opacityProperty(), 1);
    }
    /**
     * Clears the output box.
     */
    public void clearOutputBox() {
        outputBox.getChildren().clear();
    }

    /**
     * Creates graphic components of the visualized data structure for displaying.
     */
    public abstract void createVisual();

    public void createVisualArray(String name, int n, Pair<String, Integer>... indexFields) {
        monitor.createVisualArray(name, n);
        for (Pair<String, Integer> indexField : indexFields) {
            createVisualIndexField(name, indexField);
        }
    }
    public void createVisualIndexField(String nameArray, Pair<String, Integer> indexField) {
        getVisualArray(nameArray).addIndexField(indexField.getKey(), indexField.getValue());
        monitor.addIndexFieldConnection(indexField.getKey(), nameArray);
    }
    public void createVisualList(String name) {//, Pair<String, Integer>... indexFields) {
        monitor.createVisualList(name);
    }
    public void createVisualBST(String name) {
        monitor.createVisualBST(name);
    }

    public final VisualArray getVisualArray(String name) {
        return (VisualArray)monitor.getVisual(name);
    }
    public final VisualList getVisualList(String name) {
        return (VisualList)monitor.getVisual(name);
    }
    public final VisualBinarySearchTree getVisualBST(String name) {
        return (VisualBinarySearchTree)monitor.getVisual(name);
    }
}
