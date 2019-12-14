package jm233333.ui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.layout.*;
import jm233333.Director;
import jm233333.visualized.VisualizedDataStructure;

import java.lang.reflect.*;
import java.util.*;

/**
 * The {@code Controller} class is the user interface in which the user can manipulate the visualized data structure.
 * Extended from JavaFX class {@code FlowPane} only for UI layout.
 */
public class Controller extends Group {

    private FlowPane flowPane;
    private VisualizedDataStructure visualDS;

    /**
     * Creates a Controller with the reference to the visualized data structure.
     *
     * @param visualDS The reference to the visualized data structure
     */
    public Controller(VisualizedDataStructure visualDS) {
        // super
        super();
        // initialize layout
        flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setMaxHeight(48 * 4 + 16 * 2);
        flowPane.setPadding(new Insets(16));
        this.getChildren().add(flowPane);
        // set reference to the interrelated visual data structure
        this.visualDS = visualDS;
        // initialize method triggers in the controller
        initializeMethodTriggers();
        // prohibit operating while playing animation
        Director.getInstance().animationPlayingProperty().addListener((event) -> {
            if (Director.getInstance().isAnimationPlaying()) {
                this.setDisable(true);
            } else {
                this.setDisable(false);
            }
        });
    }

    /**
     * Finds all public methods of the visualized data structure and initializes corresponding method triggers with them.
     */
    private void initializeMethodTriggers() {
        // find all public methods of visualDS
        Method[] methods = visualDS.getClass().getDeclaredMethods();
        int methodCount = methods.length;
        // initialize method triggers
        ArrayList<MethodTrigger> methodTriggers = new ArrayList<>();
        for (Method method : methods) {
            if (method.getName().equals("createVisual")) {
                continue;
            }
            if (method.getName().equals("setMode")) {
                continue;
            }
//            System.out.println(method.getName() + " : " + Arrays.toString(method.getParameterTypes()));
            MethodTrigger methodTrigger = new MethodTrigger(method);
            methodTriggers.add(methodTrigger);
            flowPane.getChildren().add(methodTrigger);
        }
        // initialize event listener for method triggers
        for (MethodTrigger methodTrigger : methodTriggers) {
            // set event listener
            methodTrigger.getButton().setOnAction((event) -> {
                // get method name and parameters
                String name = methodTrigger.getName();
                ArrayList<Integer> parameters = methodTrigger.getParameters();
                StringBuilder s = new StringBuilder(name);
                // output for debug
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
}
