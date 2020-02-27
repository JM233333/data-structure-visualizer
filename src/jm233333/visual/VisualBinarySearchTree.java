package jm233333.visual;

import java.util.HashMap;
import java.util.Stack;

import javafx.util.Pair;
import jm233333.Director;

/**
 * The {@code VisualBinarySearchTree} class defines the graphic components of a binary search tree that is displayed on the monitor.
 * Used in subclasses of {@code VisualizedDataStructure}.
 */
public class VisualBinarySearchTree extends Visual {

    public static final int CACHED_NODE = -1;

    public static final boolean LEFT_CHILD = false;
    public static final boolean RIGHT_CHILD = true;

    private static final double[] localization = new double[] {
            256, 54,  0,
            108, 48,  0,
            44,  36,  0,
            16,  32,  0,
            8,   24,  0
    };
    private static final int maxDepth = 5;

    private VisualBinaryTreeNode rootNode = null;
    private HashMap<String, VisualBinaryTreeNode> mapNode = new HashMap<>();
    private Stack<Boolean> cachedPath = new Stack<>();
    private VisualBinaryTreeNode cachedNode = null;

    public VisualBinarySearchTree(String name) {
        // super
        super(name);
        // initialize
        this.getStyleClass().add("visual-binary-search-tree");
        // pre-calculate localization info
        for (int depth = 0; depth < 5; depth ++) {
            double dx = localization[3 * depth];
            double dy = localization[3 * depth + 1];
            localization[3 * depth + 2] = Math.atan(dy / dx);
        }
    }

    private VisualBinaryTreeNode createNode(int value) {
        // calculate position
        double x = 0;
        double y = 0;
        Stack<Boolean> path = new Stack<>();
        while (!cachedPath.empty()) {
            path.push(cachedPath.pop());
        }
        for (int depth = 0; !path.empty(); depth ++) {
            double angle = localization[3 * depth + 2];
            double dx = VisualNode.BOX_SIZE * Math.cos(angle) + localization[3 * depth];
            double dy = VisualNode.BOX_SIZE * Math.sin(angle) + localization[3 * depth + 1];
            int direction = (path.pop() == LEFT_CHILD ? -1 : 1);
            x += dx * direction;
            y += dy;
        }
        // create node
        VisualBinaryTreeNode node = new VisualBinaryTreeNode(value);
        node.setLayoutX(x);
        node.setLayoutY(y);
        this.getChildren().add(node);
        return node;
    }
    private void removeNode(int index) {
//        VisualBinaryTreeNode node = arrayNode.remove(index);
//        Director.getInstance().createAnimation(1.0, node.scaleXProperty(), 0);
//        Director.getInstance().updateAnimation(1.0, node.scaleYProperty(), 0);
//        Director.getInstance().getLastTimeline().setOnFinished((event) -> {
//            this.getChildren().remove(node);
//        });
    }

    public final Stack<Boolean> getCachedPath() {
        return cachedPath;
    }

    public void cacheNode(int value) {
        if (cachedNode == null) {
            cachedNode = createNode(value);
        }
    }

    public void insertNode(int value) {
        if (rootNode == null) {
            rootNode = createNode(value);
            Director.getInstance().playAnimation();
            return;
        }
        insertNode(rootNode, value);
    }
    private void insertNode(VisualBinaryTreeNode p, int value) {
        // assert
        assert (p != null);
        // deal with duplication
        if (value == p.getValue()) {
            return;
        }
        // discussion
        if (value < p.getValue()) {
            getCachedPath().push(LEFT_CHILD);
            if (p.getLeft() == null) {
                VisualBinaryTreeNode newNode = createNode(value);
                p.setLeft(newNode);
                Director.getInstance().playAnimation();
            } else {
                insertNode(p.getLeft(), value);
            }
        } else {
            getCachedPath().push(RIGHT_CHILD);
            if (p.getRight() == null) {
                VisualBinaryTreeNode newNode = createNode(value);
                p.setRight(newNode);
                Director.getInstance().playAnimation();
            } else {
                insertNode(p.getRight(), value);
            }
        }
    }

}
