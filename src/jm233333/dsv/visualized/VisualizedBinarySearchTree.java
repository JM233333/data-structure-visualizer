package jm233333.dsv.visualized;

import javafx.scene.paint.Color;
import jm233333.dsv.ui.CodeTracker;
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
            outputMessageReturn("false");
            return null;
        }
        // check if succeeded
        trackCodeEntrance(getCodeCurrentMethod() + "_ifSucc");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageReturn("true (Node)(value = " + p.value + ")");
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

    public void insert(int value) {
        if (root != null) {
            getVisualBST(getName()).markNodeOfValue(root.value);
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
                p.left.parent = p;
                getVisualBST(getName()).insertNode(value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).markClear();
            } else {
                getVisualBST(getName()).markToLeft();
                trackCodeMethodBeginning(getCodeCurrentMethod());
                insertNode(p.left, value, curDepth + 1);
            }
        } else {
            trackCodeEntrance(getCodeCurrentMethod() + "_recR");
            if (p.right == null) {
                trackCodeMethodBeginning(getCodeCurrentMethod());
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.right = new Node(value);
                p.right.parent = p;
                getVisualBST(getName()).insertNode(value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).markClear();
            } else {
                getVisualBST(getName()).markToRight();
                trackCodeMethodBeginning(getCodeCurrentMethod());
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
            getVisualBST(getName()).markNodeOfValue(root.value);
        }
        if (root != null && value == root.value) {
            trackCodeMethodBeginning("eraseNode");
            trackCodeEntrance(getCodeCurrentMethod() + "_ifEql");
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            if (root.left != null && root.right != null) {
                trackCodeEntrance(getCodeCurrentMethod() + "_findMax");
                Node np = __findMaxOf(root.left);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                int t = np.value;
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                eraseNode(root.left, np.value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).markClear();
                getVisualBST(getName()).modifyNode(root.value, t);
                root.value = t;
            } else {
                trackCodeEntrance(getCodeCurrentMethod() + "_preDel");
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                Node p = (root.left != null ? root.left : root.right);
                trackCodeEntrance(getCodeCurrentMethod() + (root.left != null ? "_linkL" : "_linkR"));
                getVisualBST(getName()).eraseNodePrep(root.value);
                trackCodeEntrance(getCodeCurrentMethod() + "_delete");
                getVisualBST(getName()).eraseCachedNode();
                root = p;
            }
            trackCodeEntrance(getCodeCurrentMethod() + "_return");
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
        trackCodeEntrance(getCodeCurrentMethod() + "_ifEql");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            if (p.left != null && p.right != null) {
                trackCodeEntrance(getCodeCurrentMethod() + "_findMax");
                Node np = __findMaxOf(p.left);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                int t = np.value;
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                eraseNode(p.left, np.value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).markClear();
                getVisualBST(getName()).modifyNode(p.value, t);
                p.value = t;
            } else {
                trackCodeEntrance(getCodeCurrentMethod() + "_preDel");
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                Node f = p.parent;
                if (p.left != null) {
                    trackCodeEntrance(getCodeCurrentMethod() + "_linkL");
                    if (p.value < f.value) {
                        f.left = p.left;
                        f.left.parent = f;
                    } else {
                        f.right = p.left;
                        f.right.parent = f;
                    }
                } else {
                    trackCodeEntrance(getCodeCurrentMethod() + "_linkR");
                    if (p.value < f.value) {
                        f.left = p.right;
                        if (p.right != null) f.left.parent = f;
                    } else {
                        f.right = p.right;
                        if (p.right != null) f.right.parent = f;
                    }
                }
                getVisualBST(getName()).eraseNodePrep(p.value);
                trackCodeEntrance(getCodeCurrentMethod() + "_delete");
                getVisualBST(getName()).eraseCachedNode();
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
            outputMessageReturn("false (empty BST)");
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
        outputMessageReturn("true (Node)(value = " + p.value + ")");
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

    public void traversePreOrder() {
        outputMessage(" | RES : ", 16, Color.BLACK, false);
        trackCodeMethodBeginning("dfsPreOrder");
        dfsPreOrder(root);
        trackCodeSetCurrentMethod("traversePreOrder");
        trackCodeEntrance(getCodeCurrentMethod() + "_call");
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }
    private void dfsPreOrder(Node p) {
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        trackCodeEntrance(getCodeCurrentMethod() + "_visit");
        visitNode(p);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        trackCodeMethodBeginning(getCodeCurrentMethod());
        dfsPreOrder(p.left);
        trackCodeEntrance(getCodeCurrentMethod() + "_recL");
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        trackCodeMethodBeginning(getCodeCurrentMethod());
        dfsPreOrder(p.right);
        trackCodeEntrance(getCodeCurrentMethod() + "_recR");
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        leaveNode(p);
    }

    private void visitNode(Node p) {
        outputMessage(String.valueOf(p.value), 16, Color.BLACK, false);
    }
    private void leaveNode(Node p) {

    }
}