package es.ucm.gdv.engine.android;

import android.view.MotionEvent;
import android.view.View;

import es.ucm.gdv.engine.TouchEvent;

public class Input extends es.ucm.gdv.engine.Input implements View.OnTouchListener {
    private TouchEvent event;

    public Input() {
        super();
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        event = new TouchEvent();
        float posX = e.getX();
        float posY = e.getY();
        event.init((int)posX, (int)posY, 1, 0);
        addEvent(event);
        return false;
    }
}
