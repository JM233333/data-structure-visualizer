package jm233333.ui;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.FileChooser;

import javafx.stage.Stage;
import jm233333.Director;
import jm233333.Global;
import jm233333.visualized.Mode;
import jm233333.visualized.VDS;

/**
 * Class {@code Controller} is the UI component to manipulate the {@link VDS}.
 * Extended from JavaFX class {@link ScrollPane} only for UI layout.
 */
public class Controller extends ScrollPane {

    private final double padding = 16, height = 36;
    private final int columnSize = 4;
    private final double panelHeadingHeight = height + 2 * padding;
    private final double panelBodyWidth = 256 + 16;
    private final double panelBodyHeight = (height + padding) * columnSize + padding;
    private final double panelHeight = panelHeadingHeight + panelBodyHeight + 2 * padding;

    private HBox root;
    private PanelConsole panelMethodTrigger, panelAnimationController, panelOutputBox, panelBatchProcessor;
    private Slider animationRateSlider;
    private TextFlow outputBox;

    private IntegerProperty batchIndexProperty;
    private ChangeListener<Number> batchListener;

    private VDS vds;

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
        root.setPadding(new Insets(padding));
        root.setSpacing(padding);
        this.setContent(root);
        // initialize panels
        panelMethodTrigger = new PanelConsole(new FlowPane(), "Method Triggers");
        panelAnimationController = new PanelConsole(new FlowPane(), "Animation Controller");
        panelOutputBox = new PanelConsole(new ScrollPane(), "Output Box");
        panelBatchProcessor = new PanelConsole(new FlowPane(), "Batch Processor");
        for (PanelConsole panel : new PanelConsole[]{panelMethodTrigger, panelAnimationController, panelOutputBox, panelBatchProcessor}) {
            panel.getStyleClass().add("panel-primary");
            panel.setMaxHeight(panelHeight);
            root.getChildren().add(panel);
        }
        // set reference to the interrelated visual data structure
        this.vds = vds;
        // initialize sub interfaces
        initializeMethodTriggers();
        initializeAnimationControllers();
        initializeOutputBox();
        initializeBatchProcessor();
        // prohibit partial operations while playing animation
        Director.getInstance().animationPlayingProperty().addListener((event) -> {
            for (PanelConsole panel : new PanelConsole[]{panelMethodTrigger, panelBatchProcessor}) {
                panel.setDisable(Director.getInstance().isAnimationPlaying());
            }
            animationRateSlider.setDisable(Director.getInstance().isAnimationPlaying());
        });
        // initialize width property
        final PanelConsole[] panels = new PanelConsole[]{panelMethodTrigger, panelAnimationController, panelOutputBox, panelBatchProcessor};
//        for (PanelConsole panel : panels) {
        panelBatchProcessor.widthProperty().addListener((obs, ov, nv) -> {
            // get the sum of width
            double sumWidth = 5 * padding;
            for (PanelConsole iPanel : panels) {
                sumWidth += iPanel.widthProperty().getValue();
            }
            root.setMinWidth(sumWidth);
            final double realWidth = sumWidth + padding + 6;
            //
            Global.getPrimaryStage().widthProperty().addListener((observable, oldValue, newValue) -> {
                double height = ((double)newValue >= realWidth ? panelHeight - 10 : panelHeight + 6);
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
        // initialize batch properties
        batchIndexProperty().setValue(-1);
        batchListener = null;
    }

    private void initializeMethodTriggers() {
        // initialize panel body
        FlowPane pane = (FlowPane)panelMethodTrigger.getPanelBody();
        pane.setOrientation(Orientation.VERTICAL);
        pane.setPadding(new Insets(padding));
        pane.setVgap(padding);
        pane.setMinHeight(panelBodyHeight);
        pane.setMaxHeight(panelBodyHeight);
        pane.setPrefHeight(panelBodyHeight);
        // find all public methods of visualDS
        Method[] methods = vds.getClass().getDeclaredMethods();
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
                // get parameter type list
                Class<?>[] parameterTypes = new Class<?>[parameters.size()];
                for (int i = 0; i < parameters.size(); i ++) {
                    Class cls = parameters.get(i).getClass();
                    try {
                        Field field = cls.getDeclaredField("TYPE");
                        parameterTypes[i] = (Class<?>) field.get(parameters.get(i));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        parameterTypes[i] = cls;
                    }
                }
                // invoke method
                vds.trackCodeMethodBeginning(name);
                try {
                    Method method = vds.getClass().getDeclaredMethod(name, parameterTypes);
                    method.invoke(vds, parameters.toArray());
                    Director.getInstance().playAnimation();
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }
        // initialize the mode selector
        VBox modeSelector = new VBox();
        modeSelector.getStyleClass().addAll("panel", "panel-default");
        modeSelector.setPadding(new Insets(padding / 2));
        modeSelector.setSpacing(padding);
        modeSelector.setAlignment(Pos.CENTER_LEFT);
        pane.getChildren().add(modeSelector);
        Class<? extends Mode> modeClass = vds.getModeClass();
        if (modeClass != null) {
            final Label label = new Label("Select Mode : ");
            modeSelector.getChildren().add(label);
            final ToggleGroup toggleGroup = new ToggleGroup();
            Mode[] modes = modeClass.getEnumConstants();
            int n = modes.length;
            if (n > 0) {
                RadioButton[] radioButtons = new RadioButton[n];
                for (int i = 0; i < n; i++) {
                    radioButtons[i] = new RadioButton(modes[i].toString());
                    radioButtons[i].setToggleGroup(toggleGroup);
                    radioButtons[i].setUserData(modes[i]);
                    modeSelector.getChildren().add(radioButtons[i]);
                }
                radioButtons[0].setSelected(true);
                toggleGroup.selectedToggleProperty().addListener((observableValue, oldValue, newValue) -> {
                    if (toggleGroup.getSelectedToggle() != null) {
                        vds.setMode((Mode)toggleGroup.getSelectedToggle().getUserData());
                    }
                });
            }
        }
    }
    private void initializeAnimationControllers() {
        // initialize panel body
        FlowPane pane = (FlowPane)panelAnimationController.getPanelBody();
        pane.setOrientation(Orientation.HORIZONTAL);
        pane.setPadding(new Insets(padding));
        pane.setHgap(padding);
        pane.setVgap(padding);
        pane.setMinWidth(panelBodyWidth);
        pane.setMaxWidth(panelBodyWidth);
        pane.setPrefWidth(panelBodyWidth);
        pane.setMinHeight(panelBodyHeight);
        pane.setMaxHeight(panelBodyHeight);
        pane.setPrefHeight(panelBodyHeight);
        // initialize rate-control
        final VBox vBoxRate = new VBox();
        vBoxRate.getStyleClass().addAll("panel", "panel-default");
        vBoxRate.setPadding(new Insets(padding / 2));
        vBoxRate.setSpacing(padding);
        pane.getChildren().add(vBoxRate);
        final Label labelRate = new Label("Animation Rate : ");
        vBoxRate.getChildren().add(labelRate);
        Slider slider = new Slider(Director.MIN_ANIMATION_RATE, Director.MAX_ANIMATION_RATE, Director.getInstance().getAnimationRate());
        slider.setPrefWidth(panelBodyWidth - 3 * padding - 2);
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
        hBoxType.setPadding(new Insets(padding / 2));
        hBoxType.setSpacing(padding);
        hBoxType.setMinWidth(panelBodyWidth - 2 * padding);
        pane.getChildren().add(hBoxType);
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
            btn.setPrefWidth((pane.getMaxWidth() - 3 * pane.getHgap()) / 2);
            btn.setAlignment(Pos.CENTER);
            pane.getChildren().add(btn);
        }
    }
    private void initializeOutputBox() {
        // initialize panel body
        ScrollPane pane = (ScrollPane)panelOutputBox.getPanelBody();
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setMinWidth(panelBodyWidth);
        pane.setMaxWidth(panelBodyWidth);
        pane.setPrefWidth(panelBodyWidth);
        pane.setMinHeight(panelBodyHeight);
        pane.setMaxHeight(panelBodyHeight);
        pane.setPrefHeight(panelBodyHeight);
        // initialize output box
        outputBox = new TextFlow();
        outputBox.setMinWidth(panelBodyWidth - 2 * padding + 2);
        outputBox.setMinHeight(panelBodyHeight - 2 * padding + 2);
        outputBox.getStyleClass().addAll("bg-default");
        ((ScrollPane)panelOutputBox.getPanelBody()).setContent(outputBox);
        // bind with the visualized data structure
        vds.setOutputBox(outputBox);
    }
    private void initializeBatchProcessor() {
        // get panel body
        FlowPane pane = (FlowPane)panelBatchProcessor.getPanelBody();
        pane.setHgap(padding);
        pane.setVgap(padding);
        pane.setMinWidth(panelBodyWidth);
        pane.setMaxWidth(panelBodyWidth);
        pane.setPrefWidth(panelBodyWidth);
        //
        TextArea textArea = new TextArea();
        textArea.setMinWidth(pane.getMinWidth() - 32 + 2);
        textArea.setMaxWidth(pane.getMaxWidth() - 32 + 2);
        textArea.setMinHeight(outputBox.getMinHeight() - 48);
        textArea.setMaxHeight(outputBox.getMinHeight() - 48);
        pane.getChildren().add(textArea);
        //
        Button btnRun = new Button("Run");
        btnRun.getStyleClass().setAll("btn", "btn-success");
        btnRun.setOnAction((event) -> {
            // get the operation list
            String[] input = textArea.getText().split("\n"); System.out.println("run " + textArea.getText() + " ;");
            ArrayList<String> operations = new ArrayList<>();
            for (String str : input) {
                if (!str.isEmpty()) operations.add(str);
            }
            // set listener
//            if (batchListener != null) {
//                batchIndexProperty().removeListener(batchListener);
//            }
            System.out.println("ACTION");
            if (batchListener != null) {
                batchIndexProperty().removeListener(batchListener);
            }
            batchListener = (observable, oldValue, newValue) -> {
                // get index
                int index = batchIndexProperty().get();
                if (index == operations.size()) {
                    batchIndexProperty().set(-1);
                }
                if (batchIndexProperty().get() == -1) {
                    return;
                }
                // get operation string
                String operation = operations.get(index);
                // get arguments
                String[] optParam = operation.split(" ");
                String name = optParam[0];
                ArrayList<Integer> arguments = new ArrayList<>();
                for (int i = 1; i < optParam.length; i ++) {
                    try {
                        int argument = Integer.parseInt(optParam[i]);
                        arguments.add(argument);
                    } catch (NumberFormatException e) {
                        System.out.println("Error : illegal argument in function " + name);
                        arguments.add(0); // return null;
                    }
                }
                // get the parameter type list
                Class<?>[] parameterTypes = new Class<?>[arguments.size()];
                for (int i = 0; i < arguments.size(); i ++) {
                    Class cls = arguments.get(i).getClass();
                    try {
                        Field field = cls.getDeclaredField("TYPE");
                        parameterTypes[i] = (Class<?>) field.get(arguments.get(i));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        parameterTypes[i] = cls;
                    }
                }
                // invoke method of VDS
                try {
                    vds.trackCodeMethodBeginning(name);
                    Method method = vds.getClass().getDeclaredMethod(name, parameterTypes);
                    method.invoke(vds, arguments.toArray());
                    Director.getInstance().getLastTimeline().setOnFinished((e) -> {
                        batchIndexProperty().set(batchIndexProperty().get() + 1);
                    });
                    Director.getInstance().playAnimation();
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            };
            batchIndexProperty().addListener(batchListener);
            batchIndexProperty().set(0);
        });
        //
        Button btnReadFile = new Button("Read File");
        btnReadFile.getStyleClass().setAll("btn", "btn-info");
        btnReadFile.setAlignment(Pos.CENTER);
        btnReadFile.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Any Text File : ");
            fileChooser.setInitialDirectory(new File("."));
            File result = fileChooser.showOpenDialog(Global.getPrimaryStage());
            if (result != null) {
                System.out.print(result.getAbsolutePath());
                StringBuilder str = new StringBuilder();
                try {
                    BufferedReader in = new BufferedReader(new FileReader(result.getAbsolutePath()));
                    while (in.ready()) {
                        str.append(in.readLine());
                        str.append('\n');
                    }
                    in.close();
                    textArea.setText(str.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        for (Button btn : new Button[]{btnRun, btnReadFile}) {
            btn.setPrefWidth((textArea.getMaxWidth() - pane.getHgap()) / 2);
            btn.setAlignment(Pos.CENTER);
            pane.getChildren().add(btn);
        }
    }

    /**
     * An {@code IntegerProperty} that represents the current executing index in a series of batch operations.
     */
    public final IntegerProperty batchIndexProperty() {
        if (batchIndexProperty == null) {
            batchIndexProperty = new IntegerPropertyBase(-1) {
                @Override
                public Object getBean() {
                    return this;
                }
                @Override
                public String getName() {
                    return "batchIndex";
                }
            };
        }
        return batchIndexProperty;
    }

}
