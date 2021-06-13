package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Coin extends GameObject {
    private float speed_;
    private float spinSpeed_;
    private float radius_;
    private float spinAngle_;
    private float angle_;

    public Coin(float posX, float posY, float radius, float speed, float angle){
        super(posX, posY, 10, 10);
        speed_= speed;
        radius_ = radius;
        angle_ = angle;

        spinSpeed_ = 5;
        spinAngle_ = 45;
    }

    @Override
    public void update(double deltaTime) {
        // Rotate around a point
        angle_ += speed_ * deltaTime;
        if (radius_ != 0) {
            posX_ = (float) (radius_ * Math.cos(Math.toRadians(angle_)));
            posY_ = (float) (radius_ * Math.sin(Math.toRadians(angle_)));
        }

        // Spin
        spinAngle_ += spinSpeed_ * deltaTime;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(255, 255, 0);
        g.save();
        g.translate((int)posX_, (int)posY_);
        g.rotate(spinAngle_);
        g.drawLine(-W_/2, -H_/2, W_/2, -H_/2);
        g.drawLine(W_/2, -H_/2,W_/2, H_/2);
        g.drawLine(W_/2, H_/2, -W_/2, H_/2);
        g.drawLine(-W_/2, H_/2, -W_/2, -H_/2);
        g.restore();
    }
}
