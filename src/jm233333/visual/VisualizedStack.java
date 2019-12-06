package jm233333.visual;

import javafx.util.Pair;
import jm233333.ui.Monitor;

public class VisualizedStack extends VisualizedDataStructure {
    private int[] data;
    private int top;
    public VisualizedStack(int n) {
        data = new int[n];
        top = 0;
    }
    @Override public void setMonitor(Monitor monitor) {
        super.setMonitor(monitor);
        this.createVisualArray("data", data.length, new Pair<>("top", 0));
    }
    public void push(int value) {
        System.out.println("Stk push " + value);
        data[++top] = value;
    }
    public void pop() {
        top --;
    }
    public int top() {
        return data[top];
    }
}
