package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;

public abstract class GameObject {
    public GameObject(int posX, int posY, int W, int Y){
        posX_= posX;
        poxY_= posY;
        W_= W;
        H_= Y;
    }
    public abstract void update();

    public abstract void render(Graphics g);

    protected
        int posX_, poxY_;
        int W_, H_;
}
