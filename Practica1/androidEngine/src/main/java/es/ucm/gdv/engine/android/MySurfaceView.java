package es.ucm.gdv.engine.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Runnable {

    Thread _renderThread;
    SurfaceHolder _holder;
    volatile boolean _running = false;
    Paint _paint = new Paint();
    Engine engine_;

    public MySurfaceView(Context context, Engine e) {
        super(context);
        engine_ = e;
        _holder = getHolder();
    }

    public void resume() {
        if (!_running) {
            _running = true;
            _renderThread = new Thread(this);
            _renderThread.start();
        }
    }

    public void pause() {
        if (_running) {
            _running = false;
            while (true) {
                try {
                    _renderThread.join();
                    _renderThread = null;
                    break;
                } catch (InterruptedException ie) { }
            }
        }
    }

    @Override
    public void run() {
        if (_renderThread != Thread.currentThread()) {
            throw new RuntimeException("run() should not be called directly");
        }

        while(_running && getWidth() == 0);

        while(_running) {
            while (!_holder.getSurface().isValid());
            Canvas canvas = _holder.lockCanvas();
            _holder.unlockCanvasAndPost(canvas);
        }
    }
}