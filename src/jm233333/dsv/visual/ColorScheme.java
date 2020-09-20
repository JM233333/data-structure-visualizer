package jm233333.dsv.visual;

import javafx.scene.paint.Color;

public class ColorScheme {

    public static final ColorScheme DEFAULT = new ColorScheme(Color.BLACK, Color.WHITE, Color.BLACK);
    public static final ColorScheme EMPHASIZED = new ColorScheme(Color.RED, Color.WHITE, Color.RED);
    public static final ColorScheme HIGHLIGHT = new ColorScheme(Color.WHITE, Color.ORANGE, Color.WHITE);
    public static final ColorScheme TRACED = new ColorScheme(Color.LIGHTSLATEGRAY, Color.DARKSEAGREEN, Color.LIGHTGRAY);
    public static final ColorScheme MARKED = new ColorScheme(Color.MIDNIGHTBLUE, Color.POWDERBLUE, Color.BLACK);

    private final Color colorBoard;
    private final Color colorBox;
    private final Color colorText;

    public ColorScheme(Color colorBoard, Color colorBox, Color colorText) {
        this.colorBoard = colorBoard;
        this.colorBox = colorBox;
        this.colorText = colorText;
    }
    public Color getColorBoard() {
        return colorBoard;
    }
    public Color getColorBox() {
        return colorBox;
    }
    public Color getColorText() {
        return colorText;
    }
}
