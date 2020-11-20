package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Coin extends GameObject {
    private float speed_;
    private float radius_;
    private float angle_, angle2_;

    public Coin(float posX, float posY, int W, int H, float speed, float radius, float angle){
        super(posX, posY, W, H);
        speed_= speed;
        radius_ = radius;
        angle_ = angle;
        angle2_ = angle_ - 90;
    }

    @Override
    public void update(double deltaTime) {
        angle_ += speed_;
        angle2_+= speed_;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(255, 255, 0);
        g.drawLine((int)(posX_ - (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle2_))*(H_))), (int)(posX_ - (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaArriba
        g.drawLine((int)(posX_ + (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle2_))*(H_))), (int)(posX_ + (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaAbajo
        g.drawLine((int)(posX_ - (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle2_))*(H_))),  (int)(posX_ + (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaIzquierda
        g.drawLine((int)(posX_ + (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle2_))*(H_))), (int)(posX_ - (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaDerecha
    }
}
