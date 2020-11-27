package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Player extends GameObject {
    private float speed_;
    private float moveSpeed_ = 250; // 400 in hard mode
    public float radius_;
    private float angle_, angle2_;
    private double dirX, dirY;
    private Path currentPath_;
    int pathVertexIndex = 0;

    public Player(Path path, int W, int H, float speed, float angle){
        super(path.vertices.get(0)[0], path.vertices.get(0)[1], W, H);
        speed_= speed;
        radius_ = (W < H) ? W : H;
        angle_ = angle;
        angle2_ = angle_ - 90;
        currentPath_ = path;
    }

    @Override
    public void update(double deltaTime) {
        if (currentPath_ != null)
            updateDirection();

        posX_ += moveSpeed_ * deltaTime * dirX;
        posY_ += moveSpeed_ * deltaTime * dirY;

        angle_ += speed_;
        angle2_+= speed_;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(0, 0, 255);
        g.drawLine((int)(posX_ - (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle2_))*(H_))), (int)(posX_ - (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaArriba
        g.drawLine((int)(posX_ + (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle2_))*(H_))), (int)(posX_ + (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaAbajo
        g.drawLine((int)(posX_ - (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle2_))*(H_))),  (int)(posX_ + (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaIzquierda
        g.drawLine((int)(posX_ + (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle2_))*(H_))), (int)(posX_ - (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaDerecha
    }

    public void setPath(Path path) {
        currentPath_ = path;
    }

    private void updateDirection() {
        int nextVertexIndex;
        if (pathVertexIndex + 1 < currentPath_.vertices.size())
            nextVertexIndex = pathVertexIndex + 1;
        else
            nextVertexIndex = 0;

        // Calculate direction
        float x1 = currentPath_.vertices.get(pathVertexIndex)[0];
        float y1 = currentPath_.vertices.get(pathVertexIndex)[1];
        float x2 = currentPath_.vertices.get(nextVertexIndex)[0];
        float y2 = currentPath_.vertices.get(nextVertexIndex)[1];

        float num = (y2 - y1);
        float den = (x2 - x1);

        double angle;
        angle = Math.toDegrees(Math.atan(num / den));

        if ((x2 < x1))
            angle += 180;

        dirX = Math.cos(Math.toRadians(angle));
        dirY = Math.sin(Math.toRadians(angle));

        if (Math.abs(posX_ - currentPath_.vertices.get(nextVertexIndex)[0]) < 0.5 &&
                Math.abs(posY_ - currentPath_.vertices.get(nextVertexIndex)[1]) < 0.5) {
            pathVertexIndex++;
            if (pathVertexIndex == currentPath_.vertices.size()) pathVertexIndex = 0;
        }
    }
}
