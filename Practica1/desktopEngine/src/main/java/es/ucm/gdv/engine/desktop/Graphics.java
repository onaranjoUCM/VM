package es.ucm.gdv.engine.desktop;

import java.awt.Color;

import javax.swing.JFrame;

import es.ucm.gdv.engine.Font;

public class Graphics implements es.ucm.gdv.engine.Graphics {
    //Castear a Graphics2D en rotate y scale
    //NO DEBE HEREDAR DE JFRAME

    java.awt.Graphics graphics;
    JFrame window;

    float scaleX = 1;
    float scaleY = 1;
    float rotation = 0;

    public Graphics(JFrame w) {
        super();
        window = w;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int r, int g, int b) {
        graphics.clearRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void translate(int x, int y) {
        graphics.translate(x, y);
    }

    @Override
    public void scale(int x, int y) {
        scaleX = x;
        scaleY = y;
    }

    @Override
    public void rotate(float angle) {
        rotation += angle;
    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void setColor(int r, int g, int b) {
        graphics.setColor(new Color(r, g, b));
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        graphics.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void fillRect(int x1, int y1, int x2, int y2) {
        graphics.fillRect(x1, y1, x2, y2);
    }

    @Override
    public void drawText(String text, int x, int y) {

    }

    @Override
    public int getWidth() {
        return window.getWidth();
    }

    @Override
    public int getHeight() {
        return window.getHeight();
    }

    public void setGraphics(java.awt.Graphics g) {
        graphics = g;
    }
}