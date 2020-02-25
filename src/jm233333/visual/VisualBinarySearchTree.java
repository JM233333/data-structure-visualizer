package jm233333.visual;

import java.util.ArrayList;

import jm233333.Director;

/**
 * The {@code VisualBinarySearchTree} class defines the graphic components of a binary search tree that is displayed on the monitor.
 * Used in subclasses of {@code VisualizedDataStructure}.
 */
public class VisualBinarySearchTree extends Visual {

    public static final int CACHED_NODE = -1;

    private ArrayList<VisualBinaryTreeNode> arrayNode = new ArrayList<>();
    private VisualBinaryTreeNode cachedNode = null;

    public VisualBinarySearchTree(String name) {
        // super
        super(name);
        // initialize
        this.getStyleClass().add("visual-binary-search-tree");
    }

    private VisualBinaryTreeNode createNode(int index, int value) {
        VisualBinaryTreeNode node = new VisualBinaryTreeNode(value);
        node.setLayoutX(32 + index * (32 + node.getWidth()));
        node.setLayoutY(96);
        this.getChildren().add(node);
        return node;
    }
    private void removeNode(int index) {
        VisualBinaryTreeNode node = arrayNode.remove(index);
        Director.getInstance().createAnimation(1.0, node.scaleXProperty(), 0);
        Director.getInstance().updateAnimation(1.0, node.scaleYProperty(), 0);
        Director.getInstance().getLastTimeline().setOnFinished((event) -> {
            this.getChildren().remove(node);
        });
    }

    public void cacheNode(int index, int value) {
        if (cachedNode == null) {
            cachedNode = createNode(index, value);
        }
    }

    public void insertNode(int value) {
        ;
    }

}
