package jm233333.ui.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import jm233333.Global;
import jm233333.ui.PanelConsole;

public class BatchProcessor extends PanelConsole<FlowPane> {

    private IntegerProperty batchIndexProperty;
    private ChangeListener<Number> batchListener;

    private TextArea textArea;

    public BatchProcessor(MethodTriggers methodTriggers) {
        // super
        super(new FlowPane(), "Batch Processor");
        // initialize batch properties
        batchIndexProperty().setValue(-1);
        batchListener = null;
        // initialize the text area
        textArea = new TextArea();
        getPanelBody().getChildren().add(textArea);
        // initialize the button [run]
        Button btnRun = new Button("Run");
        btnRun.getStyleClass().setAll("btn", "btn-success");
        btnRun.setOnAction((event) -> {
            // get the operation list
            String[] input = textArea.getText().split("\n"); System.out.println("run " + textArea.getText() + " ;");
            ArrayList<String> operations = new ArrayList<>();
            for (String str : input) {
                if (!str.isEmpty()) operations.add(str);

//                // check
//                if (!methodTriggers.hasMethod(name)) {
//                    System.err.println("Error : method [" + operation + "] does not existed.");
//                }
            }
            // set listener
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
                // get operation data
                String operation = operations.get(index);
                String[] optArgs = operation.split(" ");
                String name = optArgs[0];
                // get arguments
                ArrayList<Integer> arguments = new ArrayList<>();
                for (int i = 1; i < optArgs.length; i ++) {
                    try {
                        int argument = Integer.parseInt(optArgs[i]);
                        arguments.add(argument);
                    } catch (NumberFormatException e) {
                        System.out.println("Error : illegal argument in function " + name);
                        arguments.add(0); // return null;
                    }
                }
                // trigger the correspond method trigger
                methodTriggers.trigger(name, arguments, (e) -> {
                    batchIndexProperty().set(batchIndexProperty().get() + 1);
                });
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
            btn.setPrefWidth((textArea.getMaxWidth() - getPanelBody().getHgap()) / 2);
            btn.setAlignment(Pos.CENTER);
            getPanelBody().getChildren().add(btn);
        }
    }

    public TextArea getTextArea() {
        return textArea;
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
