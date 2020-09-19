package jm233333.dsv.visual;

import javafx.scene.Group;

/**
 * Abstract class {@code Visual} provides common properties for all types of visual components used in {@link jm233333.dsv.visualized.VDS}.
 * Extended from JavaFX class {@link Group} only for UI layout.
 */
public class Visual extends Group {

    private String name;
    private double baseX, baseY;

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
        this.baseX = 0;
        this.baseY = 0;
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

    public void setBasePosition(double baseX, double baseY) {
        setBaseX(baseX);
        setBaseY(baseY);
    }
    public void setBaseX(double baseX) {
        this.baseX = baseX;
    }
    public void setBaseY(double baseY) {
        this.baseY = baseY;
    }
    public final double getBaseX() {
        return baseX;
    }
    public final double getBaseY() {
        return baseY;
    }

}
