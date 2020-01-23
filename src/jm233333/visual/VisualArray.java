package jm233333.visual;

import javafx.scene.Group;

import java.util.*;

/**
 * The {@code VisualArray} class defines the graphic components of an array that is displayed on the monitor.
 * Used in subclasses of {@code VisualizedDataStructure}.
 * Extended from JavaFX class {@code Group} only for UI layout.
 */
public class VisualArray extends Group {

    private String name;
    private ArrayList<VisualArrayNode> arrayNode;
    private HashMap<String, VisualArrayIndex> mapIndexField;
    
    public VisualArray(String name, int n) {
        // super
        super();
        // initialize
        this.name = name;
        arrayNode = new ArrayList<>();
        mapIndexField = new HashMap<>();
        // add empty nodes
        for (int i = 0; i < n; i ++) {
            VisualArrayNode node = new VisualArrayNode();
            node.setLayoutX(node.getWidth() * i);
            arrayNode.add(node);
            this.getChildren().add(node);
        }
    }

    public void addIndexField(String name, int value) {
        VisualArrayIndex indexField = new VisualArrayIndex(name, value);
        indexField.setLayoutX(arrayNode.get(0).getWidth() * value);
        indexField.setLayoutY(arrayNode.get(0).getHeight());
        mapIndexField.put(name, indexField);
        this.getChildren().add(indexField);
    }

    public void updateIndexField(String name, int value) {
        VisualArrayIndex indexField = mapIndexField.get(name);
        indexField.setValue(value);
    }

    public void updateElement(int index, int value) {
        VisualArrayNode node = arrayNode.get(index);
        node.setValue(value);
    }

    public void eraseElement(int index) {
        VisualArrayNode node = arrayNode.get(index);
        node.clear();
    }
}
