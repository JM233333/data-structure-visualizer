package jm233333.visual;

import javafx.scene.Group;

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
