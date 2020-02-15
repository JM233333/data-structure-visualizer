package jm233333.visual;

import javafx.scene.Group;
import jm233333.Director;

import java.util.ArrayList;

/**
 * The {@code VisualList} class defines the graphic components of a list that is displayed on the monitor.
 * Used in subclasses of {@code VisualizedDataStructure}.
 * Extended from JavaFX class {@code Group} only for UI layout.
 */
public class VisualList extends Group {

    public static final int CACHED_NODE = -1;

    private String name;
    private ArrayList<VisualListNode> arrayNode = new ArrayList<>();
    private VisualListNode cachedNode = null;

    public VisualList(String name) {
        // super
        super();
        // initialize
        this.name = name;
    }

    public void cacheNode(int index, int value) {
        if (cachedNode == null) {
            cachedNode = createNode(index, value);
        }
    }
    public void insertCachedNode(int index) {
        if (cachedNode != null) {
            arrayNode.add(index, cachedNode);
            Visual.createAnimation(500, cachedNode.layoutYProperty(), 0);
            cachedNode = null;
        }
    }

    public void setPointer(int indexFrom, int indexTo) {
        VisualListNode nodeFrom = (indexFrom == CACHED_NODE ? cachedNode : arrayNode.get(indexFrom));
        VisualListNode nodeTo = (indexTo == CACHED_NODE ? cachedNode : arrayNode.get(indexTo));
        nodeFrom.setPointer(nodeTo);
    }
    public void moveRestNodes(int begin, int distance) {
        VisualListNode lastNode = arrayNode.get(begin);
        Visual.createAnimation(500, lastNode.layoutXProperty(), lastNode.getLayoutX() + 128 * distance);
        for (int i = begin + 1; i < arrayNode.size(); i ++) {
            VisualListNode node = arrayNode.get(i);
            Visual.updateAnimation(500, node.layoutXProperty(), node.getLayoutX() + 128 * distance);
        }
    }

    private VisualListNode createNode(int index, int value) {
        VisualListNode node = new VisualListNode(value);
        node.setLayoutX(32 + index * (32 + node.getWidth()));
        node.setLayoutY(96);
        this.getChildren().add(node);
        return node;
    }
    private void removeNode(int index) {
        VisualListNode node = arrayNode.remove(index);
        Visual.createAnimation(500, node.scaleXProperty(), 0);
        Visual.updateAnimation(500, node.scaleYProperty(), 0);
        Director.getInstance().getLastTimeline().setOnFinished((event) -> {
            this.getChildren().remove(node);
        });
    }

    public void pushFrontNode(int value) {
        // create node
        VisualListNode newNode = createNode(0, value);
        // set pointer and move old nodes
        if (!arrayNode.isEmpty()) {
            // set pointer
            VisualListNode lastNode = arrayNode.get(0);
            newNode.setPointer(lastNode);
            // move old nodes
            Visual.createAnimation(500, lastNode.layoutXProperty(), lastNode.getLayoutX() + 128);
            for (int i = 1; i < arrayNode.size(); i ++) {
                VisualListNode node = arrayNode.get(i);
                Visual.updateAnimation(500, node.layoutXProperty(), node.getLayoutX() + 128);
            }
        }
        // insert new node
        arrayNode.add(0, newNode);
        Visual.createAnimation(500, newNode.layoutYProperty(), 0);
        Director.getInstance().playAnimation();
    }

    public void insertNode(int index, int value) {
        // special judge
        if (index == 0) {
            pushFrontNode(value);
            return;
        }
        // create node
        VisualListNode newNode = createNode(index, value);
        // set pointer
        VisualListNode prvNode = arrayNode.get(index - 1);
        prvNode.setPointer(newNode);
        if (index < arrayNode.size()) {
            VisualListNode nxtNode = arrayNode.get(index);
            newNode.setPointer(nxtNode);
        }
        // move old nodes
        if (index < arrayNode.size()) {
            VisualListNode lastNode = arrayNode.get(index);
            Visual.createAnimation(500, lastNode.layoutXProperty(), lastNode.getLayoutX() + 128);
            for (int i = index + 1; i < arrayNode.size(); i ++) {
                VisualListNode node = arrayNode.get(i);
                Visual.updateAnimation(500, node.layoutXProperty(), node.getLayoutX() + 128);
            }
        }
        // insert new node
        arrayNode.add(index, newNode);
        Visual.createAnimation(500, newNode.layoutYProperty(), 0);
        Director.getInstance().playAnimation();
    }

    public void updateNode(int index, int value) {
        VisualListNode node = arrayNode.get(index);
        node.setValue(value);
    }

    public void popFrontNode() {
        // get erased node
        VisualListNode erasedNode = arrayNode.get(0);
        Visual.createAnimation(500, erasedNode.layoutYProperty(), 96);
        // move rest nodes
        if (arrayNode.size() > 1) {
            VisualListNode lastNode = arrayNode.get(1);
            Visual.createAnimation(500, lastNode.layoutXProperty(), lastNode.getLayoutX() - 128);
            for (int i = 2; i < arrayNode.size(); i ++) {
                VisualListNode node = arrayNode.get(i);
                Visual.updateAnimation(500, node.layoutXProperty(), node.getLayoutX() - 128);
            }
        }
        // remove erased node
        removeNode(0);
        Director.getInstance().playAnimation();
    }

    public void eraseNode(int index) {
        // special judge
        if (index == 0) {
            popFrontNode();
            return;
        }
        // get erased node
        VisualListNode erasedNode = arrayNode.get(index);
        Visual.createAnimation(500, erasedNode.layoutYProperty(), 96);
        // set pointer
        VisualListNode prvNode = arrayNode.get(index - 1);
        if (index + 1 < arrayNode.size()) {
            VisualListNode nxtNode = arrayNode.get(index + 1);
            prvNode.setPointer(nxtNode);
        } else {
            prvNode.setPointer(null);
        }
        // move rest nodes
        if (index + 1 < arrayNode.size()) {
            VisualListNode lastNode = arrayNode.get(index + 1);
            Visual.createAnimation(500, lastNode.layoutXProperty(), lastNode.getLayoutX() - 128);
            for (int i = index + 2; i < arrayNode.size(); i ++) {
                VisualListNode node = arrayNode.get(i);
                Visual.updateAnimation(500, node.layoutXProperty(), node.getLayoutX() - 128);
            }
        }
        // remove erased node
        removeNode(index);
        Director.getInstance().playAnimation();
    }
}
