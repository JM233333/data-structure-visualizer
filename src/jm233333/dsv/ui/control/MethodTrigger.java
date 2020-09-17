package jm233333.dsv.ui.control;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import jm233333.dsv.Director;
import jm233333.dsv.ui.Controller;
import jm233333.dsv.visualized.VDS;

/**
 * Class {@code MethodTrigger} is a sub-component of {@link Controller} to manipulate the {@link jm233333.dsv.visualized.VDS} with a specified public method of it.
 * Extended from JavaFX class {@link HBox} only for UI layout.
 */
public class MethodTrigger extends HBox {

    private static final double HEIGHT = 36;

    private VDS vds;
    private String name;

    private Button button;
    private ArrayList<TextField> textFields;

    /**
     * Creates a {@code MethodTrigger} with the {@link VDS} and the name and parameter list of the specified method.
     *
     * @param vds the {@link VDS}
     * @param nameMethod the name of the method
     * @param nameParameters the name list of parameters of the method
     */
    public MethodTrigger(VDS vds, String nameMethod, String... nameParameters) {
        this.name = nameMethod;
        this.vds = vds;
        initialize(nameMethod, nameParameters);
    }

    /**
     * Creates a {@code MethodTrigger} with the {@link VDS} and the specified {@link Method}.
     *
     * @param vds the {@link VDS}
     * @param method The method
     */
    public MethodTrigger(VDS vds, Method method) {
        // get method metadata
        String nameMethod = method.getName();
        Parameter[] parameters = method.getParameters();
//        Class returnType = method.getReturnType();
        // get parameter names
        String[] nameParameters = new String[parameters.length];
        for (int i = 0; i < parameters.length; i ++) {
            nameParameters[i] = parameters[i].getName();
        }
        // initialize
        this.name = nameMethod;
        this.vds = vds;
        initialize(nameMethod, nameParameters);
    }

    private void initialize(String nameMethod, String... nameParameters) {
        // initialize
        this.setPadding(new Insets(0, 16, 0, 0));
        this.setSpacing(16);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setMinHeight(HEIGHT);
        this.setMaxHeight(HEIGHT);
        this.getStyleClass().add("method-trigger");
        // initialize button
        button = new Button(nameMethod);
        button.getStyleClass().addAll("btn", "btn-primary");
        button.setMinHeight(HEIGHT);
        this.getChildren().add(button);
        // initialize textFields
        textFields = new ArrayList<>();
        for (String nameParameter : nameParameters) {
            // initialize label
            Label label = new Label(nameParameter);
            label.getStyleClass().addAll("lbl", "lbl-default");
            label.setMinHeight(HEIGHT);
            // initialize textField
            TextField textField = new TextField();
            textField.getStyleClass().addAll("text-field");
            textField.setPrefWidth(48);
            textField.setMinHeight(HEIGHT);
            // set layout of nodes
            HBox box = new HBox();
            box.setSpacing(0);
            box.setAlignment(Pos.CENTER_LEFT);
            box.getChildren().addAll(label, textField);
            // add nodes to this
            this.getChildren().add(box);
            // store reference to writer
            textFields.add(textField);
        }
        // set listener
        button.setOnAction((event) -> {
            trigger(getArguments());
            Director.getInstance().playAnimation();
        });
    }

    /**
     * Triggers the method.
     */
    public boolean trigger(ArrayList<Integer> arguments) { //, EventHandler<ActionEvent> eventAtLast) {
        // debug
        StringBuilder s = new StringBuilder(name);
        for (int argument : arguments) {
            s.append(" ").append(argument);
        }
        System.out.println(s);
        // get parameter type list
        Class<?>[] parameterTypes = new Class<?>[arguments.size()];
        for (int i = 0; i < arguments.size(); i ++) {
            Class cls = arguments.get(i).getClass();
            try {
                Field field = cls.getDeclaredField("TYPE");
                parameterTypes[i] = (Class<?>) field.get(arguments.get(i));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                parameterTypes[i] = cls;
            }
        }
        // invoke method
        vds.outputMessageInvoke(s.toString());
        vds.trackCodeMethodBeginning(name);
        try {
            Method method = vds.getClass().getDeclaredMethod(name, parameterTypes);
            method.invoke(vds, arguments.toArray());
//            Director.getInstance().getLastTimeline().setOnFinished(eventAtLast);
            return true;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the name of the method.
     *
     * @return the name of the method
     */
    public final String getName() {
        return name;
    }
    /**
     * Gets the current inputted parameters of the {@code MethodTrigger}.
     *
     * @return the current inputted parameters of the method trigger
     */
    public ArrayList<Integer> getArguments() {
        // initialize
        ArrayList<Integer> arguments = new ArrayList<>();
        // iterate textFields
        for (TextField textField : textFields) {
            // get argument
            try {
                int argument = Integer.parseInt(textField.getText());
                arguments.add(argument);
            } catch (NumberFormatException e) {
                System.out.println("Error : illegal argument in function " + name);
                arguments.add(0); // return null;
            }
        }
        // return
        return arguments;
    }
}
