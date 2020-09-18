package jm233333.dsv.visualized;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;

import jm233333.dsv.Director;
import jm233333.dsv.ui.CodeTracker;
import jm233333.dsv.ui.Monitor;
import jm233333.dsv.visual.*;

/**
 * Abstract class {@code VDS} provides common properties for all types of VDSs (VDS is the abbreviation of Visualized Data Structure or Algorithm).
 *
 * <p>
 *     detailed description of class VDS to-be-done...
 * </p>
 */
public abstract class VDS {

    private String name = "";

    private Mode mode = null;

    private Monitor monitor = null;
    private CodeTracker codeTracker = null;
    private TextFlow outputBox = null;

    /*/**
     * Records all relationships of (index field, array).
     *
     * key: name of the index field
     * value: reference to the array that the index field points to
     *
    //private HashMap<String, int[]> mapAssociatedArray;

    public VDS() {
        //mapAssociatedArray = new HashMap<>();
    }*/

    public VDS() {}

    /**
     * Creates a {@code VDS} with a specific name.
     *
     * @param name the name of the VDS
     */
    public VDS(String name) {
        setName(name);
    }

    /**
     * Sets the name of {@code VDS}.
     *
     * @param name the new name of the VDS
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the name of {@code VDS}.
     *
     * @return the name of the VDS
     */
    public String getName() {
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
            Class<? extends Mode> modeClass = Class.forName("jm233333.dsv.visualized.Mode" + dsName).asSubclass(Mode.class);
            return modeClass;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Sets the {@link Monitor} associated with {@code VDS}.
     *
     * @param monitor the monitor
     */
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

    /**
     * Sets the {@link CodeTracker} associated with {@code VDS}.
     *
     * @param codeTracker the code tracker
     */
    public void setCodeTracker(CodeTracker codeTracker) {
        codeTracker.parseCodeList(name);
        this.codeTracker = codeTracker;
    }

    /**
     * Sets the output box (a {@link TextFlow}) associated with {@code VDS}.
     *
     * @param outputBox the output box
     */
    public void setOutputBox(TextFlow outputBox) {
        this.outputBox = outputBox;
    }

    /**
     * Instruct the associated {@link CodeTracker} to track into the method of the specified name.
     * Calls {@link CodeTracker#setCurrentMethod(String)}.
     *
     * @param name name of the method to go
     */
    public void trackCodeSetCurrentMethod(String name) {
        codeTracker.setCurrentMethod(name);
    }

    /**
     * Gets the name of the method that the the associated {@link CodeTracker} is currently tracking in.
     *
     * @return name of the method that the the associated code tracker is currently tracking in
     */
    public final String trackCodeGetCurrentMethod() {
        return codeTracker.getCurrentMethod();
    }

    /**
     * Instruct the associated {@link CodeTracker} to track into the method of the specified name and then track to its beginning line.
     * Calls {@link VDS#trackCodeSetCurrentMethod(String)} and {@link VDS#trackCodeEntrance(String, boolean)}.
     *
     * @param name name of the method to go
     */
    public void trackCodeMethodBeginning(String name) {
        trackCodeMethodBeginning(name, true);
    }
    public void trackCodeMethodBeginning(String name, boolean sync) {
        trackCodeEntrance(name, sync);
        trackCodeSetCurrentMethod(name);
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
    }

    /**
     * Instruct the associated {@link CodeTracker} to track into the line of the specified entrance name in the current method,
     * and set a step point into the animation waiting list of {@link Director}.
     * Factually calls {@link VDS#trackCodeEntrance(String, boolean)} with parameters ({@code name}, {@code true}).
     *
     * @param name name of the entrance
     */
    public void trackCodeEntrance(String name) {
        trackCodeEntrance(name, true);
    }

    /**
     * Instruct the associated {@link CodeTracker} to track into the line of the specified entrance name in the current method.
     *
     * @param name name of the entrance
     * @param isStepPoint if sets a step point into the animation waiting list of {@link Director}
     */
    public void trackCodeEntrance(String name, boolean isStepPoint) {
        if (isStepPoint) {
            Director.getInstance().addStepPoint();
        }
        codeTracker.gotoEntrance(name);
    }

    /**
     * Outputs message to the associated output box.
     *
     * @param message the message sent to the associated output box.
     * @param fontSize the font size of the message.
     * @param fontColor the font color of the message.
     */
    public void outputMessage(String message, int fontSize, Color fontColor, boolean newLine) {
        Text text = new Text(newLine ? message + "\n" : message);
        text.setFont(Font.font(fontSize));
        text.setFill(fontColor);
        text.setOpacity(0);
        text.fontProperty().addListener((ev, ov, nv) -> {
            text.setFont(Font.font(fontSize));
        });
        outputBox.getChildren().add(text);
        Director.getInstance().createAnimation(0.25, text.opacityProperty(), 1.0);
    }
    public void outputMessage(String message, int fontSize, Color fontColor) {
        outputMessage(message, fontSize, fontColor, true);
    }
    public void outputMessageInvoke(String message) {
        outputMessage(message, 18, Color.BLACK);
    }
    public <T> void outputMessageReturn(T message) {
        outputMessage(" | RET : " + message, 16, Color.BLACK);
    }
    public void outputMessageError(String message) {
        outputMessage(" | ERR : " + message, 16, Color.RED);
    }
    public void outputMessageDebug(String message) {
        outputMessage(" | [DBG] " + message, 16, Color.DARKGREEN);
    }

    /**
     * Clears the associated output box.
     */
    public void clearOutputBox() {
        outputBox.getChildren().clear();
    }

    /**
     * Creates graphic components (e.g. instances of {@link Visual}) of the {@code VDS}.
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
