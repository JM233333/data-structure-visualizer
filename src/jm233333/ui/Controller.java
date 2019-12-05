package jm233333.ui;

import javafx.scene.layout.*;
import jm233333.visual.VisualDataStructure;

import java.lang.reflect.*;
import java.util.*;

public class Controller extends GridPane {
    private VisualDataStructure visualDS;
    public Controller(VisualDataStructure visualDS) {
        super();
        this.visualDS = visualDS;
        // initialize method triggers in the controller
        final MethodTrigger trigger1 = new MethodTrigger("push", "value");
        GridPane.setConstraints(trigger1, 0, 0);
        this.getChildren().addAll(trigger1);
        // initialize event listener for method triggers
        trigger1.getButton().setOnAction((event) -> {
            // get method name and parameters
            String name = trigger1.getName();
            ArrayList<Integer> parameters = trigger1.getArguments();
            StringBuilder s = new StringBuilder(name);
            for (int parameter : parameters) {
                s.append(" ").append(parameter);
            }
            System.out.println(s);
            // invoke method
            try {
                Class<?>[] parameterTypes = new Class<?>[parameters.size()];
                for (int i = 0; i < parameters.size(); i ++) {
                    Class cls = parameters.get(i).getClass();
                    try {
                        Field field = cls.getDeclaredField("TYPE");
                        parameterTypes[i] = (Class<?>) field.get(parameters.get(i));
                    } catch (NoSuchFieldException e) {
                        parameterTypes[i] = cls;
                    }
                }
                Method method = visualDS.getClass().getDeclaredMethod(name, parameterTypes);
                method.invoke(visualDS, parameters.toArray());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
}
