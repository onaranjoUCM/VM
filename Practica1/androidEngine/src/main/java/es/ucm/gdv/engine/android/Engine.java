package es.ucm.gdv.engine.android;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;

import es.ucm.gdv.engine.Input;

public class Engine implements es.ucm.gdv.engine.Engine {
    Graphics graphics_;
    Font font_;
    Input input_;

    public Engine(Context context) {
        input_ = new es.ucm.gdv.engine.android.Input();
        AssetManager assetManager = context.getAssets();
        graphics_ = new es.ucm.gdv.engine.android.Graphics(assetManager);
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
    public InputStream openInputStream(String filename) {
        return null;
    }

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
}
