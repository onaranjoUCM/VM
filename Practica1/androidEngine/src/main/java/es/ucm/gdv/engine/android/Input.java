package es.ucm.gdv.engine.android;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import es.ucm.gdv.engine.TouchEvent;

public class Input implements View.OnTouchListener, es.ucm.gdv.engine.Input {
    private ArrayList<TouchEvent> events_;
    private TouchEvent event;

    public Input() {
        super();
        events_ = new ArrayList<TouchEvent>();
    }

    @Override
    public synchronized void addEvent(TouchEvent e) {
        events_.add(e);
    }

    @Override
    public ArrayList<TouchEvent> getEvents() {
        return events_;
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
