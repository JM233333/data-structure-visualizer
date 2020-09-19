package jm233333.dsv.visual;

import jm233333.dsv.util.Direction;
import jm233333.dsv.util.Offset4;

import java.util.*;

/**
 * The {@code VisualArray} class defines the graphic components of an array that is displayed on the monitor.
 * Used in subclasses of {@code VDS}.
 */
public class VisualArray extends Visual {

    private Direction direction;

    private ArrayList<VisualArrayNode> arrayNode = new ArrayList<>();
    private HashMap<String, VisualIndex> mapIndexField = new HashMap<>();
    
    public VisualArray(String name, Direction direction, int n) {
        // super
        super(name);
        // initialize
        this.getStyleClass().add("visual-array");
        this.direction = direction;
        // initialize nodes
        for (int i = 0; i < n; i ++) {
            VisualArrayNode node = new VisualArrayNode();
            node.setLayoutX(node.getWidth() * i * Offset4.dx[direction.ordinal()]);
            node.setLayoutY(node.getHeight() * i * Offset4.dy[direction.ordinal()]);
            arrayNode.add(node);
            this.getChildren().add(node);
        }
    }

    public void addIndexField(String name, int value) {
        VisualIndex indexField = new VisualIndex(name, value);
        VisualArrayNode firstNode = arrayNode.get(0);
        indexField.setLayoutX(firstNode.getLayoutX() + firstNode.getWidth() * value * Offset4.dx[direction.ordinal()]);
        indexField.setLayoutY(firstNode.getLayoutY() + firstNode.getHeight() * value * Offset4.dy[direction.ordinal()]);
        mapIndexField.put(name, indexField);
        this.getChildren().add(indexField);
    }

    public void updateIndexField(String name, int value) {
        VisualIndex indexField = mapIndexField.get(name);
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
