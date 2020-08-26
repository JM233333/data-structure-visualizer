package jm233333.visualized;

import jm233333.ui.CodeTracker;
import jm233333.visual.VisualBinaryTreeNode;

import java.util.HashMap;

/**
 * The {@code VisualizedBinarySearchTree} class defines the data structure {@code BinarySearchTree} for visualizing.
 * Extended from abstract class {@code VDS}.
 */
public class VisualizedBinarySearchTree extends VDS {

    static class Node {
        int value;
        Node left, right;
        int depth;
        Node parent;
        Node(int value) {
            this.value = value;
            left = null;
            right = null;
            depth = 0;
            parent = null;
        }
    }

    private static final int MAX_DEPTH = 4;

    private Node root = null;
    private HashMap<Node, VisualBinaryTreeNode> mapNodes = new HashMap<>();

    public VisualizedBinarySearchTree() {
        super();
    }

    @Override
    public void createVisual() {
        createVisualBST(getName());
    }

    public Node find(int value) {
        if (root != null) {
            getVisualBST(getName()).markNodeOfValue(root.value);
        }
        trackCodeMethodBeginning("findNode");
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
            getVisualBST(getName()).markClear();
            return p;
        }
        // discussion
        trackCodeEntrance(getCodeCurrentMethod() + "_ifLR");
        if (value < p.value) {
            trackCodeEntrance(getCodeCurrentMethod() + "_recL");
            getVisualBST(getName()).markToLeft();
            trackCodeMethodBeginning(getCodeCurrentMethod());
            return findNode(p.left, value);
        } else {
            trackCodeEntrance(getCodeCurrentMethod() + "_recR");
            getVisualBST(getName()).markToRight();
            trackCodeMethodBeginning(getCodeCurrentMethod());
            return findNode(p.right, value);
        }
    }
    private Node __findParent(Node p, int value) {
        if (p == null || value == root.value) {
            return null;
        }
        if (value == p.left.value || value == p.right.value) {
            return p;
        }
        if (value < p.value) {
            return __findParent(p.left, value);
        } else {
            return __findParent(p.right, value);
        }
    }

    public void insert(int value) {
        if (root != null) {
            getVisualBST(getName()).markNodeOfValue(root.value);
        }
        trackCodeMethodBeginning("insertNode");
        if (root == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            root = new Node(value);
            root.depth = 0;
            root.parent = null;
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
            if (p.left == null) {
                trackCodeMethodBeginning(getCodeCurrentMethod());
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.left = new Node(value);
                p.left.depth = p.depth + 1;
                p.left.parent = p;
                getVisualBST(getName()).insertNode(value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).markClear();
            } else {
                getVisualBST(getName()).markToLeft();
                trackCodeMethodBeginning(getCodeCurrentMethod());
                insertNode(p.left, value);
            }
        } else {
            trackCodeEntrance(getCodeCurrentMethod() + "_recR");
            if (p.right == null) {
                trackCodeMethodBeginning(getCodeCurrentMethod());
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.right = new Node(value);
                p.right.depth = p.depth + 1;
                p.right.parent = p;
                getVisualBST(getName()).insertNode(value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).markClear();
            } else {
                getVisualBST(getName()).markToRight();
                trackCodeMethodBeginning(getCodeCurrentMethod());
                insertNode(p.right, value);
            }
        }
    }

    public void erase(int value) {
        /*if (value == root.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }*/
        if (root != null) {
            getVisualBST(getName()).markNodeOfValue(root.value);
        }
        trackCodeMethodBeginning("eraseNode");
        eraseNode(root, value);
    }
    private void eraseNode(Node p, int value) {
        // check if failed
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        // check if reached
        trackCodeEntrance(getCodeCurrentMethod() + "_ifEql");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            if (p.left != null && p.right != null) {
                trackCodeEntrance(getCodeCurrentMethod() + "_findMax");
//                getVisualBST(getName()).markNodeOfValue(p.left.value);
                Node np = __findMaxOf(p.left);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.value = np.value;
                getVisualBST(getName()).eraseNodeSeparately(p.value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                eraseNode(p.left, np.value);
            } else {
                trackCodeEntrance(getCodeCurrentMethod() + "_preDel");
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                if (p.left != null) {
                    trackCodeEntrance(getCodeCurrentMethod() + "_linkL");
                    if (p.value < p.parent.value) {
                        p.parent.left = p.left;
                    } else {
                        p.parent.right = p.left;
                    }
                } else {
                    trackCodeEntrance(getCodeCurrentMethod() + "_linkR");
                    if (p.value < p.parent.value) {
                        p.parent.left = p.right;
                    } else {
                        p.parent.right = p.right;
                    }
                }
                trackCodeEntrance(getCodeCurrentMethod() + "_delete");
                getVisualBST(getName()).eraseNodeSeparately(p.value);
            }
            trackCodeEntrance(getCodeCurrentMethod() + "_return");
            return;
        }
        // discussion
        trackCodeEntrance(getCodeCurrentMethod() + "_ifLR");
        if (value < p.value) {
            trackCodeEntrance(getCodeCurrentMethod() + "_recL");
            getVisualBST(getName()).markToLeft();
            trackCodeMethodBeginning(getCodeCurrentMethod());
            eraseNode(p.left, value);
        } else {
            trackCodeEntrance(getCodeCurrentMethod() + "_recR");
            getVisualBST(getName()).markToRight();
            trackCodeMethodBeginning(getCodeCurrentMethod());
            eraseNode(p.right, value);
        }
    }

    public Node findMax() {
        if (root != null) {
            getVisualBST(getName()).markNodeOfValue(root.value);
        }
        trackCodeMethodBeginning("findMaxOf");
        return findMaxOf(root);
    }
    private Node findMaxOf(Node p) {
        // check if null
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return null;
        }
        // iterate
        trackCodeEntrance(getCodeCurrentMethod() + "_loop");
        while (p.right != null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            getVisualBST(getName()).markToRight();
            p = p.right;
            trackCodeEntrance(getCodeCurrentMethod() + "_loop");
        }
        // return
        trackCodeEntrance(getCodeCurrentMethod() + "_return");
        return p;
    }
    private Node __findMaxOf(Node p) {
        if (p == null) {
            return null;
        }
        while (p.right != null) {
            getVisualBST(getName()).markToRight();
            p = p.right;
        }
        return p;
    }
}
