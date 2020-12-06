package es.ucm.gdv.engine.android;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;

public class Engine implements es.ucm.gdv.engine.Engine {
    Graphics graphics_;
    Font font_;

    public Engine(Context context) {
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
    public InputStream openInputStream(String filename) {
        return null;
    }
}
