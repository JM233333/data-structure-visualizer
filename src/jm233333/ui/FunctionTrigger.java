package jm233333.ui;

import java.util.*;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class FunctionTrigger extends HBox {

    private Button trigger;
    private ArrayList<TextField> writers;

    public FunctionTrigger(String nameFunction, String... nameArguments) {
        // initialize layout of this
        super();
        this.setSpacing(32);
        this.setAlignment(Pos.CENTER_LEFT);
        // initialize trigger
        trigger = new Button(nameFunction);
        this.getChildren().add(trigger);
        // initialize writers
        writers = new ArrayList<>();
        for (String argName : nameArguments) {
            // initialize nodes
            Label argLabel = new Label(argName);
            TextField argWriter = new TextField();
            // set layout of nodes
            HBox box = new HBox();
            box.setSpacing(8);
            box.setAlignment(Pos.CENTER_LEFT);
            box.getChildren().addAll(argLabel, argWriter);
            // add nodes to this
            this.getChildren().add(box);
            // store reference to writer
            writers.add(argWriter);
        }
    }

    public final Button getTrigger() {
        return trigger;
    }
    public final ArrayList<TextField> getWriters() {
        return writers;
    }
}
