package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Player extends GameObject {
    private float speed_;
    private float moveSpeed_ = 250; // 400 in hard mode
    public float radius_;
    private float angle_, angle2_;
    private Vector2 dir_;
    private Path currentPath_;
    int pathVertexIndex = 0;
    int nextVertexIndex = 1;

    public Player(Path path, int W, int H, float speed, float angle){
        super(path.vertices.get(0).x, path.vertices.get(0).y, W, H);
        dir_ = new Vector2(0, 0);
        speed_= speed;
        radius_ = (W < H) ? W : H;
        angle_ = angle;
        angle2_ = angle_ - 90;
        currentPath_ = path;
    }

    @Override
    public void update(double deltaTime) {
        // If on a path, follow it
        updateDirection();

        // Update position
        posX_ += moveSpeed_ * deltaTime * dir_.x;
        posY_ += moveSpeed_ * deltaTime * dir_.y;

        // If the player skips the target, return to it and update current vertex
        if (skippedTarget())
            updateCurrentVertex();

        // Update spin angle
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
        if (currentPath_ != null) {
            if (pathVertexIndex + 1 < currentPath_.vertices.size())
                nextVertexIndex = pathVertexIndex + 1;
            else
                nextVertexIndex = 0;

            // Calculate direction
            float x1 = currentPath_.vertices.get(pathVertexIndex).x;
            float y1 = currentPath_.vertices.get(pathVertexIndex).y;
            float x2 = currentPath_.vertices.get(nextVertexIndex).x;
            float y2 = currentPath_.vertices.get(nextVertexIndex).y;

            float num = (y2 - y1);
            float den = (x2 - x1);

            double angle;
            angle = Math.toDegrees(Math.atan(num / den));

            if ((x2 < x1))
                angle += 180;

            dir_.x = (float)Math.cos(Math.toRadians(angle));
            dir_.y = (float)Math.sin(Math.toRadians(angle));
        }
    }

    private void updateCurrentVertex() {
        if (currentPath_ != null) {
            posX_  = currentPath_.vertices.get(nextVertexIndex).x;
            posY_ = currentPath_.vertices.get(nextVertexIndex).y;

            pathVertexIndex++;
            if (pathVertexIndex == currentPath_.vertices.size()) pathVertexIndex = 0;
        }
    }

    public boolean isFlying() {
        return currentPath_ == null;
    }

    private boolean skippedTarget() {
        if (currentPath_ != null) {
            float originX = currentPath_.vertices.get(pathVertexIndex).x;
            float originY = currentPath_.vertices.get(pathVertexIndex).y;
            float targetX = currentPath_.vertices.get(nextVertexIndex).x;
            float targetY = currentPath_.vertices.get(nextVertexIndex).y;

            if (originX < targetX)
                return posX_ > targetX;
            else if (originX > targetX)
                return posX_ < targetX;
            else if (originY < targetY)
                return posY_ > targetY;
            else if (originY > targetY)
                return posY_ < targetY;
        }

        return false;
    }

    public void jump() {
        float x = currentPath_.vertices.get(nextVertexIndex).x - currentPath_.vertices.get(pathVertexIndex).x;
        float y = currentPath_.vertices.get(nextVertexIndex).y - currentPath_.vertices.get(pathVertexIndex).y;

        if (currentPath_.directions.isEmpty()) {
            dir_.x = y;
            dir_.y = -x;
        } else {
            dir_.x = currentPath_.directions.get(pathVertexIndex).x;
            dir_.y = currentPath_.directions.get(pathVertexIndex).y;
        }

        if (dir_.x > 0) dir_.x = 1;
        if (dir_.x < 0) dir_.x = -1;
        if (dir_.y > 0) dir_.y = 1;
        if (dir_.y < 0) dir_.y = -1;

        currentPath_ = null;
    }
}
