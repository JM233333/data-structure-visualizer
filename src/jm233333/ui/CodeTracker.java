package jm233333.ui;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import jm233333.Director;

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
        // initialize CSS
        initializeCSS();
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
        currentLineSymbol.setLayoutX(8);
        contentRoot.getChildren().add(currentLineSymbol);
        // initialize data
        mapEntrance = new HashMap<>();
        currentMethod = "";
        currentLineIndex = -1;
    }

    /**
     * Initializes CSS.
     */
    private void initializeCSS() {
        Class cls = this.getClass();
        String fullName = cls.getName();
        String lastName = fullName.substring(fullName.lastIndexOf('.') + 1);
        this.getStylesheets().add(cls.getResource("/jm233333/css/" + lastName + ".css").toExternalForm());
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
                text.getStyleClass().add("code");
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
            // update current line index
            final Text prvCurrentLine = getCurrentLine();
            currentLineIndex = nLineIndex;
            final Text newCurrentLine = getCurrentLine();
            // create animation
            double ny = (newCurrentLine == null ? 8 : newCurrentLine.getLayoutY() + 4);
            Director.getInstance().createAnimation(0.5, currentLineSymbol.layoutYProperty(), ny);
            Director.getInstance().getLastTimeline().setOnFinished((event) -> {
                if (prvCurrentLine != null) {
                    prvCurrentLine.setFill(Color.BLACK);
                    prvCurrentLine.setStyle("-fx-font-weight: normal;");
                }
                if (newCurrentLine != null) {
                    newCurrentLine.setFill(Color.BLUE);
                    newCurrentLine.setStyle("-fx-font-weight: bold;");
                }
            });
            Director.getInstance().createAnimation(0.5, currentLineSymbol.opacityProperty(), 1);
        }
    }
    public int getCurrentLineIndex() {
        return currentLineIndex;
    }

    private final Text getLine(int index) {
        return (Text)codeBoard.getChildren().get(index);
    }
    private final Text getCurrentLine() {
        if (currentLineIndex == -1) {
            return null;
        }
        return getLine(currentLineIndex);
    }
}
