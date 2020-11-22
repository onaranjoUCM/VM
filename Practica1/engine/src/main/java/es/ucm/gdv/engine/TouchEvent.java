package es.ucm.gdv.engine;

public class TouchEvent {
    public enum EventType {PRESSED, RELEASED, MOVED}
    public EventType type;
    public int posX;
    public int posY;
    public int id;
}
