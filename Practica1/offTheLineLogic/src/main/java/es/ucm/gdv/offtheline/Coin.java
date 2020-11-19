package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Coin extends GameObject {
    private float speed_;
    private float radius_;
    private float angle_;

    public Coin(int posX, int posY, int W, int H, float speed, float radius, float angle){
        super(posX, posY, W, H);
        speed_= speed;
        radius_ = radius;
        angle_ = angle;

    }

    @Override
    public void update(double deltaTime) {
        posX_ += speed_;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(255, 255, 0);
        g.drawLine(posX_ - (W_/2), posY_ - (H_/2), posX_ + (W_/2), posY_ - (H_/2)); //LineaArriba
        g.drawLine(posX_ - (W_/2), posY_ + (H_/2), posX_ + (W_/2), posY_ + (H_/2)); //LineaAbajo
        g.drawLine(posX_ - (W_/2), posY_ - (H_/2), posX_ - (W_/2), posY_ + (H_/2)); //LineaIzquierda
        g.drawLine(posX_ + (W_/2), posY_ - (H_/2), posX_ + (W_/2), posY_ + (H_/2)); //LineaDerecha
    }
}
