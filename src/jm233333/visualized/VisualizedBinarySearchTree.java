package jm233333.visualized;

import jm233333.ui.CodeTracker;

/**
 * The {@code VisualizedBinarySearchTree} class defines the data structure {@code BinarySearchTree} for visualizing.
 * Extended from abstract class {@code VDS}.
 */
public class VisualizedBinarySearchTree extends VDS {

    static class Node {
        int value;
        Node left, right;
        int depth;
        Node(int value) {
            this.value = value;
            left = null;
            right = null;
            depth = 0;
        }
    }

    private static final int MAX_DEPTH = 4;

    private Node root = null;

    public VisualizedBinarySearchTree() {
        super();
    }

    @Override
    public void createVisual() {
        createVisualBST(getName());
    }

    public Node find(int value) {
        trackCodeMethodBeginning("findNode");
        getVisualBST(getName()).markRoot();
        return findNode(root, value);
    }
    private Node findNode(Node p, int value) {
        // check if failed
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return null;
        }
        // check if succeeded
        trackCodeEntrance(getCodeCurrentMethod() + "_ifSucc");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return p;
        }
        // discussion
        trackCodeEntrance(getCodeCurrentMethod() + "_ifLR");
        if (value < p.value) {
            trackCodeEntrance(getCodeCurrentMethod() + "_recL");
            trackCodeMethodBeginning(getCodeCurrentMethod());
            return findNode(p.left, value);
        } else {
            trackCodeEntrance(getCodeCurrentMethod() + "_recR");
            trackCodeMethodBeginning(getCodeCurrentMethod());
            return findNode(p.right, value);
        }
    }

    public void insert(int value) {
        getVisualBST(getName()).markRoot();
        trackCodeMethodBeginning("insertNode");
        if (root == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            root = new Node(value);
            root.depth = 0;
            getVisualBST(getName()).insertNode(value);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        insertNode(root, value);
    }
    private void insertNode(Node p, int value) {
        // assert
        assert (p != null);
        if (p.depth == MAX_DEPTH) {
            System.out.printf("Sorry, depth cannot be higher than %d.\n", MAX_DEPTH);
            getVisualBST(getName()).markClear();
            return;
        }
        // deal with duplication
        trackCodeEntrance(getCodeCurrentMethod() + "_ifEql");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            getVisualBST(getName()).markClear();
            return;
        }
        // discussion
        trackCodeEntrance(getCodeCurrentMethod() + "_ifLR");
        if (value < p.value) {
            trackCodeEntrance(getCodeCurrentMethod() + "_recL");
            getVisualBST(getName()).markToLeft();
            trackCodeMethodBeginning(getCodeCurrentMethod());
            if (p.left == null) {
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.left = new Node(value);
                p.left.depth = p.depth + 1;
                getVisualBST(getName()).insertNode(value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).markClear();
            } else {
                insertNode(p.left, value);
            }
        } else {
            trackCodeEntrance(getCodeCurrentMethod() + "_recR");
            getVisualBST(getName()).markToRight();
            trackCodeMethodBeginning(getCodeCurrentMethod());
            if (p.right == null) {
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.right = new Node(value);
                p.right.depth = p.depth + 1;
                getVisualBST(getName()).insertNode(value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).markClear();
            } else {
                insertNode(p.right, value);
            }
        }
    }
}
