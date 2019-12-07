package jm233333.visual;

import javafx.scene.Group;
import javafx.scene.layout.*;

import java.util.*;

/**
 * The {@code VisualArray} class defines the graphic components of an array that is displayed on the monitor.
 * Used in subclasses of {@code VisualizedDataStructure}.
 * Extended from JavaFX class {@code VBox} only for UI layout.
 */
public class VisualArray extends VBox {

    private String name;
    private HBox boxArray, boxIndexFieldUp, boxIndexFieldDown;
    private HashMap<String, VisualArrayIndex> indexFieldMap;
    
    public VisualArray(String name, int n) {
        // super
        super();
        // initialize name
        this.name = name;
        // initialize HBox that stores the array
        boxArray = new HBox();
        for (int i = 0; i < n; i ++) {
            boxArray.getChildren().add(new VisualArrayNode());
        }
        // initialize HBox that stores the index fields
        boxIndexFieldUp = new HBox();
        boxIndexFieldDown = new HBox();
        for (int i = 0; i < n; i ++) {
            boxIndexFieldUp.getChildren().add(new VisualEmptyBox(64, 64));
            boxIndexFieldDown.getChildren().add(new VisualEmptyBox(64, 64));
        }
        // set as children
        this.getChildren().addAll(boxIndexFieldUp, boxArray, boxIndexFieldDown);
        // initialize HashMap that maps the index fields
        indexFieldMap = new HashMap<>();
    }

    public void addIndexField(String name, int value) {
        VisualArrayIndex visualArrayIndex = new VisualArrayIndex(name, value);
        indexFieldMap.put(name, visualArrayIndex);
        boxIndexFieldDown.getChildren().set(value, visualArrayIndex);
    }

    public void updateIndexField(String name, int value) {
        VisualArrayIndex visualArrayIndex = indexFieldMap.get(name);
        boxIndexFieldDown.getChildren().set(visualArrayIndex.getValue(), new VisualEmptyBox(64, 64));
        boxIndexFieldDown.getChildren().set(value, visualArrayIndex);
        visualArrayIndex.setValue(value);
    }

    public void updateArrayElement(int index, int value) {
        VisualArrayNode element = (VisualArrayNode)boxArray.getChildren().get(index);
        element.setValue(value);
    }

    public void eraseArrayElement(int index) {
        VisualArrayNode element = (VisualArrayNode)boxArray.getChildren().get(index);
        element.clear();
    }
}
