package es.ucm.gdv.engine.desktop;

import java.awt.Color;
import java.awt.image.BufferStrategy;

import es.ucm.gdv.engine.Font;

public class Graphics implements es.ucm.gdv.engine.Graphics {
    //Castear a Graphics2D en rotate y scale
    //NO DEBE HEREDAR DE JFRAME

    java.awt.Graphics graphics;

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int r, int g, int b) {

    }

    @Override
    public void translate(int x, int y) {

    }

    @Override
    public void scale(int x, int y) {

    }

    @Override
    public void rotate(float angle) {

    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void setColor(int r, int g, int b) {

    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        graphics.setColor(Color.BLACK);
        graphics.drawLine(x1, y1, x2, y2);
        graphics.fillRect(100, 100, 200, 200);
    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2) {

    }

    @Override
    public void drawText(String text, int x, int y) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    public void setGraphics(java.awt.Graphics g) {
        graphics = g;
    }
}