package jm233333.dsv.visualized;

import java.lang.reflect.Method;
import java.util.HashMap;

import javafx.scene.paint.Color;

import jm233333.dsv.ui.CodeTracker;
import jm233333.dsv.visual.ColorScheme;
import jm233333.dsv.visual.VisualBinaryTreeNode;

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
        // find node
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        trackMethodCall("findNode");
        if (root != null) {
            getVisualBST(getName()).traceRoot();
        }
        Node res = findNode(root, value);
        trackMethodReturn();
        getVisualBST(getName()).resetColorOfAll();
        return res;
    }
    private Node findNode(Node p, int value) {
        // check if failed
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
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
            // recursion to left-subtree
            trackCodeEntrance(getCurrentMethod() + "_recL");
            trackMethodCall(getCurrentMethod());
            getVisualBST(getName()).traceToLeft();
            res = findNode(p.left, value);
        } else {
            // recursion to right-subtree
            trackCodeEntrance(getCurrentMethod() + "_recR");
            trackMethodCall(getCurrentMethod());
            getVisualBST(getName()).traceToRight();
            res = findNode(p.right, value);
        }
        trackMethodReturn();
        return res;
    }

    public void insert(int value) {
        // insert node
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        trackMethodCall("insertNode");
        if (root == null) {
            // check if reached (*always true)
            trackCodeEntrance(CodeTracker.NEXT_LINE, false);
            // create node
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            root = new Node(value);
            root.parent = null;
            getVisualBST(getName()).insertNode(value);
            // return
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackMethodReturn();
        } else {
            getVisualBST(getName()).traceRoot();
            insertNode(root, value, 0);
            trackMethodReturn();
            getVisualBST(getName()).traceBack();
        }
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        getVisualBST(getName()).resetColorOfAll();
    }
    private void insertNode(Node p, int value, int curDepth) {
        // check depth limit
        if (curDepth == MAX_DEPTH) {
            outputMessageError("Depth limit exceed.");
            return;
        }
        // check if reached (*always false)
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        // check if duplicated
        trackCodeEntrance(getCurrentMethod() + "_ifEql");
        if (value == p.value) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            return;
        }
        // discussion
        trackCodeEntrance(getCurrentMethod() + "_ifLR");
        if (value < p.value) {
            // recursion to left-subtree
            trackCodeEntrance(getCurrentMethod() + "_recL");
            trackMethodCall(getCurrentMethod());
            if (p.left == null) {
                // check if reached (*always true)
                trackCodeEntrance(CodeTracker.NEXT_LINE, false);
                // create node
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.left = new Node(value);
                p.left.parent = p;
                getVisualBST(getName()).insertNode(value);
                // return
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                trackMethodReturn();
            } else {
                getVisualBST(getName()).traceToLeft();
                insertNode(p.left, value, curDepth + 1);
                trackMethodReturn();
                getVisualBST(getName()).traceBack();
            }
        } else {
            // recursion to right-subtree
            trackCodeEntrance(getCurrentMethod() + "_recR");
            trackMethodCall(getCurrentMethod());
            if (p.right == null) {
                // check if reached (*always true)
                trackCodeEntrance(CodeTracker.NEXT_LINE, false);
                // create node
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                p.right = new Node(value);
                p.right.parent = p;
                getVisualBST(getName()).insertNode(value);
                // return
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                trackMethodReturn();
            } else {
                getVisualBST(getName()).traceToRight();
                insertNode(p.right, value, curDepth + 1);
                trackMethodReturn();
                getVisualBST(getName()).traceBack();
            }
        }
        // end
        trackCodeEntrance(getCurrentMethod() + "_end");
    }

    public void erase(int value) {
        // erase node
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        trackMethodCall("eraseNode");
        if (root != null) {
            getVisualBST(getName()).traceRoot();
        }
        if (root != null && value == root.value) {
            // check if reached (*always true)
            trackCodeEntrance(getCurrentMethod() + "_ifEql");
            // discussion
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackCodeEntrance(CodeTracker.NEXT_LINE, true);
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
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        getVisualBST(getName()).resetColorOfAll();
    }
    private void eraseNode(Node p, int value) {
        // check if failed
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageReturn("The value is nonexistent.");
            return;
        }
        // check if reached
        trackCodeEntrance(getCurrentMethod() + "_ifEql");
        if (value == p.value) {
            // discussion
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            trackCodeEntrance(CodeTracker.NEXT_LINE, false);
            if (p.left != null && p.right != null) {
                // find the max node of left-subtree
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                Node np = __findMaxOf(p.left);
                getVisualBST(getName()).markNodeOfValue(np.value, ColorScheme.MARKED);
                // cache the max value
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                int t = np.value;
                getVisualBST(getName()).markNodeOfValue(np.value, ColorScheme.DEFAULT);
                // erase the max node of left-subtree (start a sub-routine)
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                trackMethodCall(getCurrentMethod());
                getVisualBST(getName()).traceToLeft();
                eraseNode(p.left, np.value);
                trackMethodReturn();
                getVisualBST(getName()).traceBack();
                // modify the value
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                getVisualBST(getName()).modifyNode(p.value, t);
                p.value = t;
            } else {
                // cache the to-be-erased node
                trackCodeEntrance(getCurrentMethod() + "_preDel");
                // discussion
                trackCodeEntrance(CodeTracker.NEXT_LINE);
                Node f = p.parent;
                if (p.left != null) {
                    // relink to left-subtree
                    trackCodeEntrance(getCurrentMethod() + "_linkL");
                    if (p.value < f.value) {
                        f.left = p.left;
                        f.left.parent = f;
                    } else {
                        f.right = p.left;
                        f.right.parent = f;
                    }
                } else {
                    // relink to right-subtree
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
                // really erase node
                trackCodeEntrance(getCurrentMethod() + "_delete");
                getVisualBST(getName()).eraseCachedNode();
            }
            // return
            trackCodeEntrance(getCurrentMethod() + "_return");
            return;
        }
        // discussion
        trackCodeEntrance(getCurrentMethod() + "_ifLR");
        if (value < p.value) {
            // recursion to left-subtree
            trackCodeEntrance(getCurrentMethod() + "_recL");
            trackMethodCall(getCurrentMethod());
            getVisualBST(getName()).traceToLeft();
            eraseNode(p.left, value);
        } else {
            // recursion to right-subtree
            trackCodeEntrance(getCurrentMethod() + "_recR");
            trackMethodCall(getCurrentMethod());
            getVisualBST(getName()).traceToRight();
            eraseNode(p.right, value);
        }
        trackMethodReturn();
        getVisualBST(getName()).traceBack();
        // end
        trackCodeEntrance(getCurrentMethod() + "_end");
    }

    public Node findMax() {
        // find the max node of the whole tree
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        trackMethodCall("findMaxOf");
        if (root != null) {
            getVisualBST(getName()).traceRoot();
        }
        Node res = findMaxOf(root);
        trackMethodReturn();
        return res;
    }
    private Node findMaxOf(Node p) {
        // check if empty
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        if (p == null) {
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            outputMessageReturn("false (empty BST)");
            return null;
        }
        // loop
        trackCodeEntrance(getCurrentMethod() + "_loop");
        while (p.right != null) {
            // go next
            trackCodeEntrance(CodeTracker.NEXT_LINE);
            getVisualBST(getName()).traceToRight();
            p = p.right;
            // re-loop
            trackCodeEntrance(getCurrentMethod() + "_loop");
        }
        // return
        trackCodeEntrance(getCurrentMethod() + "_return");
        outputMessageReturn("(Node)(value = " + p.value + ")");
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
        __traverse("dfsPreOrder");
    }
    public void traverseInOrder() {
        __traverse("dfsInOrder");
    }
    public void traversePostOrder() {
        __traverse("dfsPostOrder");
    }

    private void __traverse(String dfsMethodName) {
        outputMessage(" | RES : ", 16, Color.BLACK, false);
        // dfs pre-order
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        trackMethodCall(dfsMethodName);
        getVisualBST(getName()).traceRoot();
        try {
            Method dfs = this.getClass().getDeclaredMethod(dfsMethodName, Node.class, Method.class);
            dfs.setAccessible(true);
            dfs.invoke(this, root, dfs);
            dfs.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        trackMethodReturn();
        getVisualBST(getName()).traceBack();
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
        getVisualBST(getName()).resetColorOfAll();
        outputMessage("\n", 16, Color.BLACK, false);
    }
    private void dfsPreOrder(Node p, Method self) throws Exception {
        // check if null (*always false)
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        // traverse
        visitNode(p);
        __traverseLeftSubtree(p, self);
        __traverseRightSubtree(p, self);
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }
    private void dfsInOrder(Node p, Method self) throws Exception {
        // check if null (*always false)
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        // traverse
        __traverseLeftSubtree(p, self);
        visitNode(p);
        __traverseRightSubtree(p, self);
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }
    private void dfsPostOrder(Node p, Method self) throws Exception {
        // check if null (*always false)
        trackCodeEntrance(CodeTracker.NEXT_LINE, false);
        // traverse
        __traverseLeftSubtree(p, self);
        __traverseRightSubtree(p, self);
        visitNode(p);
        // end
        trackCodeEntrance(CodeTracker.NEXT_LINE);
    }

    private void visitNode(Node p) {
        trackCodeEntrance(getCurrentMethod() + "_visit");
        outputMessage(p.value + " ", 16, Color.BLACK, false);
    }

    private void __traverseLeftSubtree(Node p, Method dfs) throws Exception {
        trackCodeEntrance(getCurrentMethod() + "_recL");
        if (p.left != null) {
            trackMethodCall(getCurrentMethod());
            getVisualBST(getName()).traceToLeft();
            dfs.invoke(this, p.left, dfs);
            trackMethodReturn();
            getVisualBST(getName()).traceBack();
        }
    }
    private void __traverseRightSubtree(Node p, Method dfs) throws Exception {
        trackCodeEntrance(getCurrentMethod() + "_recR");
        if (p.right != null) {
            trackMethodCall(getCurrentMethod());
            getVisualBST(getName()).traceToRight();
            dfs.invoke(this, p.right, dfs);
            trackMethodReturn();
            getVisualBST(getName()).traceBack();
        }
    }
}
