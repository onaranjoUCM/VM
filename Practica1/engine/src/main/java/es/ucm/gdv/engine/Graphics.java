package es.ucm.gdv.engine;

public abstract class Graphics {
    public abstract Font newFont(String filename, int size, boolean isBold);
    public abstract void clear(int r, int g, int b);
    public abstract void translate(int x, int y);
    public abstract void scale(int x, int y);
    public abstract void rotate(float angle);
    public abstract void save();
    public abstract void restore();
    public abstract void setColor(int r, int g, int b);
    public abstract void drawLine(int x1, int y1, int x2, int y2);
    public abstract void fillRect(int x1, int y1, int x2, int y2);
    public abstract void drawText(String text, int x, int y);
    public abstract int getWidth();
    public abstract int getHeight();
}
