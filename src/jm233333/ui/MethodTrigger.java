package jm233333.ui;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

/**
 * The {@code MethodTrigger} class defines the UI to manipulate the visualized data structure with a specified public method of it.
 * Extended from JavaFX class {@code HBox} only for UI layout.
 */
public class MethodTrigger extends HBox {

    private String name;
    private Button button;
    private ArrayList<TextField> textFields;

    /**
     * Creates a MethodTrigger with the name data of a specified method.
     *
     * @param nameMethod The name of the method
     * @param nameParameters The name list of parameters of the method
     */
    public MethodTrigger(String nameMethod, String... nameParameters) {
        // super
        super();
        // initialize
        initialize(nameMethod, nameParameters);
    }

    /**
     * Creates a MethodTrigger with the specified method.
     *
     * @param method The reference to the method
     */
    public MethodTrigger(Method method) {
        // super
        super();
        // get method metadata
        String nameMethod = method.getName();
        Parameter[] parameters = method.getParameters();
//        Class returnType = method.getReturnType();
        // get parameter names
        String[] nameParameters = new String[parameters.length];
        for (int i = 0; i < parameters.length; i ++) {
            nameParameters[i] = new String(parameters[i].getName());
        }
        // initialize
        initialize(nameMethod, nameParameters);
    }

    /**
     * Initializes the method trigger.
     *
     * @param nameMethod The name of the method
     * @param nameParameters The name list of parameters of the method
     */
    private void initialize(String nameMethod, String... nameParameters) {
        // initialize layout of this
        this.setPadding(new Insets(8));
        this.setSpacing(32);
        this.setAlignment(Pos.CENTER_LEFT);
        // initialize name
        this.name = nameMethod;
        // initialize button
        button = new Button(nameMethod);
        this.getChildren().add(button);
        // initialize textFields
        textFields = new ArrayList<>();
        for (String nameParameter : nameParameters) {
            // initialize label
            Label label = new Label(nameParameter);
            // initialize textField
            TextField textField = new TextField();
            textField.setPrefWidth(48);
            // set layout of nodes
            HBox box = new HBox();
            box.setSpacing(8);
            box.setAlignment(Pos.CENTER_LEFT);
            box.getChildren().addAll(label, textField);
            // add nodes to this
            this.getChildren().add(box);
            // store reference to writer
            textFields.add(textField);
        }
    }

    public final String getName() {
        return name;
    }
    public final Button getButton() {
        return button;
    }
    public final ArrayList<TextField> getTextFields() {
        return textFields;
    }
    public ArrayList<Integer> getParameters() {
        // initialize
        ArrayList<Integer> parameters = new ArrayList<>();
        // iterate textFields
        for (TextField textField : textFields) {
            // get argument
            try {
                int parameter = Integer.parseInt(textField.getText());
                parameters.add(parameter);
            } catch (NumberFormatException e) {
                System.out.println("Error : illegal argument in function " + name);
                parameters.add(0); // return null;
            }
        }
        // return
        return parameters;
    }
}
