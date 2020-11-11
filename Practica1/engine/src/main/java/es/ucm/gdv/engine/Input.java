package es.ucm.gdv.engine;

import java.util.List;

enum eventType {
    CLICK, RELEASE, MOVE
}

public interface Input {
    List<TouchEvent> getTouchEvents();
}