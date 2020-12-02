package es.ucm.gdv.engine;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface Engine {
    Graphics getGraphics();
    Font getFont();
    InputStream openInputStream(String filename) throws FileNotFoundException;
}