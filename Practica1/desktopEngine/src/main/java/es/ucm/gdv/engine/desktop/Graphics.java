package es.ucm.gdv.engine.desktop;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;


public class Graphics implements es.ucm.gdv.engine.Graphics {
    //Castear a Graphics2D en rotate y scale
    //NO DEBE HEREDAR DE JFRAME

    java.awt.Graphics graphics;
    JFrame window;
    Engine engine_;

    public Graphics(JFrame w, Engine engine) {
        super();
        window = w;
        engine_ = engine;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        Font f = new Font(filename, size, isBold, engine_);
        return f;
    }

    @Override
    public void clear(int r, int g, int b) {
        graphics.setColor(new Color(r, g, b));
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void translate(int x, int y) {
        graphics.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        ((Graphics2D)graphics).scale(x, y);
    }

    @Override
    public void rotate(float angle) {
        ((Graphics2D)graphics).rotate(angle);
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
    public void setFont(es.ucm.gdv.engine.Font f) {
        if (graphics != null){
            graphics.setFont(((Font)f).getFont());
        }
    }

    @Override
    public void drawText(String text, int x, int y) {
        if (graphics.getFont() != null) {
            graphics.drawString(text, (int)x, y);
        }
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