package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Enemy extends GameObject {
    public float length_;
    public float angle_;
    private float speed_;
    private float time1_;
    private float time2_;
    private float moveSpeed_;
    boolean stop_;
    float timeNow_;

    Vector2 dir_;
    Vector2 posIni;
    Vector2 offset_;
    Vector2 vertexA;
    Vector2 vertexB;

    public Enemy(float posX, float posY, int length, float angle, float speed, float offsetX, float offsetY, float time1, float time2){
        super(posX, posY, length, 0);
        length_ = length - 1;   // PROVISIONAL
        speed_ = speed;
        angle_ = angle;
        time1_ = time1;
        time2_ = time2;
        offset_ = new Vector2(0, 0);
        posIni = new Vector2(posX, posY);
        dir_ = new Vector2(0, 0);

        vertexA = new Vector2(0, 0);
        vertexB = new Vector2(0, 0);
        setLimits();

        if(offsetX != 0 || offsetY!= 0) {
            offset_.set(posX_ + offsetX, posY_ + offsetY);
            int x = ((int)offset_.x - (int)posX_)^2 + ((int)offset_.y - (int)posY_)^2;
            float magnitude = (float)Math.sqrt(x);
            moveSpeed_ = magnitude/time1_;
        }
        stop_ = true;
    }

    @Override
    public void update(double deltaTime) {
        if(offset_.x != 0 || offset_.y != 0) {
            updateDirection(deltaTime);
            posX_ += moveSpeed_ *deltaTime * dir_.x;
            posY_ += moveSpeed_ * deltaTime * dir_.y;
        }

        if (speed_ != 0) {
            angle_ += speed_ * deltaTime;
            setLimits();
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

    private void updateDirection(double deltaTime) {
        if((dir_.x > 0 && posX_ >= offset_.x) || (dir_.y > 0 && posY_ >= offset_.y) || (dir_.x < 0 && posX_ <= offset_.x) || (dir_.y < 0 && posY_ <= offset_.y)){
            float auxX = offset_.x;
            float auxY = offset_.y;
            offset_.set(posIni.x, posIni.y);
            posIni.set(auxX, auxY);
            dir_.x = 0;
            dir_.y = 0;
            timeNow_ = time2_;
            stop_ = true;
        }
        if(stop_){
            timeNow_ -= deltaTime;
            if(timeNow_ <= 0)
                stop_ = false;
        }
        if(!stop_) {
            // Calculate direction
            float x1 = posX_;
            float y1 = posY_;
            float x2 = offset_.x;
            float y2 = offset_.y;

            float num = (y2 - y1);
            float den = (x2 - x1);

            double angle;
            if (num > 0 && den == 0) {
                angle = 90.0;
                dir_.x = 0;
                dir_.y = 1;
            } else if (num == 0 && den < 0) {
                angle = 180;
                dir_.x = -1;
                dir_.y = 0;
            } else if (num < 0 && den == 0) {
                angle = 270.0;
                dir_.x = 0;
                dir_.y = -1;
            } else if (num == 0 && den > 0) {
                angle = 0;
                dir_.x = 1;
                dir_.y = 0;
            } else {
                angle = Math.toDegrees(Math.atan(num / den));
                dir_.x = (float)Math.cos(Math.toRadians(angle));
                dir_.y = (float)Math.sin(Math.toRadians(angle));
            }
        }

    }

    private void setLimits() {
        vertexA.set((float)(posIni.x + (length_ / 2 * Math.cos(angle_))), (float)(posIni.y + (length_ / 2 * Math.sin(angle_))));
        vertexB.set((float)(posIni.x + (-length_ / 2 * Math.cos(angle_))), (float)(posIni.y + (-length_ / 2 * Math.sin(angle_))));
    }
}
