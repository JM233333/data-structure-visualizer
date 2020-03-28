package jm233333.visual;

import java.util.HashMap;
import java.util.Stack;

import javafx.scene.Node;
import jm233333.Director;

/**
 * The {@code VisualBinarySearchTree} class defines the graphic components of a binary search tree that is displayed on the monitor.
 * Used in subclasses of {@code VDS}.
 */
public class VisualBinarySearchTree extends Visual {

    public static final int CACHED_NODE = -1;

    public static final boolean LEFT_CHILD = false;
    public static final boolean RIGHT_CHILD = true;

    public static final double PADDING = 64;

    private static final int MAX_HEIGHT = 4;

    private static double[][] localization = new double[MAX_HEIGHT][2];

    private VisualBinaryTreeNode rootNode = null;
    private HashMap<String, VisualBinaryTreeNode> mapNode = new HashMap<>();
    private int height = 0;

    private Stack<Boolean> cachedPath = new Stack<>();
    private VisualBinaryTreeNode cachedNode = null;

    public VisualBinarySearchTree(String name) {
        // super
        super(name);
        // initialize
        this.getStyleClass().add("visual-binary-search-tree");
        // initialize localization
        initializeLocalization();
    }

    private void initializeLocalization() {
        // get box size
        final double boxSize = VisualBinaryTreeNode.BOX_SIZE;
        // initialize localization
        double dx = (boxSize + (boxSize / 5)) * MAX_HEIGHT;
        double dy = (boxSize + (boxSize / 5));
        for (int depth = 0; depth < MAX_HEIGHT; depth ++) {
            localization[depth][0] = dx;
            localization[depth][1] = dy;
            dx /= 2;
            dy += (boxSize / 5);
        }
    }

    private void relocate() {
        double minX = 0;
        for (Node child : this.getChildren()) {
            minX = Math.min(minX, child.getLayoutX());
        }
        this.setLayoutX(PADDING - minX);
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
            if (depth == MAX_HEIGHT) {
                System.err.println("error visual BST : DEPTH > MAX_HEIGHT");
                y += 80;
                break;
            }
            int direction = (path.pop() == LEFT_CHILD ? -1 : 1);
            x += localization[depth][0] * direction;
            y += localization[depth][1];
            height = Math.max(height, depth);
        }
        // create node
        VisualBinaryTreeNode node = new VisualBinaryTreeNode(value);
        node.setLayoutX(x);
        node.setLayoutY(y);
        this.getChildren().add(node);
        // relocate
        relocate();
        // return
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
            } else {
                insertNode(p.getLeft(), value);
            }
        } else {
            getCachedPath().push(RIGHT_CHILD);
            if (p.getRight() == null) {
                VisualBinaryTreeNode newNode = createNode(value);
                p.setRight(newNode);
            } else {
                insertNode(p.getRight(), value);
            }
        }
    }

}
