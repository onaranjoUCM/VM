package es.ucm.gdv.engine;

import java.util.List;

enum eventType {
    CLICK, RELEASE, MOVE
}

public abstract class Input {
    public abstract List<TouchEvent> getTouchEvents();
}