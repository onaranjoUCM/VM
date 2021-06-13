package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

// Object to represent a single live in the HUD
class Live extends GameObject {
    boolean live_;

    public Live(float posX, float posY, int W, int H, boolean live){
        super(posX, posY, W, H);
        live_ = live;
    }

    public void update(double deltaTime) { }

    public void render(Graphics g) {
        g.save();
        g.translate((int)posX_, (int)posY_);
        if(live_) {
            g.setColor(0, 0, 255);
            g.drawLine(-W_ / 2, -H_ / 2, W_ / 2, -H_ / 2); //LineaArriba
            g.drawLine(-W_ / 2, H_ / 2, W_ / 2, H_ / 2); //LineaAbajo
            g.drawLine(-W_ / 2, -H_ / 2, -W_ / 2, H_ / 2); //LineaIzquierda
            g.drawLine(W_ / 2, -H_ / 2, W_ / 2, H_ / 2); //LineaDerecha
        }
        else{
            g.setColor(255, 0, 0);
            g.drawLine(-W_ / 2,-H_ / 2, W_ / 2, H_ / 2);
            g.drawLine(-W_ / 2, H_ / 2, W_ / 2, -H_ / 2);
        }
        g.restore();
    }

    public void setLive_(boolean live_) {
        this.live_ = live_;
    }

    public boolean isLive_() {
        return live_;
    }
}

// Object to store and handle multiple lives in the HUD
public class Lives extends GameObject {
    int nLive_;
    int currentNLive_;
    Live[] lives;

    public Lives(float posX, float posY, int W, int H, int nLive){
        super(posX, posY, W, H);
        nLive_ = nLive;
        float x = 0;
        float incre = W/nLive;
        lives = new Live[nLive];
        for(int i=0; i<nLive_;i++){
            lives[i] = new Live(posX + x, posY, H, H, true);
            x += incre;
        }
        currentNLive_ = nLive_;
    }

    public void update(double deltaTime) { }

    public void render(Graphics g) {
        for(int i=0; i<nLive_;i++){
            lives[i].render(g);
        }
    }

    public void take_life() {
        for(int i = nLive_ - 1; i >= 0;i--){
            if(lives[i].isLive_()){
                lives[i].setLive_(false);
                currentNLive_--;
                break;
            }
        }
    }

    public boolean game_Over(){
        if(currentNLive_ == 0){
            return  true;
        }
        return false;
    }
}

