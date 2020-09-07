package jm233333.ui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import jm233333.Director;
import jm233333.Global;
import jm233333.ui.control.*;
import jm233333.visualized.VDS;

/**
 * Class {@code Controller} is the UI component to manipulate the {@link VDS}.
 * Extended from JavaFX class {@link ScrollPane} only for UI layout.
 */
public class Controller extends ScrollPane {

    private HBox root;
    private MethodTriggers methodTriggers;
    private AnimationController animationController;
    private OutputBox outputBox;
    private BatchProcessor batchProcessor;

    /**
     * Creates a {@code Controller} with a specific {@link VDS}.
     *
     * @param vds The data structure that will be visualized
     */
    public Controller(VDS vds) {
        // initialize
        this.setId("controller");
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        // initialize root
        root = new HBox();
        root.setAlignment(Pos.CENTER_LEFT);
        root.setPadding(new Insets(Layout.padding));
        root.setSpacing(Layout.padding);
        this.setContent(root);
        // initialize sub-interfaces
        methodTriggers = new MethodTriggers(vds);
        batchProcessor = new BatchProcessor(methodTriggers);
        outputBox = new OutputBox();
        animationController = new AnimationController();
        // bind the output box with the VDS
        vds.setOutputBox(outputBox.getTextFlow());
        //
        for (PanelConsole panel : new PanelConsole[]{methodTriggers, animationController, outputBox, batchProcessor}) {
            panel.getStyleClass().add("panel-primary");
            panel.setMaxHeight(Layout.panelHeight);
            root.getChildren().add(panel);
        }
        // initialize layout of sub-interfaces
        initializeLayout();
        // prohibit partial operations while playing animation
        Director.getInstance().animationPlayingProperty().addListener((event) -> {
            for (PanelConsole panel : new PanelConsole[]{methodTriggers, batchProcessor}) {
                panel.setDisable(Director.getInstance().isAnimationPlaying());
            }
//            animationRateSlider.setDisable(Director.getInstance().isAnimationPlaying());
        });
        // initialize width property
        final PanelConsole[] panels = new PanelConsole[]{methodTriggers, animationController, outputBox, batchProcessor};
//        for (PanelConsole panel : panels) {
        batchProcessor.widthProperty().addListener((obs, ov, nv) -> {
            // get the sum of width
            double sumWidth = 5 * Layout.padding;
            for (PanelConsole iPanel : panels) {
                sumWidth += iPanel.widthProperty().getValue();
            }
            root.setMinWidth(sumWidth);
            final double realWidth = sumWidth + Layout.padding + 6;
            //
            Global.getPrimaryStage().widthProperty().addListener((observable, oldValue, newValue) -> {
                double height = ((double)newValue >= realWidth ? Layout.panelHeight - 10 : Layout.panelHeight + 6);
                this.setMinHeight(height);
                this.setMaxHeight(height);
            });
            //
            final double maxWidth = Global.getScreenWidth();
            final double minWidth = Math.min(640.0, maxWidth);
            final Stage primaryStage = Global.getPrimaryStage();
            primaryStage.minWidthProperty().setValue(realWidth);
            primaryStage.maxWidthProperty().setValue(realWidth);
            primaryStage.minWidthProperty().setValue(minWidth);
            primaryStage.maxWidthProperty().setValue(maxWidth);
        });
//        }
    }

    private void initializeLayout() {
        initializeLayoutOfMethodTriggers();
        initializeLayoutOfAnimationController();
        initializeLayoutOfOutputBox();
        initializeLayoutOfBatchProcessor();
    }
    private void initializeLayoutOfMethodTriggers() {
        FlowPane pane = methodTriggers.getPanelBody();
        pane.setOrientation(Orientation.VERTICAL);
        pane.setPadding(new Insets(Layout.padding));
        pane.setVgap(Layout.padding);
        pane.setMinHeight(Layout.panelBodyHeight);
        pane.setMaxHeight(Layout.panelBodyHeight);
        pane.setPrefHeight(Layout.panelBodyHeight);
    }
    private void initializeLayoutOfAnimationController() {
        FlowPane pane = animationController.getPanelBody();
        pane.setOrientation(Orientation.HORIZONTAL);
        pane.setPadding(new Insets(Layout.padding));
        pane.setHgap(Layout.padding);
        pane.setVgap(Layout.padding);
        pane.setMinWidth(Layout.panelBodyWidth);
        pane.setMaxWidth(Layout.panelBodyWidth);
        pane.setPrefWidth(Layout.panelBodyWidth);
        pane.setMinHeight(Layout.panelBodyHeight);
        pane.setMaxHeight(Layout.panelBodyHeight);
        pane.setPrefHeight(Layout.panelBodyHeight);
    }
    private void initializeLayoutOfOutputBox() {
        ScrollPane pane = outputBox.getPanelBody();
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setMinWidth(Layout.panelBodyWidth);
        pane.setMaxWidth(Layout.panelBodyWidth);
        pane.setPrefWidth(Layout.panelBodyWidth);
        pane.setMinHeight(Layout.panelBodyHeight);
        pane.setMaxHeight(Layout.panelBodyHeight);
        pane.setPrefHeight(Layout.panelBodyHeight);
        TextFlow textFlow = outputBox.getTextFlow();
        textFlow.setMinWidth(Layout.panelBodyWidth - 2 * Layout.padding + 2);
        textFlow.setMinHeight(Layout.panelBodyHeight - 2 * Layout.padding + 2);
    }
    private void initializeLayoutOfBatchProcessor() {
        FlowPane pane = batchProcessor.getPanelBody();
        pane.setHgap(Layout.padding);
        pane.setVgap(Layout.padding);
        pane.setMinWidth(Layout.panelBodyWidth);
        pane.setMaxWidth(Layout.panelBodyWidth);
        pane.setPrefWidth(Layout.panelBodyWidth);
        TextArea textArea = batchProcessor.getTextArea();
        textArea.setMinWidth(pane.getMinWidth() - 32 + 2);
        textArea.setMaxWidth(pane.getMaxWidth() - 32 + 2);
        textArea.setMinHeight(outputBox.getTextFlow().getMinHeight() - 48);
        textArea.setMaxHeight(outputBox.getTextFlow().getMinHeight() - 48);
    }

}
