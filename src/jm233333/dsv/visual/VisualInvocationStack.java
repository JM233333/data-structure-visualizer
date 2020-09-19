package jm233333.dsv.visual;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The {@code VisualArray} class defines the graphic components of an array that is displayed on the monitor.
 * Used in subclasses of {@code VDS}.
 */
public class VisualInvocationStack extends Visual {

    public static final String BUILTIN_NAME = "__BUILTIN_INVOCATION_STACK";

    private ArrayList<VisualInvocationStackFrame> arrayFrames;
    private int top = 0;
//    private VisualIndex visualIndexTop;

    public VisualInvocationStack(String name, int n) {
        // super
        super(name);
        // initialize
        this.getStyleClass().add("visual-array");
        // initialize frames
        arrayFrames = new ArrayList<>();
        for (int i = 0; i < n; i ++) {
            VisualInvocationStackFrame frame = new VisualInvocationStackFrame();
            frame.setLayoutY(frame.getHeight() * i);
            arrayFrames.add(frame);
            this.getChildren().add(frame);
        }
    }

    public void callMethod(String methodName) {
        arrayFrames.get(top).setMethodName(methodName);
        top ++;
    }
    public void returnMethod() {
        top --;
        arrayFrames.get(top).clear();
    }
    public String getCurrentMethod() {
        return (top == 0 ? null : arrayFrames.get(top - 1).getMethodName());
    }

    public double getWidth() {
        return (arrayFrames.isEmpty() ? 0 : arrayFrames.get(0).getWidth());
    }
    public double getHeight() {
        return (arrayFrames.isEmpty() ? 0 : arrayFrames.get(0).getHeight() * arrayFrames.size());
    }
}
