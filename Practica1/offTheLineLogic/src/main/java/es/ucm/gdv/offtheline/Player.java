package es.ucm.gdv.offtheline;

import java.util.ArrayList;

import es.ucm.gdv.engine.Graphics;

public class Player extends GameObject {
    boolean hardMode_;
    float moveSpeed_;
    float rotationSpeed_;
    float radius_;
    float angle_, angle2_;

    int currentSegmentIndex_;
    Segment currentSegment_;
    Path currentPath_;
    Vector2 dir_;
    Vector2 previousPos;
    boolean clockwise = false;

    public Player(Path path, int W, int H, float speed, float angle, boolean mode) {
        super(path.segments.get(0).pointA_.x, path.segments.get(0).pointA_.y, W, H);
        hardMode_ = mode;
        moveSpeed_ = (hardMode_) ? 400 : 250;
        rotationSpeed_ = speed;

        radius_ = (W < H) ? W : H;
        angle_ = angle;
        angle2_ = angle_ - 90;

        currentPath_ = path;
        currentSegment_ = path.segments.get(0);
        currentSegmentIndex_ = 0;
        dir_ = new Vector2(0, 0);
        previousPos = new Vector2(posX_, posY_);
    }

    @Override
    public void update(double deltaTime) {
        // Save previous position for collisions
        previousPos.set(posX_, posY_);

        // If on a path, follow it
        updateDirection();

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
        if (currentSegment_ != null) {
            if (clockwise)
                dir_.set(currentSegment_.dir_.x, currentSegment_.dir_.y);
            else
                dir_.set(-currentSegment_.dir_.x, -currentSegment_.dir_.y);
        }
    }

    // Checks if the player has skipped a vertex by going too fast
    private boolean skippedTarget() {
        if (currentSegment_ != null) {
            float originX;
            float originY;
            float targetX;
            float targetY;

            if (clockwise) {
                originX = currentSegment_.pointA_.x;
                originY = currentSegment_.pointA_.y;
                targetX = currentSegment_.pointB_.x;
                targetY = currentSegment_.pointB_.y;
            } else {
                originX = currentSegment_.pointB_.x;
                originY = currentSegment_.pointB_.y;
                targetX = currentSegment_.pointA_.x;
                targetY = currentSegment_.pointA_.y;
            }

            if (originX < targetX && posX_ > targetX) return true;
            if (originX > targetX && posX_ < targetX) return true;
            if (originY < targetY && posY_ > targetY) return true;
            if (originY > targetY && posY_ < targetY) return true;
        }

        return false;
    }

    // This method is called when player reaches a path vertex
    private void updateCurrentVertex() {
        if (currentSegment_ != null) {
            if (clockwise) {
                currentSegmentIndex_++;
                if (currentSegmentIndex_ == currentPath_.segments.size())
                    currentSegmentIndex_ = 0;
                currentSegment_ = currentPath_.segments.get(currentSegmentIndex_);
                posX_ = currentSegment_.pointA_.x;
                posY_ = currentSegment_.pointA_.y;
            } else {
                currentSegmentIndex_--;
                if (currentSegmentIndex_ == -1)
                    currentSegmentIndex_ = currentPath_.segments.size() - 1;
                currentSegment_ = currentPath_.segments.get(currentSegmentIndex_);
                posX_ = currentSegment_.pointB_.x;
                posY_ = currentSegment_.pointB_.y;
            }
        }
    }

    // Sets the player direction to cross the path segment
    public void jump() {
        if (currentSegment_ != null) {
            dir_.set(currentSegment_.jumpDir_);
            moveSpeed_ = 1500;
            clockwise = !clockwise;
            currentPath_ = null;
            currentSegment_ = null;
        }
    }

    // Checks if the last player movement has made him cross a path segment
    public void collidesWithPath(ArrayList<GameObject> gameObjects) {
        for (GameObject o : gameObjects) {
            try {
                Path path = (Path)o;
                if (path != currentPath_) {
                    for (int i = 0; i < path.segments.size(); i++) {
                        if (i != currentSegmentIndex_) {
                            Vector2 playerPos = new Vector2(posX_, posY_);
                            Segment seg = path.segments.get(i);
                            if (Utils.segmentsIntersection(previousPos, playerPos, seg.pointA_, seg.pointB_) != null) {
                                currentPath_ = path;
                                currentSegment_ = seg;
                                currentSegmentIndex_ = i;
                                moveSpeed_ = (hardMode_) ? 400 : 250;
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
                    return true;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return false;
    }

    public Coin collidesWithCoin(ArrayList<GameObject> gameObjects) {
        for (GameObject o : gameObjects) {
            try {
                Coin coin = (Coin)o;
                Segment playerMoved = new Segment(new Vector2(posX_, posY_), previousPos);
                if (Utils.sqrDistancePointSegment(playerMoved, new Vector2(coin.posX_, coin.posY_)) < 400) {
                    return coin;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }
}
