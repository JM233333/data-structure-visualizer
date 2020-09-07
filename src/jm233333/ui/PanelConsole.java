package jm233333.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Class {@code PanelConsole} is the root UI component for sub-consoles of {@link Controller}.
 * Extended from JavaFX class {@link VBox} only for UI layout.
 */
public class PanelConsole<T extends Region> extends VBox {

    private HBox panelHeading = null;
    private T panelBody = null;
    private Label panelTitle;

    /**
     * Creates a {@code PanelConsole} with a specified body UI and title name.
     *
     * @param body the specified body UI
     * @param title the title name
     */
    public PanelConsole(T body, String title) {
        // super
        super();
        // initialize
        this.getStyleClass().add("panel");
        // initialize layout
        setPanelHeading(new HBox());
        setPanelBody(body);
        // initialize title
        panelTitle = new Label(title);
        panelTitle.getStyleClass().addAll("panel-title");
        panelHeading.getChildren().add(panelTitle);
    }
    public PanelConsole(T body) {
        this(body, "");
    }

    public void setPanelHeading(HBox heading) {
        this.getChildren().remove(panelHeading);
        panelHeading = heading;
        panelHeading.getStyleClass().addAll("panel-heading");
        this.getChildren().add(panelHeading);
    }
    public HBox getPanelHeading() {
        return panelHeading;
    }

    public void setPanelBody(T body) {
        this.getChildren().remove(panelBody);
        panelBody = body;
        if (panelBody != null) {
            panelBody.getStyleClass().addAll("panel-body");
            this.getChildren().add(panelBody);
        }
    }
    public T getPanelBody() {
        return panelBody;
    }

    public void setPanelTitle(String title) {
        panelTitle.setText(title);
    }
    public String getPanelTitle() {
        return panelTitle.getText();
    }
}
