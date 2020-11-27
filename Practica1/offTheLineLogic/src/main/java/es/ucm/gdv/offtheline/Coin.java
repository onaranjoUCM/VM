package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Coin extends GameObject {
    private float speed_;
    private float spinSpeed_;
    private float radius_;
    private float spinAngle_, spinAngle2_;
    private float angle_;

    public Coin(float posX, float posY, float radius, float speed, float angle){
        super(posX, posY, 10, 10);
        speed_= speed;
        radius_ = radius;
        angle_ = angle;

        spinSpeed_ = 0.05f;
        spinAngle_ = 45;
        spinAngle2_ = spinAngle_ - 90;
    }

    @Override
    public void update(double deltaTime) {
        angle_ += speed_ * deltaTime;

        if (radius_ != 0) {
            posX_ = (float) (radius_ * Math.cos(Math.toRadians(angle_)));
            posY_ = (float) (radius_ * Math.sin(Math.toRadians(angle_)));
        }

        spinAngle_ += spinSpeed_;
        spinAngle2_+= spinSpeed_;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(255, 255, 0);
        g.drawLine((int)(posX_ - (Math.cos(Math.toRadians (spinAngle2_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (spinAngle2_))*(H_))), (int)(posX_ - (Math.cos(Math.toRadians (spinAngle_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (spinAngle_))*(H_)))); //LineaArriba
        g.drawLine((int)(posX_ + (Math.cos(Math.toRadians (spinAngle2_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (spinAngle2_))*(H_))), (int)(posX_ + (Math.cos(Math.toRadians (spinAngle_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (spinAngle_))*(H_)))); //LineaAbajo
        g.drawLine((int)(posX_ - (Math.cos(Math.toRadians (spinAngle2_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (spinAngle2_))*(H_))),  (int)(posX_ + (Math.cos(Math.toRadians (spinAngle_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (spinAngle_))*(H_)))); //LineaIzquierda
        g.drawLine((int)(posX_ + (Math.cos(Math.toRadians (spinAngle2_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (spinAngle2_))*(H_))), (int)(posX_ - (Math.cos(Math.toRadians (spinAngle_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (spinAngle_))*(H_)))); //LineaDerecha
    }
}
