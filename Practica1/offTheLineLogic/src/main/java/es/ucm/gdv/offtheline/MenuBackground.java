package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class MenuBackground extends GameObject {

    private int r_, g_, b_;

    public MenuBackground(float posX, float posY, int W, int H, int r, int g, int b) {
        super(posX, posY, W, H);
        r_ = r;
        g_ = g;
        b_ = b;
    }

    @Override
    public void update(double deltaTime) { }

    @Override
    public void render(Graphics g) {
        g.setColor(r_, g_, b_);
        g.fillRect((int)posX_, (int)posY_, (int)W_, (int)H_);
    }
}
