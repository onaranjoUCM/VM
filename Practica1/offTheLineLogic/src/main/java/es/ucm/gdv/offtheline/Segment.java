package es.ucm.gdv.offtheline;

public class Segment {
    Vector2 pointA_;
    Vector2 pointB_;
    Vector2 dir_;
    Vector2 jumpDir_;
    float length;

    Segment(Vector2 A, Vector2 B) {
        init(A, B);
        jumpDir_ = new Vector2(dir_.y, -dir_.x);
    }

    Segment(Vector2 A, Vector2 B, Vector2 jumpDir) {
        init(A, B);
        jumpDir_ = jumpDir;
    }

    void init(Vector2 A, Vector2 B) {
        pointA_ = A;
        pointB_ = B;
        dir_ = Utils.directionFromTwoPoints(A, B);
        length = (float)Math.sqrt(Utils.sqrDistanceBetweenTwoPoints(A, B));
    }
}
