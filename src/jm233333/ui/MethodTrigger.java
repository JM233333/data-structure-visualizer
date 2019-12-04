package jm233333.ui;

import java.util.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class MethodTrigger extends HBox {

    private String name;
    private Button button;
    private ArrayList<TextField> textFields;

    public MethodTrigger(String nameFunction, String... nameArguments) {
        // initialize layout of this
        super();
        this.setPadding(new Insets(8));
        this.setSpacing(32);
        this.setAlignment(Pos.CENTER_LEFT);
        // initialize name
        this.name = nameFunction;
        // initialize button
        button = new Button(nameFunction);
        this.getChildren().add(button);
        // initialize textFields
        textFields = new ArrayList<>();
        for (String argName : nameArguments) {
            // initialize label
            Label label = new Label(argName);
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
