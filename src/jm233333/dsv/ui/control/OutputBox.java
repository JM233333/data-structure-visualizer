package jm233333.dsv.ui.control;

import javafx.scene.control.ScrollPane;
import javafx.scene.text.TextFlow;

import jm233333.dsv.ui.PanelConsole;

public class OutputBox extends PanelConsole<ScrollPane> {

    private TextFlow textFlow;

    public OutputBox() {
        // super
        super(new ScrollPane(), "Output Box");
        // initialize the text flow
        textFlow = new TextFlow();
        textFlow.getStyleClass().addAll("bg-default");
        getPanelBody().setContent(textFlow);
    }

    public TextFlow getTextFlow() {
        return textFlow;
    }
}
