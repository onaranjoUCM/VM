package es.ucm.gdv.engine.android;

import android.view.SurfaceView;
import java.io.InputStream;

public class Engine implements es.ucm.gdv.engine.Engine {
    Graphics graphics_;
    Font font_;

    public void init(SurfaceView surface) {
        graphics_ = new es.ucm.gdv.engine.android.Graphics();
        graphics_.init(surface);

        font_ = new es.ucm.gdv.engine.android.Font();
    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Font getFont() {
        return null;
    }

    @Override
    public InputStream openInputStream(String filename) {
        return null;
    }
}
