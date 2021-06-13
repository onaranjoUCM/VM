package es.ucm.gdv.engine.android;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;

import es.ucm.gdv.engine.Input;

public class Engine implements es.ucm.gdv.engine.Engine {
    Graphics graphics_;
    Font font_;
    Input input_;
    int originalWidth_, originalHeight_;

    public Engine(Context context, int w, int h) {
        originalWidth_ = w;
        originalHeight_ = h;
        input_ = new es.ucm.gdv.engine.android.Input();
        AssetManager assetManager = context.getAssets();
        graphics_ = new es.ucm.gdv.engine.android.Graphics(assetManager, this);
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
        float incX = originalWidth_ / wSize.x;
        float incY = originalHeight_ / wSize.y;
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
}
