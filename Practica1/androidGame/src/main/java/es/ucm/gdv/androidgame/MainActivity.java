package es.ucm.gdv.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;

import es.ucm.gdv.engine.android.Engine;
import es.ucm.gdv.engine.android.MySurfaceView;
import es.ucm.gdv.offtheline.OffTheLineLogic;

public class MainActivity extends AppCompatActivity {
    protected MySurfaceView renderView;
    SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Engine e = new Engine();
        renderView = new MySurfaceView(this, e);
        setContentView(renderView);
        holder = renderView.getHolder();

        OffTheLineLogic logic = new OffTheLineLogic(e);

        boolean running = true;
        long lastFrameTime = System.nanoTime();
        while(running) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            logic.update(elapsedTime);

            while (!holder.getSurface().isValid());
            Canvas canvas = holder.lockCanvas();
            e.getGraphics().setCanvas(canvas);
            logic.render();
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        renderView.pause();
    }
}
