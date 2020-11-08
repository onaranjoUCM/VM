package es.ucm.gdv.androidengine;

import java.io.InputStream;

import es.ucm.gdv.engine.Engine;

public class MainActivity extends Engine {
    @Override
    public AndroidGraphics getGraphics() {
        return null;
    }

    @Override
    public AndroidFont getFont() {
        return null;
    }

    @Override
    public InputStream openInputStream(String filename) {
        return null;
    }
}
