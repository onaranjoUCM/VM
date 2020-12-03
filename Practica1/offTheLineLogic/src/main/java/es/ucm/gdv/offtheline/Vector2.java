package es.ucm.gdv.offtheline;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x_, float y_) {
        x = x_;
        y = y_;
    }

    public void set(float x_, float y_) {
        x = x_;
        y = y_;
    }

    public void set(Vector2 d) {
        x = d.x;
        y = d.y;
    }
}
