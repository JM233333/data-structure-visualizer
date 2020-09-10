package jm233333.dsv.visual;

import java.util.ArrayList;

import jm233333.dsv.Director;

/**
 * The {@code VisualList} class defines the graphic components of a list that is displayed on the monitor.
 * Used in subclasses of {@code VDS}.
 */
public class VisualList extends Visual {

    public static final int CACHED_NODE = -1;

    private ArrayList<VisualListNode> arrayNode = new ArrayList<>();
    private VisualListNode cachedNode = null;
    private int markedIndex = -1;

    public VisualList(String name) {
        // super
        super(name);
        // initialize
        this.getStyleClass().add("visual-list");
    }

    private VisualListNode createNode(int index, int value) {
        VisualListNode node = new VisualListNode(value);
        double unitLength = node.getWidth() + VisualListNode.BOX_SIZE / 2;
        node.setLayoutX(32 + unitLength * index);
        node.setLayoutY(96);
        this.getChildren().add(node);
        return node;
    }
    private void removeNode(int index) {
        VisualListNode node = arrayNode.remove(index);
        Director.getInstance().createAnimation(1.0, node.scaleXProperty(), 0);
        Director.getInstance().updateAnimation(1.0, node.scaleYProperty(), 0);
        Director.getInstance().getLastTimeline().setOnFinished((event) -> {
            this.getChildren().remove(node);
        });
    }

    public void setCachedNode(int index, int value) {
        cachedNode = createNode(index, value);
    }

    public void insertCachedNode(int index) {
        if (cachedNode != null) {
            arrayNode.add(index, cachedNode);
            Director.getInstance().createAnimation(1.0, cachedNode.layoutYProperty(), 0);
            cachedNode = null;
        }
    }
    public void markNode(int index) {
        if (markedIndex == -1) {
            markedIndex = index;
            Director.getInstance().createAnimation(1.0, arrayNode.get(markedIndex).layoutYProperty(), 96);
        }
    }
    public void eraseMarkedNode() {
        if (markedIndex != -1) {
            removeNode(markedIndex);
            markedIndex = -1;
        }
    }
    public int getMarkedIndex() {
        return markedIndex;
    }

    public void setPointerNext(int indexFrom, int indexTo) {
        VisualListNode nodeFrom = (indexFrom == CACHED_NODE ? cachedNode : arrayNode.get(indexFrom));
        VisualListNode nodeTo = (indexTo == CACHED_NODE ? cachedNode : arrayNode.get(indexTo));
        nodeFrom.setNext(nodeTo);
    }
    public void setPointerNextToNull(int index) {
        VisualListNode node = (index == CACHED_NODE ? cachedNode : arrayNode.get(index));
        node.setNext(null);
    }

    public void moveRestNodes(int begin, int distance) {
        if (begin < arrayNode.size()) {
            VisualListNode lastNode = arrayNode.get(begin);
            double unitLength = lastNode.getWidth() + VisualListNode.BOX_SIZE / 2;
            Director.getInstance().createAnimation(1.0, lastNode.layoutXProperty(), lastNode.getLayoutX() + unitLength * distance);
            for (int i = begin + 1; i < arrayNode.size(); i++) {
                VisualListNode node = arrayNode.get(i);
                Director.getInstance().updateAnimation(1.0, node.layoutXProperty(), node.getLayoutX() + unitLength * distance);
            }
        }
    }

    public void pushFrontNode(int value) {
        insertNode(0, value);
    }
    public void insertNode(int index, int value) {
        // insert new node
        VisualListNode newNode = createNode(index, value);
        arrayNode.add(index, newNode);
        // set pointer next
        if (index > 0) {
            setPointerNext(index - 1, index);
        }
        if (index + 1 < arrayNode.size()) {
            setPointerNext(index, index + 1);
        }
        // move rest nodes
        moveRestNodes(index + 1, 1);
        // play animation
        Director.getInstance().createAnimation(1.0, newNode.layoutYProperty(), 0);
    }

    public void updateNode(int index, int value) {
        VisualListNode node = arrayNode.get(index);
        node.setValue(value);
    }

    public void popFrontNode() {
        eraseNode(0);
    }
    public void eraseNode(int index) {
        // get erased node
        VisualListNode erasedNode = arrayNode.get(index);
        removeNode(index);
        // set pointer
        if (index > 0) {
            if (index < arrayNode.size()) {
                setPointerNext(index - 1, index);
            } else {
                setPointerNextToNull(index - 1);
            }
        }
        // move rest nodes
        moveRestNodes(index, -1);
        // play animation
        Director.getInstance().createAnimation(1.0, erasedNode.layoutYProperty(), 96);
    }

    public void setHighlight(int index, boolean flag) {
        setHighlight(index, flag, false);
    }
    public void setHighlight(int index, boolean flag, boolean sync) {
        arrayNode.get(index).setHighlight(flag, sync);
    }
}
