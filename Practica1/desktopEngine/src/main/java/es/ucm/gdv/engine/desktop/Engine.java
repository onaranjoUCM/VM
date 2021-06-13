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
    int originalWidth_, originalHeight_;

    public Engine(int w, int h) {
        super();
        originalWidth_ = w;
        originalHeight_ = h;
        input_ = new es.ucm.gdv.engine.desktop.Input();
        Window window = new Window("OFF THE LINE", w, h, (es.ucm.gdv.engine.desktop.Input) input_);
        window.createBufferStrategy(2);
        strategy_ = window.getBufferStrategy();
        graphics_ = new es.ucm.gdv.engine.desktop.Graphics(window, this);
    }

    // Scales and transforms the game to fit screen resolution
    @Override
    public es.ucm.gdv.engine.Engine.Vector2 adjustToWindow(float w, float h) {
        float incX = w / originalWidth_;
        float incY = h / originalHeight_;

        // Check whether we should adjust to width or height
        if (640 * incY < w)
            graphics_.scale(incY, -incY);
        else
            graphics_.scale(incX, -incX);

        return new es.ucm.gdv.engine.Engine.Vector2(graphics_.getWidth(), graphics_.getHeight());
    }

    // Transform click coordinates to current resolution
    @Override
    public Vector2 transformCoordinates(Vector2 coords, Vector2 wSize) {
        float incX = 640 / wSize.x;
        float incY = 480 / wSize.y;
        coords.x -= wSize.x / 2;
        coords.y -= wSize.y / 2;
        float difW = wSize.x - originalWidth_;
        float difH = wSize.y - originalHeight_;
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
