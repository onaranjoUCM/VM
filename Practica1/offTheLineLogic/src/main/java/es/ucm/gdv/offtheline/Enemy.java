package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

import es.ucm.gdv.engine.Graphics;

public class Enemy extends GameObject {
    public float length_;
    private float angle_;
    private float speed_;
    private float posXIni;
    private float posYIni;
    private float offsetX_;
    private float offsetY_;
    private float time1_;
    private float time2_;
    private double dirX, dirY;
    private float speedMove_;
    boolean stop_;
    float timenow_;

    public Enemy(float posX, float posY, int length, float angle, float speed, float offsetX, float offsetY, float time1, float time2){
        super(posX, posY, length, 0);
        length_ = length;
        speed_ = speed;
        angle_ = angle;
        time1_ = time1;
        time2_ = time2;
        posXIni = posX;
        posYIni = posY;
        if(offsetX != 0 || offsetY!= 0) {
            offsetX_ = posX_ + offsetX;
            offsetY_ = posY_ + offsetY;
            int x = ((int)offsetX_ - (int)posX_)^2 + ((int)offsetY_ - (int)posY_)^2;
            float magnitude = (float)Math.sqrt(x);
            speedMove_ = magnitude/time1_;
        }
        stop_ = true;
    }

    @Override
    public void update(double deltaTime) {
        if(offsetX_ != 0 || offsetY_!= 0) {
            updateDirection(deltaTime);
            posX_ += speedMove_*deltaTime * dirX;
            posY_ += speedMove_* deltaTime * dirY;
        }
        angle_ += speed_ * deltaTime;
    }

    private void updateDirection(double deltaTime) {
        if((dirX > 0 && posX_ >= offsetX_) || (dirY > 0 && posY_ >= offsetY_) || (dirX < 0 && posX_ <= offsetX_) || (dirY < 0 && posY_ <= offsetY_)){
            float auxX = offsetX_;
            float auxY = offsetY_;
            offsetX_ = posXIni;
            offsetY_ = posYIni;
            posXIni = auxX;
            posYIni = auxY;
            dirX = 0;
            dirY = 0;
            timenow_ = time2_;
            stop_ = true;
        }
        if(stop_){
            timenow_ -= deltaTime;
            if(timenow_ <= 0)
                stop_ = false;
        }
        if(!stop_) {
            // Calculate direction
            float x1 = posX_;
            float y1 = posY_;
            float x2 = offsetX_;
            float y2 = offsetY_;

            float num = (y2 - y1);
            float den = (x2 - x1);

            double angle;
            if (num > 0 && den == 0) {
                angle = 90.0;
                dirX = 0;
                dirY = 1;
            } else if (num == 0 && den < 0) {
                angle = 180;
                dirX = -1;
                dirY = 0;
            } else if (num < 0 && den == 0) {
                angle = 270.0;
                dirX = 0;
                dirY = -1;
            } else if (num == 0 && den > 0) {
                angle = 0;
                dirX = 1;
                dirY = 0;
            } else {
                angle = Math.toDegrees(Math.atan(num / den));
                dirX = Math.cos(Math.toRadians(angle));
                dirY = Math.sin(Math.toRadians(angle));
            }
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(255, 0, 0);
        int x = (int) (posX_ + Math.cos(angle_) * (W_ / 2));
        int y = (int) (posY_ - Math.sin(angle_) * (W_ / 2));
        g.drawLine((int) posX_, (int) posY_, (int) (posX_ + Math.cos(Math.toRadians (angle_)) * (W_ / 2)), (int) (posY_ + Math.sin(Math.toRadians (angle_)) * (W_ / 2))); //Linea1
        g.drawLine((int) posX_, (int) posY_, (int) (posX_ - Math.cos(Math.toRadians (angle_)) * (W_ / 2)), (int) (posY_ + Math.sin(Math.toRadians (-angle_)) * (W_ / 2))); //Linea2
    }
}
