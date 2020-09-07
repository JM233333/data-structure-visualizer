package jm233333.visual;

import javafx.scene.Group;

/**
 * Abstract class {@code Visual} provides common properties for all types of visual components used in {@link jm233333.visualized.VDS}.
 * Extended from JavaFX class {@link Group} only for UI layout.
 */
public class Visual extends Group {

    private String name;

    /**
     * Creates a {@code Visual} with an empty name.
     */
    Visual() {
        this("");
    }

    /**
     * Creates a {@code Visual} with a specific name.
     *
     * @param name the name of the visual component
     */
    Visual(String name) {
        this.name = name;
        this.getStyleClass().add("visual");
        this.initializeCSS();
    }

    private void initializeCSS() {
        Class cls = this.getClass();
        this.getStylesheets().add(cls.getResource("/css/Visual.css").toExternalForm());
    }

    public void setName(String name) {
        this.name = name;
    }
    public final String getName() {
        return name;
    }
}
