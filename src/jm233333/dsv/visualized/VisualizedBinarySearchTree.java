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
        super("BST");
    }

    @Override
    public void createVisual() {
        createVisualBST(getName());
    }

    public Node find(int value) {
        if (root != null) {
            getVisualBST(getName()).traceRoot();
        }
        trackMethodCall("findNode");
        Node res = findNode(root, value);
        trackMethodReturn();
        return res;
    }
    private Node findNode(Node p, int value) {
        // check if failed
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageReturn("false");
            return null;
        }
        // check if succeeded
        trackCodeEntrance(getCurrentMethod() + "_ifSucc");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageReturn("true (Node)(value = " + p.value + ")");
            getVisualBST(getName()).traceClear();
            return p;
        }
        // discussion
        trackCodeEntrance(getCurrentMethod() + "_ifLR");
        Node res;
        if (value < p.value) {
            trackCodeEntrance(getCurrentMethod() + "_recL");
            getVisualBST(getName()).traceToLeft();
            trackMethodCall(getCurrentMethod());
            res = findNode(p.left, value);
            trackMethodReturn();
        } else {
            trackCodeEntrance(getCurrentMethod() + "_recR");
            getVisualBST(getName()).traceToRight();
            trackMethodCall(getCurrentMethod());
            res = findNode(p.right, value);
            trackMethodReturn();
        }
        return res;
    }

    public void insert(int value) {
        if (root != null) {
            getVisualBST(getName()).traceRoot();
        }
        trackMethodCall("insertNode");
        if (root == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            root = new Node(value);
            root.parent = null;
            getVisualBST(getName()).insertNode(value);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
        } else {
            insertNode(root, value, 0);
        }
        trackMethodReturn();
        getVisualBST(getName()).traceBack();
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }
    private void insertNode(Node p, int value, int curDepth) {
        // check depth limit
        if (curDepth == MAX_DEPTH) {
            outputMessageError("Depth limit exceed.");
            return;
        }
        // deal with duplication
        trackCodeEntrance(getCurrentMethod() + "_ifEql");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        // discussion
        trackCodeEntrance(getCurrentMethod() + "_ifLR");
        if (value < p.value) {
            trackCodeEntrance(getCurrentMethod() + "_recL");
            if (p.left == null) {
                trackMethodCall(getCurrentMethod());
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.left = new Node(value);
                p.left.parent = p;
                getVisualBST(getName()).insertNode(value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                trackMethodReturn();
            } else {
                getVisualBST(getName()).traceToLeft();
                trackMethodCall(getCurrentMethod());
                insertNode(p.left, value, curDepth + 1);
                trackMethodReturn();
                getVisualBST(getName()).traceBack();
            }
        } else {
            trackCodeEntrance(getCurrentMethod() + "_recR");
            if (p.right == null) {
                trackMethodCall(getCurrentMethod());
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.right = new Node(value);
                p.right.parent = p;
                getVisualBST(getName()).insertNode(value);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                trackMethodReturn();
            } else {
                getVisualBST(getName()).traceToRight();
                trackMethodCall(getCurrentMethod());
                insertNode(p.right, value, curDepth + 1);
                trackMethodReturn();
                getVisualBST(getName()).traceBack();
            }
        }
        trackCodeEntrance(getCurrentMethod() + "_end");
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
        trackMethodCall("eraseNode");
        if (root != null && value == root.value) {
            trackCodeEntrance(getCurrentMethod() + "_ifEql");
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            if (root.left != null && root.right != null) {
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                Node np = __findMaxOf(root.left);
                getVisualBST(getName()).markNodeOfValue(np.value, ColorScheme.MARKED);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                int t = np.value;
                getVisualBST(getName()).markNodeOfValue(np.value, ColorScheme.DEFAULT);
                /* start sub routine */
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).traceToLeft();
                trackMethodCall(getCurrentMethod());
                eraseNode(root.left, np.value);
                trackMethodReturn();
                getVisualBST(getName()).traceBack();
                /* end sub routine */
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).modifyNode(root.value, t);
                root.value = t;
            } else {
                trackCodeEntrance(getCurrentMethod() + "_preDel");
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                Node p = (root.left != null ? root.left : root.right);
                trackCodeEntrance(getCurrentMethod() + (root.left != null ? "_linkL" : "_linkR"));
                getVisualBST(getName()).eraseNodePrep(root.value);
                trackCodeEntrance(getCurrentMethod() + "_delete");
                getVisualBST(getName()).eraseCachedNode();
                root = p;
            }
            trackCodeEntrance(getCurrentMethod() + "_return");
        } else {
            eraseNode(root, value);
        }
        trackMethodReturn();
        getVisualBST(getName()).traceBack();
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }
    private void eraseNode(Node p, int value) {
        // check if failed
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageReturn("The value is nonexistent.");
            return;
        }
        // check if reached
        trackCodeEntrance(getCurrentMethod() + "_ifEql");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            if (p.left != null && p.right != null) {
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                Node np = __findMaxOf(p.left);
                getVisualBST(getName()).markNodeOfValue(np.value, ColorScheme.MARKED);
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                int t = np.value;
                getVisualBST(getName()).markNodeOfValue(np.value, ColorScheme.DEFAULT);
                /* start sub routine */
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).traceToLeft();
                trackMethodCall(getCurrentMethod());
                eraseNode(p.left, np.value);
                trackMethodReturn();
                getVisualBST(getName()).traceBack();
                /* end sub routine */
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).modifyNode(p.value, t);
                p.value = t;
            } else {
                trackCodeEntrance(getCurrentMethod() + "_preDel");
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                Node f = p.parent;
                if (p.left != null) {
                    trackCodeEntrance(getCurrentMethod() + "_linkL");
                    if (p.value < f.value) {
                        f.left = p.left;
                        f.left.parent = f;
                    } else {
                        f.right = p.left;
                        f.right.parent = f;
                    }
                } else {
                    trackCodeEntrance(getCurrentMethod() + "_linkR");
                    if (p.value < f.value) {
                        f.left = p.right;
                        if (p.right != null) f.left.parent = f;
                    } else {
                        f.right = p.right;
                        if (p.right != null) f.right.parent = f;
                    }
                }
                getVisualBST(getName()).eraseNodePrep(p.value);
                trackCodeEntrance(getCurrentMethod() + "_delete");
                getVisualBST(getName()).eraseCachedNode();
            }
            trackCodeEntrance(getCurrentMethod() + "_return");
            return;
        }
        // discussion
        trackCodeEntrance(getCurrentMethod() + "_ifLR");
        if (value < p.value) {
            trackCodeEntrance(getCurrentMethod() + "_recL");
            getVisualBST(getName()).traceToLeft();
            trackMethodCall(getCurrentMethod());
            eraseNode(p.left, value);
        } else {
            trackCodeEntrance(getCurrentMethod() + "_recR");
            getVisualBST(getName()).traceToRight();
            trackMethodCall(getCurrentMethod());
            eraseNode(p.right, value);
        }
        trackMethodReturn();
        getVisualBST(getName()).traceBack();
        trackCodeEntrance(getCurrentMethod() + "_end");
    }

    public Node findMax() {
        if (root != null) {
            getVisualBST(getName()).traceRoot();
        }
        trackMethodCall("findMaxOf");
        Node res = findMaxOf(root);
        trackMethodReturn();
        return res;
    }
    private Node findMaxOf(Node p) {
        // check if null
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageReturn("false (empty BST)");
            return null;
        }
        // iterate
        trackCodeEntrance(getCurrentMethod() + "_loop");
        while (p.right != null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            getVisualBST(getName()).traceToRight();
            p = p.right;
            trackCodeEntrance(getCurrentMethod() + "_loop");
        }
        // return
        trackCodeEntrance(getCurrentMethod() + "_return");
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
        trackMethodCall("dfsPreOrder");
        dfsPreOrder(root);
        trackMethodReturn();
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }
    private void dfsPreOrder(Node p) {
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        trackCodeEntrance(getCurrentMethod() + "_main_begin");
        visitNode(p);
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        if (p.left != null) getVisualBST(getName()).markNodeOfValue(p.left.value, ColorScheme.MARKED);
        trackMethodCall(getCurrentMethod());
        dfsPreOrder(p.left);
        trackMethodReturn();
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        if (p.right != null) getVisualBST(getName()).markNodeOfValue(p.right.value, ColorScheme.MARKED);
        trackMethodCall(getCurrentMethod());
        dfsPreOrder(p.right);
        trackMethodReturn();
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
