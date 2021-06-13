package es.ucm.gdv.engine;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface Engine {
    Graphics getGraphics();
    Font getFont();
    Input getInput();
    InputStream openInputStream(String filename) throws FileNotFoundException;
    Vector2 transformCoordinates(Vector2 coord, Vector2 wSize);

    class Vector2 {
        public float x;
        public float y;

        public Vector2(float x_, float y_) {
            x = x_;
            y = y_;
        }

        public void set(float x_, float y_) {
            x = x_;
            y = y_;
        }

        public void set(Vector2 d) {
            x = d.x;
            y = d.y;
        }
    }
}