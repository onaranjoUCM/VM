package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Player extends GameObject {
    private float speed_;

    public Player(int posX, int posY, int W, int H, float speed){
        super(posX, posY, W, H);
        speed_= speed;

    }

    @Override
    public void update(double deltaTime) {
        //posX_ += speed_;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(0, 0, 255);
        g.drawLine(posX_ - (W_/2), poxY_ - (H_/2), posX_ + (W_/2), poxY_ - (H_/2)); //LineaArriba
        g.drawLine(posX_ - (W_/2), poxY_ + (H_/2), posX_ + (W_/2), poxY_ + (H_/2)); //LineaAbajo
        g.drawLine(posX_ - (W_/2), poxY_ - (H_/2), posX_ - (W_/2), poxY_ + (H_/2)); //LineaIzquierda
        g.drawLine(posX_ + (W_/2), poxY_ - (H_/2), posX_ + (W_/2), poxY_ + (H_/2)); //LineaDerecha
    }
}
