package es.ucm.gdv.engine.desktop;

import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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

    // Transform click coordinates to current resolution
    @Override
    public Vector2 transformCoordinates(Vector2 coords, Vector2 wSize) {
        float incX = 640 / wSize.x;
        float incY = 480 / wSize.y;
        coords.x -= wSize.x / 2;
        coords.y -= wSize.y / 2;
        float difW = wSize.x - 640;
        float difH = wSize.y - 480;
        if(difH < difW){
            coords.x *= incY;
            coords.y *= incY;
        } else{
            coords.x *= incX;
            coords.y *= incX;
        }

        return coords;
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
