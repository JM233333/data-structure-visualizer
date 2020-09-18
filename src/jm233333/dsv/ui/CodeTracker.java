package jm233333.dsv.ui;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import jm233333.dsv.Director;
import jm233333.dsv.io.ResourceReader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class {@code CodeTracker} is responsible for tracking the invoked method code of the visualized data structure.
 * Extended from JavaFX class {@link ScrollPane} only for UI layout.
 */
public class CodeTracker extends ScrollPane {

    /**
     * The flag string meaning to go to the next line.
     */
    public static final String NEXT_LINE = "__next";
    /**
     * The flag string meaning to do nothing.
     */
    public static final String REMAIN = "__remain";

    private final double padding = 16;

    private Group contentRoot;
    private TextFlow codeBoard;
    private Polygon currentLineSymbol;

    private HashMap<String, Integer> mapEntrance;
    private String currentMethod;
    private int currentLineIndex;

    /**
     * Creates a {@code CodeTracker}.
     */
    public CodeTracker() {
        // initialize
        this.getStyleClass().add("code-tracker");
        // initialize content
        contentRoot = new Group();
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setContent(contentRoot);
        // initialize the code board
        codeBoard = new TextFlow();
        codeBoard.setPadding(new Insets(padding, 3 * padding, padding, 2 * padding));
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
     * Parses the code list of the {@link jm233333.dsv.visualized.VDS} named as {@code name}.
     *
     * @param name the specified name of vds
     */
    public void parseCodeList(final String name) {
        // clear
        codeBoard.getChildren().clear();
        mapEntrance.clear();
        // process the code text
        ArrayList<String> codeList = ResourceReader.getInstance().getVdsCodeMap().get(name);
        if (codeList == null) {
            System.err.println("Native Code File Not Found for " + name);
            return;
        }
        for (int i = 0; i < codeList.size(); i ++) {
            // read a line from the source file and split it as []{code, entrance}
            String delimiter = "//#/";
            String[] args = codeList.get(i).split("\\s*" + delimiter + "\\s*", 2);
            // add code text
            Text text = new Text(args[0] + "\n");
            text.getStyleClass().add("code");
            codeBoard.getChildren().add(text);
            // set entrance
            if (args.length > 1) {
                mapEntrance.put(args[1], i);
            }
        }
    }

    /**
     * Tracks into the method of the specified name.
     *
     * @param nMethod name of the method to go
     */
    public void setCurrentMethod(String nMethod) {
        //
        if (mapEntrance.get(nMethod) == null) {
            System.err.println("Code Tracking Error for Method Tag " + nMethod);
            return;
        }
        int nLineIndex = mapEntrance.get(nMethod);
        //
        double virtualCurHeight = getLine(Math.max(0, nLineIndex - 3)).getLayoutY();
        double virtualMaxHeight = getLine(codeBoard.getChildren().size() - 1).getLayoutY() - this.heightProperty().getValue();
        double nScrollPos = virtualCurHeight / virtualMaxHeight;
        Director.getInstance().updateAnimation(0.5, this.vvalueProperty(), Math.min(1.0, Math.max(0.0, nScrollPos)));
        //
        currentMethod = nMethod;
    }

    /**
     * Gets the name of the method currently tracked in.
     *
     * @return name of the method currently tracked in
     */
    public final String getCurrentMethod() {
        return currentMethod;
    }

    /**
     * Tracks into the line of the specified entrance name in the current method.
     *
     * @param name name of the entrance
     */
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

    /**
     * Tracks into the code line of the specified line index.
     *
     * @param nLineIndex the specified line index
     */
    public void setCurrentLineIndex(Integer nLineIndex) {
        if (nLineIndex == null || nLineIndex < 0 || nLineIndex >= codeBoard.getChildren().size()) {
            System.err.println("Code Tracking Error for Line " + nLineIndex);
            return;
        }
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

    /**
     * Gets the current tracked line index.
     *
     * @return the current tracked line index
     */
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
