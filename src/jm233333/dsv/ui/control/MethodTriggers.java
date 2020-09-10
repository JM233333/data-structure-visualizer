package jm233333.dsv.ui.control;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.FlowPane;

import jm233333.dsv.ui.PanelConsole;
import jm233333.dsv.visualized.VDS;

public class MethodTriggers extends PanelConsole<FlowPane> {

    private HashMap<String, MethodTrigger> mapTriggers;

    public MethodTriggers(VDS vds) {
        // super
        super(new FlowPane(), "Method Triggers");
        // find all public methods of VDS
        Method[] methods = vds.getClass().getDeclaredMethods();
        // initialize triggers
        mapTriggers = new HashMap<>();
        for (Method method : methods) {
            if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                continue;
            }
            if (method.getName().equals("createVisual")) {
                continue;
            }
            if (method.getName().equals("setMode")) {
                continue;
            }
//            System.out.println(method.getName() + " : " + Arrays.toString(method.getParameterTypes()));
            MethodTrigger methodTrigger = new MethodTrigger(vds, method);
            mapTriggers.put(method.getName(), methodTrigger);
            getPanelBody().getChildren().add(methodTrigger);
        }
    }

    public void trigger(String name, ArrayList<Integer> arguments, EventHandler<ActionEvent> eventAtLast) {
        mapTriggers.get(name).trigger(arguments, eventAtLast);
    }
}
