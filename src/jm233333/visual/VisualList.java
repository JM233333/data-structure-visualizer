package jm233333.visual;

import javafx.scene.Group;

import java.util.HashMap;

/**
 * The {@code VisualList} class defines the graphic components of a list that is displayed on the monitor.
 * Used in subclasses of {@code VisualizedDataStructure}.
 * Extended from JavaFX class {@code Group} only for UI layout.
 */
public class VisualList extends Group {

    private String name;
    private HashMap<String, VisualListNode> mapNode;

    public VisualList(String name) {
        // super
        super();
        // initialize
        this.name = name;
        mapNode = new HashMap<>();
    }

    private VisualListNode createNode(int value) {
        VisualListNode node = new VisualListNode(value);
        mapNode.put(node.getId(), node);
        this.getChildren().add(node);
        return node;
    }

    public void insert(int index, int value) {
        VisualListNode node = createNode(value);
    }

//    public void updateNode(String name, int value) {
//        VisualArrayIndex indexField = mapIndexField.get(name);
//        indexField.setValue(value);
//    }
}
