package jm233333.ui;

public interface Layout {
    double padding = 16, height = 36;
    int columnSize = 4;
    double panelHeadingHeight = height + 2 * padding;
    double panelBodyWidth = 256 + 16;
    double panelBodyHeight = (height + padding) * columnSize + padding;
    double panelHeight = panelHeadingHeight + panelBodyHeight + 2 * padding;
}
