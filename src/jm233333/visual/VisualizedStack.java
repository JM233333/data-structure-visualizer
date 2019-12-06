package jm233333.visual;

import javafx.util.Pair;
import jm233333.ui.Monitor;

/**
 * The {@code VisualizedStack} class defines the data structure {@code Stack} for visualizing.
 * Extended from abstract class {@code VisualizedDataStructure}.
 */
public class VisualizedStack extends VisualizedDataStructure {

    private int[] data;
    private int top;

    public VisualizedStack(int n) {
        super();
        data = new int[n];
        top = 0;
    }

    @Override
    void createVisual() {
        createVisualArray("data", data.length, new Pair<>("top", 0));
    }

    public void push(int value) {
        data[++top] = value;
    }

    public void pop() {
        top --;
    }

    public int top() {
        return data[top];
    }
}
