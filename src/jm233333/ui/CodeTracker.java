package jm233333.ui;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import jm233333.visual.Visual;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * The {@code CodeTracker}  is responsible for tracking the invoked method code of the visualized data structure.
 *  * Extended from JavaFX class {@code Group} only for UI layout.
 */
public class CodeTracker extends ScrollPane {

    public static final String NEXT_LINE = "__next";
    public static final String REMAIN = "__remain";

    private Group contentRoot;
    private TextFlow codeBoard;
    private Polygon currentLineSymbol;

    private HashMap<String, Integer> mapEntrance;
    private String currentMethod;
    private int currentLineIndex;

    public CodeTracker() {
        // initialize content
        contentRoot = new Group();
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setContent(contentRoot);
        // initialize the code board
        codeBoard = new TextFlow();
        codeBoard.setPadding(new Insets(16, 32, 16, 32));
        contentRoot.getChildren().add(codeBoard);
        // initialize the current-line symbol
        currentLineSymbol = new Polygon(0, 0, 16, 8, 0, 16);
        currentLineSymbol.setFill(Color.ORANGE);
        currentLineSymbol.setOpacity(0);
        currentLineSymbol.setLayoutX(8);
        contentRoot.getChildren().add(currentLineSymbol);
        // initialize data
        mapEntrance = new HashMap<>();
        currentMethod = "";
        currentLineIndex = -1;
    }
    public void readFile(final String name) {
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
        // debug
        for (HashMap.Entry<String, Integer> entry : mapEntrance.entrySet()) {
            Text text = new Text(" > " + entry.getKey() + " " + entry.getValue() + "\n");
            text.setFont(Font.font(18));
            codeBoard.getChildren().add(text);
        }
    }

    public void resetCurrentMethod() {
        currentMethod = null;
    }
    public void setCurrentMethod(String nMethod) {
        currentMethod = nMethod;
        setCurrentLineIndex(mapEntrance.get(nMethod));
        gotoEntrance(NEXT_LINE);
    }
    public final String getCurrentMethod() {
        return currentMethod;
    }

    public void gotoEntrance(String name) {
        if (name.equals(REMAIN)) {
            return;
        }
        if (name.equals(NEXT_LINE)) {
            setCurrentLineIndex(currentLineIndex + 1);
        } else {
            setCurrentLineIndex(mapEntrance.get(name));
        }
    }
    public void setCurrentLineIndex(int nLineIndex) {
        if (currentLineIndex != nLineIndex) {
            if (currentLineIndex == -1) {
                Visual.createAnimation(1.0, currentLineSymbol.opacityProperty(), 1);
            } else {
                Visual.createAnimation(1.0, getCurrentLine().fillProperty(), Color.BLACK);
            }
            currentLineIndex = nLineIndex;
            Visual.updateAnimation(0.5, currentLineSymbol.layoutYProperty(), getCurrentLine().getLayoutY() + 4);
            if (currentLineIndex == -1) {
                Visual.updateAnimation(1.0, currentLineSymbol.opacityProperty(), 0);
            } else {
                Visual.updateAnimation(0.5, getCurrentLine().fillProperty(), Color.BLUE);
            }
            // pause debug
            if (currentLineIndex == -1) {
                Visual.createAnimation(0.2, currentLineSymbol.opacityProperty(), 0);
            } else {
                Visual.createAnimation(0.2, currentLineSymbol.opacityProperty(), 1);
            }
        }
    }
    public int getCurrentLineIndex() {
        return currentLineIndex;
    }

    public final Text getLine(int index) {
        return (Text)codeBoard.getChildren().get(index);
    }
    public final Text getCurrentLine() {
        if (currentLineIndex == -1) {
            return null;
        }
        return getLine(currentLineIndex);
    }
}
