package jm233333.ui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import jm233333.Director;
import jm233333.visualized.VisualizedDataStructure;

import java.lang.reflect.*;
import java.util.*;

/**
 * The {@code Controller} class is the user interface in which the user can manipulate the visualized data structure.
 * Extended from JavaFX class {@code FlowPane} only for UI layout.
 */
public class Controller extends Group {

    private HBox root;
    private PanelConsole panelMethodTrigger, panelAnimationController, panelOutputBox, panelFileReader;
    private TextFlow outputBox;
    private VisualizedDataStructure visualDS;

    /**
     * Creates a Controller with the reference to the visualized data structure.
     *
     * @param visualDS The reference to the visualized data structure
     */
    public Controller(VisualizedDataStructure visualDS) {
        // super
        super();
        // initialize root
        root = new HBox();
        root.setPadding(new Insets(16));
        root.setSpacing(16);
        //root.getStyleClass().setAll("panel", "panel-default");
        this.getChildren().add(root);
        // set ui layout data
        final double padding = 16, height = 32;
        final int columnSize = 4;
        final double panelHeadingHeight = height + 2 * padding;
        final double panelBodyHeight = (height + padding) * columnSize + padding;
        final double panelHeight = panelHeadingHeight + panelBodyHeight + 2 * padding;
        // initialize panels
        panelMethodTrigger = new PanelConsole(new FlowPane(), "Method Triggers");
        panelAnimationController = new PanelConsole(new FlowPane(), "Animation Controller");
        panelOutputBox = new PanelConsole(new ScrollPane(), "Output Box");
        panelFileReader = new PanelConsole(new ScrollPane(), "File Reader");
        for (PanelConsole panel : new PanelConsole[]{panelMethodTrigger, panelAnimationController, panelOutputBox, panelFileReader}) {
            panel.setMaxHeight(panelHeight);
            panel.getStyleClass().add("panel-primary");
            root.getChildren().add(panel);
        }
        for (PanelConsole panel : new PanelConsole[]{panelMethodTrigger, panelAnimationController}) {
            FlowPane pane = (FlowPane)panel.getPanelBody();
            pane.setOrientation(Orientation.VERTICAL);
            pane.setPrefHeight(panelBodyHeight);
            pane.setMaxHeight(panelBodyHeight);
            pane.setPadding(new Insets(padding));
            pane.setVgap(padding);
        }
//        paneAnimationController.setMinWidth(256);
        for (PanelConsole panel : new PanelConsole[]{panelOutputBox}) {
            ScrollPane pane = (ScrollPane)panel.getPanelBody();
            pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            pane.setMinWidth(256);
            pane.setMaxWidth(256);
            pane.setMaxHeight(panelBodyHeight);
            pane.setPrefHeight(panelBodyHeight);
        }
        // set reference to the interrelated visual data structure
        this.visualDS = visualDS;
        // initialize method triggers
        initializeMethodTriggers();
        // initialize animation controllers
        initializeAnimationControllers();
        // initialize output box
        outputBox = new TextFlow();
        outputBox.setMinWidth(256 - 32 + 2);
        outputBox.setMinHeight(panelBodyHeight - 32 + 2);
        outputBox.getStyleClass().addAll("bg-default");
        ((ScrollPane)panelOutputBox.getPanelBody()).setContent(outputBox);
    }

    /**
     * Initializes method triggers.
     * Finds all public methods of the visualized data structure and initializes corresponding method triggers with them.
     */
    private void initializeMethodTriggers() {
        // get panel body
        FlowPane pane = (FlowPane)panelMethodTrigger.getPanelBody();
        // find all public methods of visualDS
        Method[] methods = visualDS.getClass().getDeclaredMethods();
        // initialize method triggers
        ArrayList<MethodTrigger> methodTriggers = new ArrayList<>();
        for (Method method : methods) {
            if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                continue;
            }
            if (method.getName().equals("createVisual")) {
                continue;
            }
            if (method.getName().equals("setMode")) {
                continue;
            }
//            System.out.println(method.getName() + " : " + Arrays.toString(method.getParameterTypes()));
            MethodTrigger methodTrigger = new MethodTrigger(method);
            methodTriggers.add(methodTrigger);
            pane.getChildren().add(methodTrigger);
        }
        // initialize event listener for method triggers
        for (MethodTrigger methodTrigger : methodTriggers) {
            // set event listener
            methodTrigger.getButton().setOnAction((event) -> {
                // get method name and parameters
                String name = methodTrigger.getName();
                ArrayList<Integer> parameters = methodTrigger.getParameters();
                StringBuilder s = new StringBuilder(name);
                // debug
                for (int parameter : parameters) {
                    s.append(" ").append(parameter);
                }
                System.out.println(s);
                // invoke method
                try {
                    Class<?>[] parameterTypes = new Class<?>[parameters.size()];
                    for (int i = 0; i < parameters.size(); i ++) {
                        Class cls = parameters.get(i).getClass();
                        try {
                            Field field = cls.getDeclaredField("TYPE");
                            parameterTypes[i] = (Class<?>) field.get(parameters.get(i));
                        } catch (NoSuchFieldException e) {
                            parameterTypes[i] = cls;
                        }
                    }
                    visualDS.trackCodeMethodBeginning(name);
                    Method method = visualDS.getClass().getDeclaredMethod(name, parameterTypes);
                    method.invoke(visualDS, parameters.toArray());
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            // prohibit operating while playing animation
            Director.getInstance().animationPlayingProperty().addListener((event) -> {
                if (Director.getInstance().isAnimationPlaying()) {
                    methodTrigger.setDisable(true);
                } else {
                    methodTrigger.setDisable(false);
                }
            });
        }
    }

    /**
     * Initializes animation controllers.
     */
    private void initializeAnimationControllers() {
        // get panel body
        FlowPane pane = (FlowPane)panelAnimationController.getPanelBody();
        //
        Button btnPlay = new Button("play animation");
        btnPlay.getStyleClass().setAll("btn", "btn-success");
        btnPlay.setOnAction((event) -> {
            Director.getInstance().playAnimation();
        });
        pane.getChildren().add(btnPlay);
        Button btnPause = new Button("pause animation");
        btnPause.getStyleClass().setAll("btn", "btn-danger");
        btnPause.setOnAction((event) -> {
            Director.getInstance().pauseAnimation();
        });
        pane.getChildren().add(btnPause);
        //
        for (Button button : new Button[]{btnPlay, btnPause}) {
            //button.setAlignment(Pos.CENTER_LEFT);
        }
        //
        final Label fff = new Label("Animation Rate : ");
        pane.getChildren().add(fff);
        Slider slider = new Slider(Director.MIN_ANIMATION_RATE, Director.MAX_ANIMATION_RATE, Director.DEFAULT_ANIMATION_RATE);
        slider.setPrefWidth(160);
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickUnit(slider.getMax() - slider.getMin() - 1);
        slider.setMinorTickCount((int)(slider.getMajorTickUnit() / Director.UNIT_ANIMATION_RATE));
        slider.setBlockIncrement(Director.UNIT_ANIMATION_RATE);
        pane.getChildren().add(slider);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Director.getInstance().setAnimationRate(slider.getValue());
        });
        //
        final Label fff2 = new Label("Action Type : ");
        pane.getChildren().add(fff2);
        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Single Step");
        rb1.setToggleGroup(group);
        rb1.setUserData(true);
        RadioButton rb2 = new RadioButton("Continuous");
        rb2.setToggleGroup(group);
        rb2.setUserData(false);
        (Director.getInstance().isSingleStep() ? rb1 : rb2).setSelected(true);
        pane.getChildren().addAll(rb1, rb2);
        group.selectedToggleProperty().addListener((observableValue, oldValue, newValue) -> {
            if (group.getSelectedToggle() != null) {
                Director.getInstance().setSingleStep((boolean)group.getSelectedToggle().getUserData());
            }
        });
    }

    /**
     * Gets the output box.
     */
    public final TextFlow getOutputBox() {
        return outputBox;
    }
}
