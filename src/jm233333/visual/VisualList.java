package jm233333.visual;

import javafx.scene.Group;
import jm233333.Director;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The {@code VisualList} class defines the graphic components of a list that is displayed on the monitor.
 * Used in subclasses of {@code VisualizedDataStructure}.
 * Extended from JavaFX class {@code Group} only for UI layout.
 */
public class VisualList extends Group {

    private String name;
    private HashMap<String, VisualListNode> mapNode;
    private ArrayList<VisualListNode> arrayNode;

    public VisualList(String name) {
        // super
        super();
        // initialize
        this.name = name;
        mapNode = new HashMap<>();
        arrayNode = new ArrayList<>();
    }

    private VisualListNode createNode(int value) {
        VisualListNode node = new VisualListNode(value);
        node.setLayoutX(32);
        node.setLayoutY(96);
        mapNode.put(node.getId(), node);
        this.getChildren().add(node);
        return node;
    }

    public void pushFrontNode(int value) {
        VisualListNode newNode = createNode(value);
        if (!arrayNode.isEmpty()) {
            VisualListNode lastNode = arrayNode.get(arrayNode.size() - 1);
            newNode.setPointer(lastNode);
            Visual.createAnimation(500, lastNode.layoutXProperty(), lastNode.getLayoutX() + 128);
            for (int i = arrayNode.size() - 2; i >= 0; i --) {
                VisualListNode node = arrayNode.get(i);
                Visual.updateAnimation(500, node.layoutXProperty(), node.getLayoutX() + 128);
            }
        }
        arrayNode.add(newNode);
        Visual.createAnimation(500, newNode.layoutYProperty(), 0);
        Director.getInstance().playAnimation();
    }

//    public void updateNode(String name, int value) {
//        VisualArrayIndex indexField = mapIndexField.get(name);
//        indexField.setValue(value);
//        Visual.createAnimation(500, node.layoutXProperty(), 64 * value);
//    }
}
