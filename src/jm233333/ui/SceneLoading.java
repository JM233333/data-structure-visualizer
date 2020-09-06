package jm233333.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import jm233333.Director;
import jm233333.Global;
import jm233333.io.ResourceReader;

/**
 * Class {@code SceneLoading} maintains a scene graph for the resource-loading GUI.
 * Extended from JavaFX class {@link Scene}.
 */
public class SceneLoading extends Scene {

    private static final double MIN_WAIT_TIME = 1000;
    private boolean waited = false;

    private VBox root;
    private ProgressBar progressBar;

    /**
     * Creates a {@code SceneLoading} with a specific size.
     *
     * @param root The root node of the scene graph
     * @param width The width of the scene
     * @param height The height of the scene
     */
    public SceneLoading(VBox root, double width, double height) {
        super(root, width, height);
        this.root = root;
        initialize();
    }

    /**
     * Initializes the {@code SceneLoading}.
     */
    private void initialize() {
        // initialize
        this.getStylesheets().add(this.getClass().getResource("/css/bootstrapfx.css").toExternalForm());
        this.getStylesheets().add(this.getClass().getResource("/css/_common.css").toExternalForm());
        root.setAlignment(Pos.CENTER);
        root.setSpacing(16);
        // initialize label
        final Label label = new Label("Loading Resources ...");
        label.getStyleClass().setAll("lbl", "lbl-primary");
        root.getChildren().add(label);
        // initialize progress bar
        progressBar = new ProgressBar(0);
        progressBar.getStyleClass().setAll("progress-bar", "progress-bar-primary");
        progressBar.setMinWidth(512);
        root.getChildren().add(progressBar);
        // initialize go-on button
        final Button button = new Button("START");
        button.getStyleClass().setAll("btn", "btn-primary");
        button.setAlignment(Pos.CENTER);
        button.setVisible(false);
        root.getChildren().add(button);
        // set progress listener
        final ChangeListener<Number> changeListener = (observable, oldValue, newValue) -> {
            // update progress bar
            progressBar.setProgress(ResourceReader.getInstance().getLoadingProgress());
            // check if resource-loading is finished
            if (waited && ResourceReader.getInstance().isAllLoaded()) {
                // update label
                label.getStyleClass().setAll("lbl", "lbl-success");
                label.setText("OK!");
                // update progress bar
                progressBar.getStyleClass().setAll("progress-bar", "progress-bar-success");
                // update go-on button
                button.setVisible(true);
            }
        };
        ResourceReader.getInstance().loadingProgressProperty().addListener(changeListener);
        // set button listener
        button.setOnAction((event) -> {
            ResourceReader.getInstance().loadingProgressProperty().removeListener(changeListener);
            Scene scene = new SceneMenu(new FlowPane(), 1200, 800);
            Global.getPrimaryStage().setScene(scene);
        });
        // set minimum wait time
        Timeline wait = new Timeline(new KeyFrame(Duration.millis(MIN_WAIT_TIME), (event) -> {
            waited = true;
        }));
        wait.play();
        // load resources
        Director.getInstance().addEmptyTimeline();
        Director.getInstance().createDelayInvocation(1000, (event) -> {
            ResourceReader.getInstance().getCustomResources();
        });
        Director.getInstance().playAnimation();
    }
}
