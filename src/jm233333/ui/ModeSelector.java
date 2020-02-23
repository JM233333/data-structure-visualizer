package jm233333.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

/**
 * The {@code ModeSelector} class defines the UI to select a implementation mode of the visualized data structure.
 * Extended from JavaFX class {@code HBox} only for UI layout.
 */
public class ModeSelector extends HBox {

    private Label label;
    private ArrayList<Button> buttons = new ArrayList<>();

    /**
     * Creates an empty ModeSelector.
     */
    public ModeSelector() {
        // initialize
        this.setPadding(new Insets(0, 16, 0, 16));
        this.setSpacing(32);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setMinHeight(32);
        this.setMaxHeight(32);
        // initialize label
        label = new Label("Select Mode:");
        this.getChildren().add(label);
    }

    public void addButton(Button button) {
        buttons.add(button);
        this.getChildren().add(button);
    }
    public final ArrayList<Button> getButtons() {
        return buttons;
    }
}
