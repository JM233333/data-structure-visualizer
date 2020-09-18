package jm233333.dsv.visual;

import javafx.scene.Node;
import jm233333.dsv.Director;
import jm233333.dsv.util.Pair;

import java.util.HashMap;

/**
 * The {@code VisualBinarySearchTree} class defines the graphic components of a binary search tree that is displayed on the monitor.
 * Used in subclasses of {@code VDS}.
 */
public class VisualBinarySearchTree extends Visual {

    public static final double PADDING = 64;

    private static final int MAX_HEIGHT = 4;

    private static final double[][] localization = new double[MAX_HEIGHT + 1][2];
    private static double delY;

    private VisualBinaryTreeNode rootNode = null;

    private final HashMap<Integer, VisualBinaryTreeNode> mapNode = new HashMap<>();
    private VisualBinaryTreeNode cachedNode = null;
    private VisualBinaryTreeNode tracedNode = null;

    static {
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
        localization[MAX_HEIGHT][0] = 0;
        localization[MAX_HEIGHT][1] = 0;
        //
        delY = PADDING / 2;
        for (int depth = 0; depth < MAX_HEIGHT; depth ++) {
            delY += localization[depth][1];
        }
    }

    public VisualBinarySearchTree(String name) {
        // super
        super(name);
        // initialize
        this.getStyleClass().add("visual-binary-search-tree");
    }

    private VisualBinaryTreeNode createNode(int value, double x, double y) {
        return createNode(value, x, y, rootNode);
    }
    private VisualBinaryTreeNode createNode(int value, double x, double y, final VisualBinaryTreeNode parentNode) {
        // create node
        VisualBinaryTreeNode node = new VisualBinaryTreeNode(value);
        node.setParentNode(parentNode);
        node.setLayoutX(x);
        node.setLayoutY(y);
        this.getChildren().add(node);
        mapNode.put(node.getValue(), node);
        // relocate
        relocate();
        // return
        return node;
    }
    private void removeNode(VisualBinaryTreeNode node) {
        Director.getInstance().createAnimation(1.0, node.scaleXProperty(), 0);
        Director.getInstance().updateAnimation(1.0, node.scaleYProperty(), 0);
        Director.getInstance().getLastTimeline().setOnFinished((event) -> {
            this.getChildren().remove(node);
            mapNode.remove(node.getValue());
            relocate();
        });
    }

    private VisualBinaryTreeNode getNode(int value) {
        return getNode(rootNode, value);
    }
    private VisualBinaryTreeNode getNode(VisualBinaryTreeNode p, int value) {
        if (p == null) {
            return null;
        }
        if (value == p.getValue()) {
            return p;
        }
        if (value < p.getValue()) {
            return getNode(p.getLeft(), value);
        } else {
            return getNode(p.getRight(), value);
        }
    }
    private VisualBinaryTreeNode getParent(int value) {
        return getParent(rootNode, value);
    }
    private VisualBinaryTreeNode getParent(VisualBinaryTreeNode p, int value) {
        if (p == null || value == rootNode.getValue()) {
            return null;
        }
        if (value < p.getValue()) {
            if (p.getLeft() == null || value == p.getLeft().getValue()) {
                return p;
            }
            return getParent(p.getLeft(), value);
        } else {
            if (p.getRight() == null || value == p.getRight().getValue()) {
                return p;
            }
            return getParent(p.getRight(), value);
        }
    }

    private void relocate() {
        double minX = 0;
        for (Node child : this.getChildren()) {
            minX = Math.min(minX, child.getLayoutX());
        }
        this.setLayoutX(PADDING - minX);
    }
    private void reconstruct() {
        Director.getInstance().addEmptyTimeline();
        reconstruct(rootNode, 0.0, 0.0);
    }
    private void reconstruct(VisualBinaryTreeNode p, double x, double y) {
        // end recursion
        if (p == null) {
            return;
        }
        // reset depth
        if (p == rootNode) {
            p.setParentNode(null);
        } else {
            p.setParentNode(p.getParentNode());
        }
        // reset position
        Director.getInstance().updateAnimation(1.0, p.layoutXProperty(), x);
        Director.getInstance().updateAnimation(1.0, p.layoutYProperty(), y);
        // recursion
        reconstruct(p.getLeft(), x - localization[p.getDepth()][0], y + localization[p.getDepth()][1]);
        reconstruct(p.getRight(), x + localization[p.getDepth()][0], y + localization[p.getDepth()][1]);
    }

    public void traceRoot() {
        boolean sync = (tracedNode != null);
        if (tracedNode != null) {
            tracedNode.setColorScheme(ColorScheme.DEFAULT);
        }
        tracedNode = rootNode;
        if (tracedNode != null) {
            tracedNode.setColorScheme(ColorScheme.HIGHLIGHT, sync);
        }
    }
    public void traceToLeft() {
        if (tracedNode == null) {
            return;
        }
        if (tracedNode.getLeft() != null) {
            tracedNode.getPointerToLeft().setColorScheme(ColorScheme.HIGHLIGHT);
            tracedNode.getLeft().setColorScheme(ColorScheme.HIGHLIGHT, true);
            tracedNode.setColorScheme(ColorScheme.DEFAULT);
            tracedNode.getPointerToLeft().setColorScheme(ColorScheme.DEFAULT, true);
        } else {
            tracedNode.setColorScheme(ColorScheme.DEFAULT);
        }
        tracedNode = tracedNode.getLeft();
    }
    public void traceToRight() {
        if (tracedNode == null) {
            return;
        }
        if (tracedNode.getRight() != null) {
            tracedNode.getPointerToRight().setColorScheme(ColorScheme.HIGHLIGHT);
            tracedNode.getRight().setColorScheme(ColorScheme.HIGHLIGHT, true);
            tracedNode.setColorScheme(ColorScheme.DEFAULT);
            tracedNode.getPointerToRight().setColorScheme(ColorScheme.DEFAULT, true);
        } else {
            tracedNode.setColorScheme(ColorScheme.DEFAULT);
        }
        tracedNode = tracedNode.getRight();
    }
    public void traceClear() {
        if (tracedNode != null) {
            tracedNode.setColorScheme(ColorScheme.DEFAULT);
            tracedNode = null;
        }
    }

    public void markNodeOfValue(int value, ColorScheme colorScheme) {
        markNodeOfValue(value, colorScheme, false);
    }
    public void markNodeOfValue(int value, ColorScheme colorScheme, boolean sync) {
        VisualBinaryTreeNode node = mapNode.get(value);
        if (node != null) {
            node.setColorScheme(colorScheme, sync);
        }
    }
    public void markClear() {
        for (VisualBinaryTreeNode node : mapNode.values()) {
            node.setColorScheme(ColorScheme.DEFAULT, true);
        }
    }

    public void insertNode(int value) {
        if (rootNode == null) {
            rootNode = createNode(value, 0, 0);
            return;
        }
        Pair<Double, Double> position = new Pair<>(0.0, 0.0);
        insertNode(rootNode, value, position);
    }
    private void insertNode(VisualBinaryTreeNode p, int value, Pair<Double, Double> position) {
        // assert
        assert (p != null);
        // deal with duplication
        if (value == p.getValue()) {
            return;
        }
        // discussion
        if (value < p.getValue()) {
            position.first -= localization[p.getDepth()][0];
            position.second += localization[p.getDepth()][1];
            if (p.getLeft() == null) {
                VisualBinaryTreeNode newNode = createNode(value, position.first, position.second, p);
                p.setLeft(newNode);
            } else {
                insertNode(p.getLeft(), value, position);
            }
        } else {
            position.first += localization[p.getDepth()][0];
            position.second += localization[p.getDepth()][1];
            if (p.getRight() == null) {
                VisualBinaryTreeNode newNode = createNode(value, position.first, position.second, p);
                p.setRight(newNode);
            } else {
                insertNode(p.getRight(), value, position);
            }
        }
    }

    public void eraseNodePrep(int value) {
        if (value == rootNode.getValue()) {
            VisualBinaryTreeNode p = rootNode;
            if (p.getLeft() != null && p.getRight() != null) {
                return;
            }
            VisualBinaryTreeNode c = (p.getLeft() != null ? p.getLeft() : p.getRight());
            rootNode = c;
            cachedNode = p;
            Director.getInstance().createAnimation(1.0, p.layoutYProperty(), delY);
            return;
        }
        VisualBinaryTreeNode f = getParent(value);
        if (f == null) {
            return;
        }
        VisualBinaryTreeNode p = (value < f.getValue() ? f.getLeft() : f.getRight());
        if (p.getLeft() != null && p.getRight() != null) {
            return;
        }
        VisualBinaryTreeNode c = (p.getLeft() != null ? p.getLeft() : p.getRight());
        if (value < f.getValue()) {
            f.setLeft(c);
        } else {
            f.setRight(c);
        }
        cachedNode = p;
        Director.getInstance().createAnimation(1.0, p.layoutYProperty(), delY);
    }
    public void eraseCachedNode() {
        removeNode(cachedNode);
        cachedNode = null;
        reconstruct();
        Director.getInstance().createDelayInvocation(500, (event) -> {
            relocate();
        });//Director.getInstance().getLastTimeline().setOnFinished(
    }

    public void modifyNode(int value, int nValue) {
        VisualBinaryTreeNode p = getNode(value);
        if (p != null) {
            mapNode.remove(p.getValue());
            p.setValue(nValue);
            mapNode.put(p.getValue(), p);
        }
    }

    private VisualBinaryTreeNode findMaxOf(VisualBinaryTreeNode p) {
        if (p == null) {
            return null;
        }
        while (p.getRight() != null) {
            p = p.getRight();
        }
        return p;
    }

    private void cc(VisualBinaryTreeNode p) {
        if (p == null) return;
        System.out.printf("p=%d pL=%d pR=%d pF=%d pDepth=%d\n",
                p.getValue(), p.getLeft()==null?-1:p.getLeft().getValue(),p.getRight()==null?-1:p.getRight().getValue(),
                p.getParentNode()==null?-1:p.getParentNode().getValue(), p.getDepth());
        cc(p.getLeft());
        cc(p.getRight());
    }
}
