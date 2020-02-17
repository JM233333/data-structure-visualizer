package jm233333.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class PanelConsole extends VBox {

    private HBox panelHeading = null;
    private Region panelBody = null;
    private Label panelTitle;

    public PanelConsole(Region body) {
        // super
        super();
        // initialize
        this.getStyleClass().add("panel");
        // initialize layout
        setPanelHeading(new HBox());
        setPanelBody(body);
        // initialize title
        panelTitle = new Label();
        panelTitle.getStyleClass().addAll("panel-title");
        panelHeading.getChildren().add(panelTitle);
    }

    public PanelConsole(Region body, String title) {
        this(body);
        panelTitle.setText(title);
    }

    public void setPanelHeading(HBox heading) {
        this.getChildren().remove(panelHeading);
        panelHeading = heading;
        panelHeading.getStyleClass().addAll("panel-heading");
        this.getChildren().add(panelHeading);
    }
    public final HBox getPanelHeading() {
        return panelHeading;
    }

    public void setPanelBody(Region body) {
        this.getChildren().remove(panelBody);
        panelBody = body;
        panelBody.getStyleClass().addAll("panel-body");
        this.getChildren().add(panelBody);
    }
    public final Region getPanelBody() {
        return panelBody;
    }

    public void setPanelTitle(String title) {
        panelTitle.setText(title);
    }
    public final String getPanelTitle() {
        return panelTitle.getText();
    }
}
