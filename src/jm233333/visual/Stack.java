package jm233333.visual;

public class Stack extends VisualDataStructure {
    private int[] data;
    private int top;
    public Stack(int n) {
        data = new int[n];
        top = 0;
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
