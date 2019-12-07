package jm233333.visual;

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
            boxIndexFieldUp.getChildren().add(VisualizedDataStructure.getEmptyBox(64, 64));
            boxIndexFieldDown.getChildren().add(VisualizedDataStructure.getEmptyBox(64, 64));
        }
        // set as children
        this.getChildren().addAll(boxIndexFieldUp, boxArray, boxIndexFieldDown);
        // initialize HashMap that maps the index fields
        indexFieldMap = new HashMap<>();
    }

    public void addIndexField(String name, int value) {
        VisualArrayIndex index = new VisualArrayIndex(name, value);
        indexFieldMap.put(name, index);
        boxIndexFieldDown.getChildren().set(value, index);
    }

    public void updateIndexField(String name, int value) {
        VisualArrayIndex index = indexFieldMap.get(name);
        boxIndexFieldDown.getChildren().remove(index.getValue());
        boxIndexFieldDown.getChildren().set(value, index);
        index.setValue(value);
    }
}
