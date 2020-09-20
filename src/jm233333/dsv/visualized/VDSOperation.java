package jm233333.dsv.visualized;

import java.util.ArrayList;

public class VDSOperation {

    private String methodName;
    private final ArrayList<Integer> arguments;

    public VDSOperation(String methodName, ArrayList<Integer> arguments) {
        this.methodName = methodName;
        this.arguments = new ArrayList<>();
        this.arguments.addAll(arguments);
    }

    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public ArrayList<Integer> getArguments() {
        return arguments;
    }
}
