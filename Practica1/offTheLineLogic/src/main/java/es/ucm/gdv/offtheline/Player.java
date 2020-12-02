package es.ucm.gdv.offtheline;

import java.util.ArrayList;

import es.ucm.gdv.engine.Graphics;

public class Player extends GameObject {
    boolean hardMode_;

    float moveSpeed_;
    float rotationSpeed_;

    float radius_;
    float angle_, angle2_;

    Path currentPath_;
    Vector2 dir_;
    Vector2 previousPos;
    int pathVertexIndex = 0;
    int nextVertexIndex = 1;

    public Player(Path path, int W, int H, float speed, float angle, boolean mode){
        super(path.vertices.get(0).x, path.vertices.get(0).y, W, H);
        hardMode_ = mode;
        moveSpeed_ = (hardMode_) ? 250 : 400;
        rotationSpeed_ = speed;

        radius_ = (W < H) ? W : H;
        angle_ = angle;
        angle2_ = angle_ - 90;

        currentPath_ = path;
        dir_ = new Vector2(0, 0);
        previousPos = new Vector2(posX_, posY_);
    }

    @Override
    public void update(double deltaTime) {
        // If on a path, follow it
        updateDirection();

        // Save previous position for collisions
        previousPos.update(posX_, posY_);

        // Update position
        posX_ += moveSpeed_ * deltaTime * dir_.x;
        posY_ += moveSpeed_ * deltaTime * dir_.y;

        // If the player skips the target, return to it and update current vertex
        if (skippedTarget())
            updateCurrentVertex();

        // Update spin angle
        angle_ += rotationSpeed_;
        angle2_+= rotationSpeed_;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(0, 0, 255);
        g.drawLine((int)(posX_ - (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle2_))*(H_))), (int)(posX_ - (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaArriba
        g.drawLine((int)(posX_ + (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle2_))*(H_))), (int)(posX_ + (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaAbajo
        g.drawLine((int)(posX_ - (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle2_))*(H_))),  (int)(posX_ + (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaIzquierda
        g.drawLine((int)(posX_ + (Math.cos(Math.toRadians (angle2_))*(W_))), (int)(posY_ + (Math.sin(Math.toRadians (angle2_))*(H_))), (int)(posX_ - (Math.cos(Math.toRadians (angle_))*(W_))), (int)(posY_ - (Math.sin(Math.toRadians (angle_))*(H_)))); //LineaDerecha
    }

    // Sets the player direction to that of the path he is in
    private void updateDirection() {
        if (currentPath_ != null) {
            // Last vertex = first vertex
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

    // This method is called when player reaches a path vertex
    private void updateCurrentVertex() {
        if (currentPath_ != null) {
            posX_  = currentPath_.vertices.get(nextVertexIndex).x;
            posY_ = currentPath_.vertices.get(nextVertexIndex).y;

            pathVertexIndex++;
            if (pathVertexIndex == currentPath_.vertices.size()) pathVertexIndex = 0;
        }
    }

    // Checks if the player has skipped a vertex by going too fast
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

    // Sets the player direction to cross the path segment
    public void jump() {
        if (currentPath_ != null) {
            float x = currentPath_.vertices.get(nextVertexIndex).x - currentPath_.vertices.get(pathVertexIndex).x;
            float y = currentPath_.vertices.get(nextVertexIndex).y - currentPath_.vertices.get(pathVertexIndex).y;

            if (currentPath_.directions.isEmpty()) {
                dir_.x = y;
                dir_.y = -x;
            } else {
                dir_.x = currentPath_.directions.get(pathVertexIndex).x;
                dir_.y = currentPath_.directions.get(pathVertexIndex).y;
            }

            moveSpeed_ = 1500;
            if (dir_.x > 0) dir_.x = 1;
            if (dir_.x < 0) dir_.x = -1;
            if (dir_.y > 0) dir_.y = 1;
            if (dir_.y < 0) dir_.y = -1;

            currentPath_ = null;
        }
    }

    // Checks if the last player movement has made him cross a path segment
    public void collidesWithPath(ArrayList<GameObject> gameObjects) {
        for (GameObject o : gameObjects) {
            try {
                Path path = (Path)o;
                if (path != currentPath_) {
                    for (int i = 0; i < path.vertices.size(); i++) {
                        if (i != pathVertexIndex) {
                            int nextVertexIndex;
                            if (i + 1 < path.vertices.size())
                                nextVertexIndex = i + 1;
                            else
                                nextVertexIndex = 0;

                            Vector2 playerPos = new Vector2(posX_, posY_);
                            Vector2 segStart = new Vector2(path.vertices.get(i).x, path.vertices.get(i).y);
                            Vector2 segEnd = new Vector2(path.vertices.get(nextVertexIndex).x, path.vertices.get(nextVertexIndex).y);

                            if (Utils.segmentsIntersection(previousPos, playerPos, segStart, segEnd) != null) {
                                currentPath_ = path;
                                pathVertexIndex = i;
                                moveSpeed_ = (hardMode_) ? 250 : 400;
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
    }

    public boolean collidesWithEnemy(ArrayList<GameObject> gameObjects) {
        for (GameObject o : gameObjects) {
            try {
                Enemy enemy = (Enemy)o;
                Vector2 playerPos = new Vector2(posX_, posY_);
                if (Utils.segmentsIntersection(previousPos, playerPos, enemy.vertexA, enemy.vertexB) != null) {
                    System.out.println("OUCH");
                    return true;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return false;
    }
}
