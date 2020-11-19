package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Player extends GameObject {
    private float speed_;
    private int directionX, directionY;

    public Player(float posX, float posY, int W, int H, float speed){
        super(posX, posY, W, H);
        speed_= speed;

    }

    @Override
    public void update(double deltaTime) {
        posX_ += speed_; //* deltaTime;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(0, 0, 255);
        g.drawLine((int)(posX_ - (W_/2)), (int)(posY_ - (H_/2)), (int)(posX_ + (W_/2)), (int)(posY_ - (H_/2))); //LineaArriba
        g.drawLine((int)(posX_ - (W_/2)), (int)(posY_ + (H_/2)), (int)(posX_ + (W_/2)), (int)(posY_ + (H_/2))); //LineaAbajo
        g.drawLine((int)(posX_ - (W_/2)), (int)(posY_ - (H_/2)), (int)(posX_ - (W_/2)), (int)(posY_ + (H_/2))); //LineaIzquierda
        g.drawLine((int)(posX_ + (W_/2)), (int)(posY_ - (H_/2)), (int)(posX_ + (W_/2)), (int)(posY_ + (H_/2))); //LineaDerecha
    }
}
