package jm233333.ui;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import jm233333.Director;

import java.util.*;

public class Monitor extends Pane {
    private final Group canvas;
//    private HashMap<Class, HashMap<String, ? super Object>> fieldMap;
    public Monitor() {
        canvas = new Group();
        this.getChildren().add(canvas);
    }
//    public <T> void updateField(String name, Class type, T value) {
//        fieldMap.computeIfAbsent(type, k -> new HashMap<>());
//        fieldMap.get(type).put(name, value);
//    }
    public void createVisualArray(String name, int n, Pair<String, Integer>... indexFields) {
        VisualArray visualArray = new VisualArray(name, n);
        for (Pair<String, Integer> indexField : indexFields) {
            visualArray.addIndexField(indexField.getKey(), indexField.getValue());
        }
        visualArray.setLayoutX(64);
        visualArray.setLayoutY(64);
        canvas.getChildren().add(visualArray);
    }
}
