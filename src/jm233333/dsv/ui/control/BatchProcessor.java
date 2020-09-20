package jm233333.dsv.ui.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import jm233333.dsv.Director;
import jm233333.dsv.Global;
import jm233333.dsv.ui.PanelConsole;
import jm233333.dsv.visualized.VDSOperation;
import jm233333.util.Pair;
import jm233333.dsv.visualized.VDS;
import jm233333.dsv.visualized.VDSOperationParser;

public class BatchProcessor extends PanelConsole<FlowPane> {

    private IntegerProperty batchIndexProperty;
    private ChangeListener<Number> batchListener;

    private final TextArea textArea;

    public BatchProcessor(VDS vds, MethodTriggers methodTriggers) {
        // super
        super(new FlowPane(), "Batch Processor");
        // initialize the text area
        textArea = new TextArea();
        getPanelBody().getChildren().add(textArea);
        // initialize buttons
        initializeButtonRun(vds, methodTriggers);
        initializeButtonBuild(vds, methodTriggers);
        initializeButtonReadFile();
    }

    private void initializeButtonRun(VDS vds, MethodTriggers methodTriggers) {
        // initialize button
        Button btnRun = new Button("Run");
        btnRun.getStyleClass().setAll("btn", "btn-info");
        getPanelBody().getChildren().add(btnRun);
        // set listener
        btnRun.setOnAction((event) -> {
            // get operation list
            ArrayList<VDSOperation> operationList;
            try {
                operationList = getOperationList(vds, methodTriggers);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return;
            }
            // set listener
            System.out.println("ACTION");
            if (batchListener != null) {
                batchIndexProperty().removeListener(batchListener);
            }
            batchListener = (observable, oldValue, newValue) -> {
                // get index
                int index = batchIndexProperty().get();
                if (index == operationList.size()) {
                    batchIndexProperty().set(-1);
                }
                if (batchIndexProperty().get() == -1) {
                    return;
                }
                // get operation
                VDSOperation operation = operationList.get(index);
                // trigger the correspond method trigger
                methodTriggers.trigger(operation.getMethodName(), operation.getArguments(), (ev) -> {
                    batchIndexProperty().set(batchIndexProperty().get() + 1);
                });
                Director.getInstance().playAnimation();
            };
            batchIndexProperty().addListener(batchListener);
            batchIndexProperty().set(0);
        });
    }
    private void initializeButtonBuild(VDS vds, MethodTriggers methodTriggers) {
        // initialize button
        Button btnBuild = new Button("Build");
        btnBuild.getStyleClass().setAll("btn", "btn-info");
        getPanelBody().getChildren().add(btnBuild);
        // set listener
        btnBuild.setOnAction((event) -> {
            // get operation list
            ArrayList<VDSOperation> operationList;
            try {
                operationList = getOperationList(vds, methodTriggers);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return;
            }
            // build
            for (VDSOperation operation : operationList) {
                methodTriggers.trigger(operation.getMethodName(), operation.getArguments(), null);
                Director.getInstance().extractAnimation(event);
            }
        });

    }
    private void initializeButtonReadFile() {
        // initialize button
        Button btnReadFile = new Button("Read File");
        btnReadFile.getStyleClass().setAll("btn", "btn-danger");
        getPanelBody().getChildren().add(btnReadFile);
        // set listener
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
    }

    private ArrayList<VDSOperation> getOperationList(VDS vds, MethodTriggers methodTriggers) {
        // split input
        String[] input = textArea.getText().split("\\n+");
        // parse each line
        ArrayList<VDSOperation> operationList = new ArrayList<>();
        for (String str : input) {
            if (str.isEmpty()) {
                continue;
            }
            System.out.println("in-batch " + str);
            VDSOperation operation = VDSOperationParser.parse(vds, str);
            if (operation != null) {
                operationList.add(operation);
            } else {
                throw new IllegalArgumentException("Illegal command [" + str + "]. Process refused.");
            }
        }
        // return
        return operationList;
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

    public TextArea getTextArea() {
        return textArea;
    }
}
