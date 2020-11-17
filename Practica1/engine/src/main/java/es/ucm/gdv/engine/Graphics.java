package es.ucm.gdv.engine;

public interface Graphics {
    Font newFont(String filename, int size, boolean isBold);
    void clear(int r, int g, int b);
    void translate(int x, int y);
    void scale(int x, int y);
    void rotate(float angle);
    void save();
    void restore();
    void setColor(int r, int g, int b);
    void drawLine(int x1, int y1, int x2, int y2);
    void fillRect(int x1, int y1, int x2, int y2);
    void drawText(String text, int x, int y);
    int getWidth();
    int getHeight();
}
