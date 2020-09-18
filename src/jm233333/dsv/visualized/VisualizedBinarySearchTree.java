package jm233333.dsv.visualized;

import javafx.scene.paint.Color;
import jm233333.dsv.ui.CodeTracker;
import jm233333.dsv.visual.ColorScheme;
import jm233333.dsv.visual.VisualBinaryTreeNode;

import java.util.HashMap;

/**
 * Class {@code VisualizedBinarySearchTree} defines the data structure {@code BinarySearchTree} for visualizing.
 * Extended from abstract class {@link VDS}.
 */
public class VisualizedBinarySearchTree extends VDS {

    static class Node {
        int value;
        Node left, right;
        Node parent;
        Node(int value) {
            this.value = value;
            left = null;
            right = null;
            parent = null;
        }
    }

    private static final int MAX_DEPTH = 4;

    private Node root = null;
    private final HashMap<Node, VisualBinaryTreeNode> mapNodes = new HashMap<>();

    public VisualizedBinarySearchTree() {
        super();
    }

    @Override
    public void createVisual() {
        createVisualBST(getName());
    }

    public Node find(int value) {
        if (root != null) {
            getVisualBST(getName()).traceRoot();
        }
        trackCodeMethodBeginning("findNode");
        return findNode(root, value);
    }
    private Node findNode(Node p, int value) {
        // check if failed
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageReturn("false");
            return null;
        }
        // check if succeeded
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_ifSucc");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageReturn("true (Node)(value = " + p.value + ")");
            getVisualBST(getName()).traceClear();
            return p;
        }
        // discussion
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_ifLR");
        if (value < p.value) {
            trackCodeEntrance(trackCodeGetCurrentMethod() + "_recL");
            getVisualBST(getName()).traceToLeft();
            trackCodeMethodBeginning(trackCodeGetCurrentMethod());
            return findNode(p.left, value);
        } else {
            trackCodeEntrance(trackCodeGetCurrentMethod() + "_recR");
            getVisualBST(getName()).traceToRight();
            trackCodeMethodBeginning(trackCodeGetCurrentMethod());
            return findNode(p.right, value);
        }
    }

    public void insert(int value) {
        if (root != null) {
            getVisualBST(getName()).traceRoot();
        }
        trackCodeMethodBeginning("insertNode");
        if (root == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            root = new Node(value);
            root.parent = null;
            getVisualBST(getName()).insertNode(value);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        insertNode(root, value, 0);
    }
    private void insertNode(Node p, int value, int curDepth) {
        // assert
        assert (p != null);
        if (curDepth == MAX_DEPTH) {
            outputMessageError("Depth limit exceed.");
            getVisualBST(getName()).traceClear();
            return;
        }
        // deal with duplication
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_ifEql");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            getVisualBST(getName()).traceClear();
            return;
        }
        // discussion
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_ifLR");
        if (value < p.value) {
            trackCodeEntrance(trackCodeGetCurrentMethod() + "_recL");
            if (p.left == null) {
                trackCodeMethodBeginning(trackCodeGetCurrentMethod());
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.left = new Node(value);
                p.left.parent = p;
                getVisualBST(getName()).insertNode(value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).traceClear();
            } else {
                getVisualBST(getName()).traceToLeft();
                trackCodeMethodBeginning(trackCodeGetCurrentMethod());
                insertNode(p.left, value, curDepth + 1);
            }
        } else {
            trackCodeEntrance(trackCodeGetCurrentMethod() + "_recR");
            if (p.right == null) {
                trackCodeMethodBeginning(trackCodeGetCurrentMethod());
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.right = new Node(value);
                p.right.parent = p;
                getVisualBST(getName()).insertNode(value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).traceClear();
            } else {
                getVisualBST(getName()).traceToRight();
                trackCodeMethodBeginning(trackCodeGetCurrentMethod());
                insertNode(p.right, value, curDepth + 1);
            }
        }
    }

    private void cc(Node p) {
        if (p == null) return;
        System.out.printf("cc1 p=%d pL=%d pR=%d pF=%d\n",
                p.value, p.left==null?-1:p.left.value,p.right==null?-1:p.right.value,
                p.parent==null?-1:p.parent.value);
        cc(p.left);
        cc(p.right);
    }
    public void erase(int value) {
        if (root != null) {
            getVisualBST(getName()).traceRoot();
        }
        if (root != null && value == root.value) {
            trackCodeMethodBeginning("eraseNode");
            trackCodeEntrance(trackCodeGetCurrentMethod() + "_ifEql");
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            if (root.left != null && root.right != null) {
                trackCodeEntrance(trackCodeGetCurrentMethod() + "_findMax");
                Node np = __findMaxOf(root.left);
                getVisualBST(getName()).markNodeOfValue(np.value, ColorScheme.MARKED);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                int t = np.value;
                getVisualBST(getName()).markNodeOfValue(np.value, ColorScheme.DEFAULT);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).traceToLeft();
                eraseNode(root.left, np.value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).traceClear();
                getVisualBST(getName()).markClear();
                getVisualBST(getName()).modifyNode(root.value, t);
                root.value = t;
                trackCodeEntrance(trackCodeGetCurrentMethod() + "_return");
                trackCodeEntrance(CodeTracker.NEXT_LINE);
            } else {
                trackCodeEntrance(trackCodeGetCurrentMethod() + "_preDel");
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                Node p = (root.left != null ? root.left : root.right);
                trackCodeEntrance(trackCodeGetCurrentMethod() + (root.left != null ? "_linkL" : "_linkR"));
                getVisualBST(getName()).eraseNodePrep(root.value);
                trackCodeEntrance(trackCodeGetCurrentMethod() + "_delete");
                getVisualBST(getName()).eraseCachedNode();
                root = p;
            }
            return;
        }
        trackCodeMethodBeginning("eraseNode");
        eraseNode(root, value);
    }
    private void eraseNode(Node p, int value) {
        // check if failed
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageReturn("The value is nonexistent.");
            return;
        }
        // check if reached
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_ifEql");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            if (p.left != null && p.right != null) {
                trackCodeEntrance(trackCodeGetCurrentMethod() + "_findMax");
                Node np = __findMaxOf(p.left);
                getVisualBST(getName()).markNodeOfValue(np.value, ColorScheme.MARKED);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                int t = np.value;
                getVisualBST(getName()).markNodeOfValue(np.value, ColorScheme.DEFAULT);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).traceToLeft();
                eraseNode(p.left, np.value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).traceClear();
                getVisualBST(getName()).markClear();
                getVisualBST(getName()).modifyNode(p.value, t);
                p.value = t;
                trackCodeEntrance(trackCodeGetCurrentMethod() + "_return");
                trackCodeEntrance(CodeTracker.NEXT_LINE);
            } else {
                trackCodeEntrance(trackCodeGetCurrentMethod() + "_preDel");
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                Node f = p.parent;
                if (p.left != null) {
                    trackCodeEntrance(trackCodeGetCurrentMethod() + "_linkL");
                    if (p.value < f.value) {
                        f.left = p.left;
                        f.left.parent = f;
                    } else {
                        f.right = p.left;
                        f.right.parent = f;
                    }
                } else {
                    trackCodeEntrance(trackCodeGetCurrentMethod() + "_linkR");
                    if (p.value < f.value) {
                        f.left = p.right;
                        if (p.right != null) f.left.parent = f;
                    } else {
                        f.right = p.right;
                        if (p.right != null) f.right.parent = f;
                    }
                }
                getVisualBST(getName()).eraseNodePrep(p.value);
                trackCodeEntrance(trackCodeGetCurrentMethod() + "_delete");
                getVisualBST(getName()).eraseCachedNode();
                trackCodeEntrance(trackCodeGetCurrentMethod() + "_subp");
            }
            return;
        }
        // discussion
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_ifLR");
        if (value < p.value) {
            trackCodeEntrance(trackCodeGetCurrentMethod() + "_recL");
            getVisualBST(getName()).traceToLeft();
            trackCodeMethodBeginning(trackCodeGetCurrentMethod());
            eraseNode(p.left, value);
        } else {
            trackCodeEntrance(trackCodeGetCurrentMethod() + "_recR");
            getVisualBST(getName()).traceToRight();
            trackCodeMethodBeginning(trackCodeGetCurrentMethod());
            eraseNode(p.right, value);
        }
    }

    public Node findMax() {
        if (root != null) {
            getVisualBST(getName()).traceRoot();
        }
        trackCodeMethodBeginning("findMaxOf");
        return findMaxOf(root);
    }
    private Node findMaxOf(Node p) {
        // check if null
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageReturn("false (empty BST)");
            return null;
        }
        // iterate
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_loop");
        while (p.right != null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            getVisualBST(getName()).traceToRight();
            p = p.right;
            trackCodeEntrance(trackCodeGetCurrentMethod() + "_loop");
        }
        // return
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_return");
        outputMessageReturn("true (Node)(value = " + p.value + ")");
        return p;
    }
    private Node __findMaxOf(Node p) {
        if (p == null) {
            return null;
        }
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }

    public void traversePreOrder() {
        outputMessage(" | RES : ", 16, Color.BLACK, false);
        if (root != null) getVisualBST(getName()).markNodeOfValue(root.value, ColorScheme.MARKED);
        trackCodeMethodBeginning("dfsPreOrder");
        dfsPreOrder(root);
        trackCodeSetCurrentMethod("traversePreOrder");
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_call");
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }
    private void dfsPreOrder(Node p) {
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_visit");
        visitNode(p);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        if (p.left != null) getVisualBST(getName()).markNodeOfValue(p.left.value, ColorScheme.MARKED);
        trackCodeMethodBeginning(trackCodeGetCurrentMethod());
        dfsPreOrder(p.left);
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_recL");
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        if (p.right != null) getVisualBST(getName()).markNodeOfValue(p.right.value, ColorScheme.MARKED);
        trackCodeMethodBeginning(trackCodeGetCurrentMethod());
        dfsPreOrder(p.right);
        trackCodeEntrance(trackCodeGetCurrentMethod() + "_recR");
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        leaveNode(p);
    }

    private void visitNode(Node p) {
        outputMessage(p.value + ", ", 16, Color.BLACK, false);
        getVisualBST(getName()).markNodeOfValue(p.value, ColorScheme.HIGHLIGHT);
        if (p.parent != null) {
            getVisualBST(getName()).markNodeOfValue(p.parent.value, ColorScheme.MARKED, true);
        }
    }
    private void leaveNode(Node p) {
        getVisualBST(getName()).markNodeOfValue(p.value, ColorScheme.MARKED);
        if (p.parent != null) {
            getVisualBST(getName()).markNodeOfValue(p.parent.value, ColorScheme.HIGHLIGHT, true);
        }
    }
}
