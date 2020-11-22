package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

import es.ucm.gdv.engine.Graphics;

public class Enemy extends GameObject {
    private float length_;
    private float angle_;
    private float speed_;
    private float posXIni;
    private float posYIni;
    private float offsetX_;
    private float offsetY_;
    private float time1_;
    private float time2_;

    public Enemy(float posX, float posY, int length, float angle, float speed, float offsetX, float offsetY, float time1, float time2){
        super(posX, posY, length, 0);
        length_ = length;
        speed_ = speed;
        angle_ = angle;
        offsetX_ = offsetX;
        offsetY_ = offsetY;
        time1_ = time1;
        time2_ = time2;
        posXIni = posX;
        posXIni = posY;
    }

    @Override
    public void update(double deltaTime) {
        angle_ += speed_;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(255, 0, 0);
        g.drawLine((int) posX_, (int) posY_, (int) (posX_ + Math.cos(angle_) * (W_ / 2)), (int) (posY_ - Math.sin(angle_) * (W_ / 2))); //Linea1
        g.drawLine((int) posX_, (int) posY_, (int) (posX_ - Math.cos(angle_) * (W_ / 2)), (int) (posY_ + Math.sin(angle_) * (W_ / 2))); //Linea2
    }
}
