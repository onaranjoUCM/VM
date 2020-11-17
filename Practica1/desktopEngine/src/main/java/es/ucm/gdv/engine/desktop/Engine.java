package es.ucm.gdv.engine.desktop;

import java.awt.image.BufferStrategy;
import java.io.InputStream;

public class Engine implements es.ucm.gdv.engine.Engine {
    Graphics graphics_;
    Font font_;

    public Engine(BufferStrategy strategy) {
        super();
        graphics_ = new es.ucm.gdv.engine.desktop.Graphics();
        font_ = new es.ucm.gdv.engine.desktop.Font();
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
    public InputStream openInputStream(String filename) {
        return null;
    }
}
