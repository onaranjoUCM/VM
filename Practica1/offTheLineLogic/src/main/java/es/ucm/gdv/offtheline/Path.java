package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Path extends GameObject {
    float posFinX_, posFinY_;

    public Path(float posIniX, float posIniY, float posFinX, float posFinY, float direction){
        super(posIniX, posIniY, 0, 0);
        posFinX_ = posFinX;
        posFinY_ = posFinY;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(255, 255, 255);
        g.drawLine((int) posX_, (int) posY_, (int) posFinX_, (int) posFinY_); //Linea1

    }
}
