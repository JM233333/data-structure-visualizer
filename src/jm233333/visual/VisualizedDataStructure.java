package jm233333.visual;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import jm233333.ui.Monitor;

import java.lang.reflect.*;

public abstract class VisualizedDataStructure {

    private Monitor monitor = null;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
//        Field[] fs = this.getClass().getDeclaredFields();
//        for (Field field : fs) {
//            Class fieldType = field.getType();
//            String typeName = fieldType.getName();
//            String fieldName = field.getName();
//            System.out.println(typeName + " " + fieldName);
//        }
    }
    public final Monitor getMonitor() {
        return monitor;
    }

    public static Rectangle getEmptyBox(double width, double height) {
        return new Rectangle(width, height, Color.TRANSPARENT);
    }

    protected void createVisualArray(String name, int n, Pair<String, Integer>... indexFields) {
        monitor.createVisualArray(name, n, indexFields);
    }
}
