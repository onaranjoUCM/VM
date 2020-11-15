package es.ucm.gdv.androidgame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import es.ucm.gdv.engine.android.Engine;
import es.ucm.gdv.engine.android.MySurfaceView;
import es.ucm.gdv.offtheline.OffTheLineLogic;

public class MainActivity extends AppCompatActivity {
    protected MySurfaceView _renderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Engine e = new Engine();
        _renderView = new MySurfaceView(this, e);
        setContentView(_renderView);
        e.init(_renderView);

        //OffTheLineLogic logic = new OffTheLineLogic();
        //logic.init(e);
        //logic.run();
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
}
