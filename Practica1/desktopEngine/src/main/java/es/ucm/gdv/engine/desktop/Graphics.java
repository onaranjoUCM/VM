package es.ucm.gdv.engine.desktop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;

public class Graphics implements es.ucm.gdv.engine.Graphics {
    java.awt.Graphics graphics;
    AffineTransform saveAT;
    JFrame window;
    Engine engine_;

    public Graphics(JFrame w, Engine engine) {
        super();
        window = w;
        engine_ = engine;
    }

    // Scales and transforms the game to fit screen resolution
    @Override
    public es.ucm.gdv.engine.Engine.Vector2 adjustToWindow() {
        translate(getWidth() / 2, getHeight() / 2);

        float incX = (float)getWidth() / engine_.originalWidth_;
        float incY = (float)getHeight() / engine_.originalHeight_;

        // Check whether we should adjust to width or height
        if (engine_.originalWidth_ * incY < getWidth())
            scale(incY, -incY);
        else
            scale(incX, -incX);

        return new es.ucm.gdv.engine.Engine.Vector2(getWidth(), getHeight());
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
    public void save() { saveAT = ((Graphics2D)graphics).getTransform(); }

    @Override
    public void restore() {
        ((Graphics2D)graphics).setTransform(saveAT);
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