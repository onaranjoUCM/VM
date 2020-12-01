package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public abstract class GameObject {
    public GameObject(float posX, float posY, int W, int Y){
        posX_= posX;
        posY_= posY;
        W_= W;
        H_= Y;
    }
    public abstract void update(double deltaTime);

    public abstract void render(Graphics g);

    protected
        float posX_, posY_;
        int W_, H_;
}
