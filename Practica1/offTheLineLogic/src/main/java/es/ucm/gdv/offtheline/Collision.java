package es.ucm.gdv.offtheline;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class Collision {
    ArrayList<GameObject> gameObjects_;
    Player player_;

    Collision(ArrayList<GameObject> gameObjects) {
        gameObjects_ = gameObjects;
        player_ = (Player)gameObjects_.get(gameObjects_.size() - 1);
    }

    Coin collidesWithCoin() {
        for (GameObject o : gameObjects_)
            try {
                Coin c = (Coin)o;
                if (collides(player_, c))
                    return c;
            } catch (Exception e) {
                continue;
            }
        return null;
    }

    boolean collidesWithEnemy() {
        for (GameObject o : gameObjects_)
            try {
                Enemy e = (Enemy)o;
                if (collides(player_, e))
                    return true;
            } catch (Exception e) {
                continue;
            }
        return false;
    }

    Path collidesWithPath() {
        for (GameObject o : gameObjects_)
            try {
                Path p = (Path)o;
                if (collides(player_, p))
                    return p;
            } catch (Exception e) {
                continue;
            }
        return null;
    }

    boolean collides(Player p, Coin c) {
        float coinRadius = (c.W_ < c.H_) ? c.W_/2 : c.H_/2;
        float radius = p.radius_ + coinRadius;

        if (Math.abs(p.posX_ - c.posX_) < radius || Math.abs(p.posY_ - c.posY_) < radius)
            return true;
        else
            return false;
    }

    boolean collides(Player p, Enemy e) {
        return false;
    }

    boolean collides(Player p, Path path) {
        for (int i = 0; i < path.vertices.size(); i++) {
            int nextVertexIndex;
            if (i + 1 < path.vertices.size())
                nextVertexIndex = i + 1;
            else
                nextVertexIndex = 0;

            float x1 = path.vertices.get(i)[0];
            float y1 = path.vertices.get(i)[1];
            float x2 = path.vertices.get(nextVertexIndex)[0];
            float y2 = path.vertices.get(nextVertexIndex)[1];

            float distX = x1 - x2;
            float distY = y1 - y2;
            float len = (float)Math.sqrt((distX*distX) + (distY*distY));

            float dot = (float)((((p.posX_-x1)*(x2-x1)) + ((p.posY_-y1)*(y2-y1)) ) / pow(len,2));

            float closestX = x1 + (dot * (x2-x1));
            float closestY = y1 + (dot * (y2-y1));

            //boolean onSegment = linePoint(x1,y1,x2,y2, closestX,closestY);
            //if (!onSegment) continue;

            distX = closestX - p.posX_;
            distY = closestY - p.posY_;
            float distance = (float)Math.sqrt((distX*distX) + (distY*distY));

            if (distance <= p.radius_) {
                return true;
            }
        }

        return false;
    }
}
