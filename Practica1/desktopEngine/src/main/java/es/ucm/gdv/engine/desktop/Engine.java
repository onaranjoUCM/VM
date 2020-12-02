package es.ucm.gdv.engine.desktop;

import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.JFrame;

public class Engine implements es.ucm.gdv.engine.Engine {
    Graphics graphics_;
    Font font_;

    public Engine(JFrame window) {
        super();
        graphics_ = new es.ucm.gdv.engine.desktop.Graphics(window, this);
        //font_ = new es.ucm.gdv.engine.desktop.Font();
    }

    @Override
    public Graphics getGraphics() {
        return graphics_;
    }

    @Override
    public Font getFont() {
        return font_;
    }

    @Override
    public InputStream openInputStream(String filename) throws FileNotFoundException {
        InputStream is = new FileInputStream(filename);
        return is;
    }
}
