package es.ucm.gdv.offtheline;
import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;

public class Player extends GameObject{
    public Player(int posX, int posY, int W, int H, int speed ){
        super(posX, posY, W, H);
        speed_= speed;

    }

    public void update(Input i){
        posX_ += speed_;
    }

    public void render(Graphics g){
        g.drawLine(posX_ - (W_/2), poxY_ - (H_/2), posX_ + (W_/2), poxY_ - (H_/2)); //LineaArriba
        g.drawLine(posX_ - (W_/2), poxY_ + (H_/2), posX_ + (W_/2), poxY_ + (H_/2)); //LineaAbajo
        g.drawLine(posX_ - (W_/2), poxY_ - (H_/2), posX_ - (W_/2), poxY_ + (H_/2)); //LineaIzquierda
        g.drawLine(posX_ + (W_/2), poxY_ - (H_/2), posX_ + (W_/2), poxY_ + (H_/2)); //LineaDerecha
    }

    private
        int speed_;
}
