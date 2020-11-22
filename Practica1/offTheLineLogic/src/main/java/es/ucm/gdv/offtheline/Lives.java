package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

class Live extends GameObject {
    boolean live_;
    public Live(float posX, float posY, int W, int H, boolean live){
        super(posX, posY, W, H);
        live_ = live;
    }
    public void update(double deltaTime) {
    }
    public void render(Graphics g) {
        if(live_) {
            g.setColor(0, 0, 255);
            g.drawLine((int) (posX_ - (W_ / 2)), (int) (posY_ - (H_ / 2)), (int) (posX_ + (W_ / 2)), (int) (posY_ - (H_ / 2))); //LineaArriba
            g.drawLine((int) (posX_ - (W_ / 2)), (int) (posY_ + (H_ / 2)), (int) (posX_ + (W_ / 2)), (int) (posY_ + (H_ / 2))); //LineaAbajo
            g.drawLine((int) (posX_ - (W_ / 2)), (int) (posY_ - (H_ / 2)), (int) (posX_ - (W_ / 2)), (int) (posY_ + (H_ / 2))); //LineaIzquierda
            g.drawLine((int) (posX_ + (W_ / 2)), (int) (posY_ - (H_ / 2)), (int) (posX_ + (W_ / 2)), (int) (posY_ + (H_ / 2))); //LineaDerecha
        }
        else{
            g.setColor(255, 0, 0);
            g.drawLine((int) (posX_ - (W_ / 2)), (int) (posY_ - (H_ / 2)), (int) (posX_ + (W_ / 2)), (int) (posY_ + (H_ / 2)));
            g.drawLine((int) (posX_ - (W_ / 2)), (int) (posY_ + (H_ / 2)), (int) (posX_ + (W_ / 2)), (int) (posY_ - (H_ / 2)));
        }
    }

    public void setLive_(boolean live_) {
        this.live_ = live_;
    }

    public boolean isLive_() {
        return live_;
    }
}

public class Lives extends GameObject {
    int nLive_;
    int currentNLive_;
    Live[] lives;
    public Lives(float posX, float posY, int W, int H, int nLive){
        super(posX, posY, W, H);
        nLive_ = nLive;
        int x = 0;
        lives = new Live[nLive];
        for(int i=0; i<nLive_;i++){
            lives[i] = new Live(posX + x, posY, H, H, true);
            x += 50;
        }
        currentNLive_ = nLive_;
    }
    public void update(double deltaTime) {
    }
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

