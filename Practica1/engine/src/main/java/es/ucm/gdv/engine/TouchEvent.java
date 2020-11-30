package es.ucm.gdv.engine;

public class TouchEvent {
    public void init(int posx, int posy, int type_, int id_) {
        posX = posx;
        posY = posy;
        type = type_;
        id = id_;
    }
    public int type;
    public int posX;
    public int posY;
    public int id;
}
