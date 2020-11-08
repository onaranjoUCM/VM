package es.ucm.gdv.engine;

import java.io.InputStream;

public abstract class Engine {
    public abstract Graphics getGraphics();
    public abstract Font getFont();
    public abstract InputStream openInputStream(String filename);
}