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
}
