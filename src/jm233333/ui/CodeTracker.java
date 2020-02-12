package jm233333.ui;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * The {@code CodeTracker}  is responsible for tracking the invoked method code of the visualized data structure.
 *  * Extended from JavaFX class {@code Group} only for UI layout.
 */
public class CodeTracker extends Group {

    private TextFlow codeBoard;

    private HashMap<String, Integer> mapEntrance;
    private int lineIndex;

    public CodeTracker() {
        // initialize code board
        codeBoard = new TextFlow();
        codeBoard.setPadding(new Insets(16));
        this.getChildren().add(codeBoard);
        // initialize data
        mapEntrance = new HashMap<>();
        lineIndex = -1;
        // dbg
        readF("Stack");
    }
    private void readF(final String name) {
        // clear
        codeBoard.getChildren().clear();
        mapEntrance.clear();
        // read from the source file
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/data/code/" + name + ".cpp"));
            for (int row = 0; in.ready(); row ++) {
                // read a line from the source file and split it as []{code, entrance}
                String delimiter = "//#/";
                String[] args = in.readLine().split("\\s*" + delimiter + "\\s*", 2);
                // add code text
                Text text = new Text(args[0] + "\n");
                text.setFont(Font.font(18));
                codeBoard.getChildren().add(text);
                // set entrance
                if (args.length > 1) {
                    mapEntrance.put(args[1], row);
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final Text getLine(int index) {
        return (Text)codeBoard.getChildren().get(index);
    }

    public void setLineIndex(int lineIndex) {
        if (this.lineIndex != lineIndex) {
            getLine(this.lineIndex).setFill(Color.BLACK);
            getLine(lineIndex).setFill(Color.BLUE);
            this.lineIndex = lineIndex;
        }
    }
    public int getLineIndex() {
        return lineIndex;
    }
}
