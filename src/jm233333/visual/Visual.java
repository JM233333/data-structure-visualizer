package jm233333.visual;

import javafx.scene.Group;

/**
 * The {@code Visual} abstract class provides common properties for all types of graphic components.
 * Extended from JavaFX class {@code Group} only for UI layout.
 */
public class Visual extends Group {

    private String name;

    Visual() {
        this("");
    }
    Visual(String name) {
        this.name = name;
        this.getStyleClass().add("visual");
    }

    public void setName(String name) {
        this.name = name;
    }
    public final String getName() {
        return name;
    }
}
