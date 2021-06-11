package es.ucm.gdv.engine;

import java.util.ArrayList;
import java.util.List;

public interface Input {
    void addEvent(TouchEvent e);
    ArrayList<TouchEvent> getEvents();
}