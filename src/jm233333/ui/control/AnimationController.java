package jm233333.ui.control;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import jm233333.Director;
import jm233333.ui.Layout;
import jm233333.ui.PanelConsole;

public class AnimationController extends PanelConsole<FlowPane> {

    private Slider animationRateSlider;

    public AnimationController() {
        // super
        super(new FlowPane(), "Animation Controller");

        // initialize rate-control
        final VBox vBoxRate = new VBox();
        vBoxRate.getStyleClass().addAll("panel", "panel-default");
        vBoxRate.setPadding(new Insets(Layout.padding / 2));
        vBoxRate.setSpacing(Layout.padding);
        getPanelBody().getChildren().add(vBoxRate);
        final Label labelRate = new Label("Animation Rate : ");
        vBoxRate.getChildren().add(labelRate);
        Slider slider = new Slider(Director.MIN_ANIMATION_RATE, Director.MAX_ANIMATION_RATE, Director.getInstance().getAnimationRate());
        slider.setPrefWidth(Layout.panelBodyWidth - 3 * Layout.padding - 2);
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickUnit(slider.getMax() - slider.getMin() - 1);
        slider.setMinorTickCount((int)(slider.getMajorTickUnit() / Director.UNIT_ANIMATION_RATE));
        slider.setBlockIncrement(Director.UNIT_ANIMATION_RATE);
        vBoxRate.getChildren().add(slider);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Director.getInstance().setAnimationRate(slider.getValue());
        });
        animationRateSlider = slider;
        // initialize type-control
        final HBox hBoxType = new HBox();
        hBoxType.getStyleClass().addAll("panel", "panel-default");
        hBoxType.setAlignment(Pos.CENTER);
        hBoxType.setPadding(new Insets(Layout.padding / 2));
        hBoxType.setSpacing(Layout.padding);
        hBoxType.setMinWidth(Layout.panelBodyWidth - 2 * Layout.padding);
        getPanelBody().getChildren().add(hBoxType);
        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Single Step");
        rb1.setToggleGroup(group);
        rb1.setUserData(true);
        RadioButton rb2 = new RadioButton("Continuous");
        rb2.setToggleGroup(group);
        rb2.setUserData(false);
        (Director.getInstance().isSingleStep() ? rb1 : rb2).setSelected(true);
        group.selectedToggleProperty().addListener((observableValue, oldValue, newValue) -> {
            if (group.getSelectedToggle() != null) {
                Director.getInstance().setSingleStep((boolean)group.getSelectedToggle().getUserData());
            }
        });
        hBoxType.getChildren().addAll(rb1, rb2);
        // initialize state-control
        Button btnPlay = new Button("Play");
        btnPlay.getStyleClass().setAll("btn", "btn-success");
        btnPlay.setOnAction((event) -> {
            Director.getInstance().playAnimation();
        });
        Button btnPause = new Button("Pause");
        btnPause.getStyleClass().setAll("btn", "btn-warning");
        btnPause.setOnAction((event) -> {
            Director.getInstance().pauseAnimation();
        });
        for (Button btn : new Button[]{btnPlay, btnPause}) {
            btn.setPrefWidth((getPanelBody().getMaxWidth() - 3 * getPanelBody().getHgap()) / 2);
            btn.setAlignment(Pos.CENTER);
            getPanelBody().getChildren().add(btn);
        }


        // prohibit partial operations while playing animation
        Director.getInstance().animationPlayingProperty().addListener((event) -> {
            animationRateSlider.setDisable(Director.getInstance().isAnimationPlaying());
        });
    }
}
