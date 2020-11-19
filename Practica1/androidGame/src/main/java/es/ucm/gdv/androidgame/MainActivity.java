package es.ucm.gdv.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import es.ucm.gdv.engine.android.Engine;
import es.ucm.gdv.offtheline.OffTheLineLogic;

public class MainActivity extends AppCompatActivity {
    protected MySurfaceView _renderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _renderView = new MySurfaceView(this);
        setContentView(_renderView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _renderView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        _renderView.pause();
    }

    class MySurfaceView extends SurfaceView implements Runnable {
        Thread _renderThread;
        SurfaceHolder _holder;
        volatile boolean _running = false;
        OffTheLineLogic _logic;
        Engine _engine;

        public MySurfaceView(Context context) {
            super(context);
            _holder = getHolder();
            _engine = new Engine();
            _logic = new OffTheLineLogic(_engine);
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
                    } catch (InterruptedException ie) {
                    }
                }
            }

        }

        @Override
        public void run() {

            if (_renderThread != Thread.currentThread()) {
                throw new RuntimeException("run() should not be called directly");
            }

            while(_running && getWidth() == 0);

            long lastFrameTime = System.nanoTime();
            while(_running) {
                long currentTime = System.nanoTime();
                long nanoElapsedTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                double elapsedTime = (double) nanoElapsedTime / 1.0E9;
                _logic.update(elapsedTime);

                while (!_holder.getSurface().isValid());
                Canvas canvas = _holder.lockCanvas();
                _engine.getGraphics().setCanvas(canvas);
                _logic.render();
                _holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
