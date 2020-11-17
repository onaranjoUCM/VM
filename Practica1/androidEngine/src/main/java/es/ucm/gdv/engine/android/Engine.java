package es.ucm.gdv.engine.android;

import java.io.InputStream;

public class Engine implements es.ucm.gdv.engine.Engine {
    Graphics graphics_;
    Font font_;

    public Engine() {
        graphics_ = new es.ucm.gdv.engine.android.Graphics();
        font_ = new es.ucm.gdv.engine.android.Font();
    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Font getFont() {
        return null;
    }

    @Override
    public InputStream openInputStream(String filename) {
        return null;
    }
}
