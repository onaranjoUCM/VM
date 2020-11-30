package es.ucm.gdv.engine;

import java.util.ArrayList;
import java.util.List;

public class Input {
    public ArrayList<TouchEvent> events_;
    public Input(){
        events_ = new ArrayList<TouchEvent>();
    }
    public void addEvent(TouchEvent e) {
        events_.add(e);
    }
    public ArrayList<TouchEvent> getEvents() {
        return events_;
    }
}