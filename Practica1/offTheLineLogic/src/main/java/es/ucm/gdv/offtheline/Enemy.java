package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

import es.ucm.gdv.engine.Graphics;

public class Enemy extends GameObject {
    private float speed_;
    private float angle_;
    private float offset_; //posicion final que se desplaza
    private double time1_, time2_; //time1 el tiempo que tarda en recorrer de la posINI a posFIN, time2 el tiempo que tarda el reanudar la marcha

    public Enemy(int posX, int posY, int length, float speed, float angle, float offset, double time1, double time2){
        super(posX, posY, length, 0);
        speed_= speed;
        angle_ = angle;
        offset_= offset;
        time1_ = time1;
        time2_ = time2;
    }

    @Override
    public void update(double deltaTime) {
        angle_ += speed_;
        //posX_ += speed_;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(255, 0, 0);
        g.drawLine(posX_, posY_, (int) (posX_ + Math.cos(angle_) * (W_ / 2)), (int) (posY_ - Math.sin(angle_) * (W_ / 2))); //Linea1
        g.drawLine(posX_, posY_, (int) (posX_ - Math.cos(angle_) * (W_ / 2)), (int) (posY_ + Math.sin(angle_) * (W_ / 2))); //Linea2
    }
}
