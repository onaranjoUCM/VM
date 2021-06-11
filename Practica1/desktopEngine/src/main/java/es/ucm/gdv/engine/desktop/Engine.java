package es.ucm.gdv.engine.desktop;

import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.JFrame;

import es.ucm.gdv.engine.Input;

public class Engine implements es.ucm.gdv.engine.Engine {
    Graphics graphics_;
    Font font_;
    Input input_;
    BufferStrategy strategy_;

    public Engine() {
        super();
        input_ = new es.ucm.gdv.engine.desktop.Input();
        Window w = new Window("OFF THE LINE", 640, 480, (es.ucm.gdv.engine.desktop.Input) input_);
        w.createBufferStrategy(2);
        strategy_ = w.getBufferStrategy();
        graphics_ = new es.ucm.gdv.engine.desktop.Graphics(w, this);
    }

    public BufferStrategy getStrategy() {
        return strategy_;
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
    public Input getInput() {
        return input_;
    }

    @Override
    public InputStream openInputStream(String filename) throws FileNotFoundException {
        InputStream is = new FileInputStream(filename);
        return is;
    }
}
