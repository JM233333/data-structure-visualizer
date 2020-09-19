package jm233333.dsv.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import jm233333.dsv.Director;
import jm233333.dsv.io.ResourceReader;
import jm233333.util.Pair;

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

    private HashMap<String, Integer> mapEntrance = new HashMap<>();
    private int currentLineIndex = -1;
    private Stack<Pair<String, Integer>> stackInvocation = new Stack<>();
    private int cachedCallLineIndex = -1;

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
    }

    /**
     * Parses the code list of the {@link jm233333.dsv.visualized.VDS} named as {@code name}.
     *
     * @param name the specified name of vds
     */
    public void parseCodeList(String name) {
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
     * Caches the line index while calling the latest method.
     */
    public void cacheCallLineIndex() {
        cachedCallLineIndex = currentLineIndex;
    }
    /**
     * Calls a method (e.g. changes the current method).
     *
     * @param nameMethod name of the called method
     */
    public void callMethod(String nameMethod) {
        if (nameMethod != null && mapEntrance.get(nameMethod) == null) {
            System.err.println("Error : Undefined method " + nameMethod + " (in code tracker).");
            return;
        }
        stackInvocation.push(new Pair<>(nameMethod, cachedCallLineIndex));
        cachedCallLineIndex = -1;
    }
    /**
     * Returns from the current method.
     */
    public void returnMethod() {
        setCurrentLineIndex(stackInvocation.peek().second);
        stackInvocation.pop();
    }

    /**
     * Gets the name of the method currently tracked in.
     *
     * @return name of the method currently tracked in
     */
    public String getCurrentMethod() {
        return (stackInvocation.isEmpty() ? null : stackInvocation.peek().first);
    }
    public int getLastCallEntrance() {
        return (stackInvocation.isEmpty() ? -1 : stackInvocation.peek().second);
    }

    /**
     * Tracks into the line of the specified entrance name in the current method.
     *
     * @param name name of the entrance
     */
    public void gotoEntrance(String name) {
        // check eid REMAIN
        if (name.equals(REMAIN)) {
            return;
        }
        // get next index
        Integer index = (name.equals(NEXT_LINE) ? currentLineIndex + 1 : mapEntrance.get(name));
        // check existence
        if (index == null || index < 0 || index >= codeBoard.getChildren().size()) {
            System.err.println("Code Tracking Error for entrance name " + name);
            return;
        }
        // set index
        setCurrentLineIndex(index);
    }

    /**
     * Tracks into the code line of the specified line index.
     *
     * @param index the specified line index
     */
    public void setCurrentLineIndex(int index) {
        if (currentLineIndex != index) {
            // set line index
            final Text prvCurrentLine = getCurrentLine();
            currentLineIndex = index;
            final Text newCurrentLine = getCurrentLine();
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
            // adjust viewport
            if (index != -1) {
                double virtualCurHeight = getLine(Math.max(0, index - 7)).getLayoutY();
                double virtualMaxHeight = getLine(codeBoard.getChildren().size() - 1).getLayoutY() - this.heightProperty().getValue();
                double nVvalue = Math.min(1.0, Math.max(0.0, virtualCurHeight / virtualMaxHeight));
                Director.getInstance().updateAnimation(0.5, this.vvalueProperty(), Math.min(1.0, Math.max(0.0, nVvalue)));
            }
            // delay
            Director.getInstance().createDelayInvocation(0.5, null);
        }
    }

    public int getCurrentLineIndex() {
        return currentLineIndex;
    }

    private Text getLine(int index) {
        return (Text)codeBoard.getChildren().get(index);
    }
    private Text getCurrentLine() {
        if (currentLineIndex == -1) {
            return null;
        }
        return getLine(currentLineIndex);
    }
}
