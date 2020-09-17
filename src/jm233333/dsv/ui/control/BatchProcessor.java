package jm233333.dsv.ui.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import jm233333.dsv.Director;
import jm233333.dsv.Global;
import jm233333.dsv.ui.PanelConsole;
import jm233333.dsv.util.Pair;
import jm233333.dsv.visualized.VDS;
import jm233333.dsv.visualized.VDSOptParser;

public class BatchProcessor extends PanelConsole<FlowPane> {

    private TextArea textArea;

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
            // batch process
            try {
                batchProcess(vds, methodTriggers);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return;
            }
            // play animation
            Director.getInstance().playAnimation();
        });
    }
    private void initializeButtonBuild(VDS vds, MethodTriggers methodTriggers) {
        // initialize button
        Button btnBuild = new Button("Build");
        btnBuild.getStyleClass().setAll("btn", "btn-info");
        getPanelBody().getChildren().add(btnBuild);
        // set listener
        btnBuild.setOnAction((event) -> {
            // batch process
            try {
                batchProcess(vds, methodTriggers);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return;
            }
            // extract animation results
            Director.getInstance().extractAnimation();
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

    private void batchProcess(VDS vds, MethodTriggers methodTriggers) {
        // get the operation list
        String[] input = textArea.getText().split("\\n+");
        ArrayList<Pair<String, ArrayList<Integer>>> operationList = new ArrayList<>();
        for (String str : input) {
            System.out.println("in-batch " + str);
            Pair<String, ArrayList<Integer>> operation = VDSOptParser.parse(vds, str);
            if (operation != null) {
                operationList.add(operation);
            } else {
                throw new IllegalArgumentException("Illegal command [" + str + "]. Process refused.");
            }
        }
        // trigger the methods in order
        for (Pair<String, ArrayList<Integer>> operation : operationList) {
            methodTriggers.trigger(operation.first, operation.second, false);
        }
    }

    public TextArea getTextArea() {
        return textArea;
    }
}
