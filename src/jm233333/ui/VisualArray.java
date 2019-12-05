package jm233333.ui;

import javafx.scene.layout.*;

import java.util.*;

public class VisualArray extends HBox {
    private String name;
    private HashMap<String, VisualArrayIndex> indexFieldMap;
    public VisualArray(String name, int n) {
        this.name = name;
        indexFieldMap = new HashMap<>();
        for (int i = 0; i < n; i ++) {
            this.getChildren().add(new VisualArrayNode());
        }
    }
    public void addIndexField(String name, int value) {
        VisualArrayIndex index = new VisualArrayIndex(name, value);
        indexFieldMap.put(name, index);
        this.getChildren().add(index);
    }
}
