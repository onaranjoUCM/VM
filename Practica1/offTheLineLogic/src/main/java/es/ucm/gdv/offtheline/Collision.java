package es.ucm.gdv.offtheline;

import java.util.ArrayList;

import static java.lang.Math.pow;
/*
public class Collision {
    static Coin collidesWithCoin(Player p, ArrayList<GameObject> gameObjects) {
        for (GameObject o : gameObjects)
            try {
                Coin c = (Coin)o;
                if (collides(p, c))
                    return c;
            } catch (Exception e) {
                continue;
            }
        return null;
    }

    static boolean collidesWithEnemy(Player p, ArrayList<GameObject> gameObjects) {
        for (GameObject o : gameObjects)
            try {
                Enemy e = (Enemy)o;
                if (collides(p, e))
                    return true;
            } catch (Exception e) {
                continue;
            }
        return false;
    }

    Path collidesWithPath(Player player_, ArrayList<GameObject> gameObjects) {
        for (GameObject o : gameObjects)
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
        float x1 = (float)(e.posX_ + e.posX_ * Math.cos(Math.toRadians(e.angle_)));
        float y1 = (float)(e.posX_ + e.posX_ * Math.sin(Math.toRadians(e.angle_)));
        float x2 = (float)(e.posX_ + e.posX_ * Math.cos(Math.toRadians(e.angle_ + 180)));
        float y2 = (float)(e.posX_ + e.posX_ * Math.sin(Math.toRadians(e.angle_ + 180)));

        float dot = (float)((((p.posX_-x1)*(x2-x1)) + ((p.posY_-y1)*(y2-y1)) ) / pow(e.length_,2));

        float closestX = x1 + (dot * (x2-x1));
        float closestY = y1 + (dot * (y2-y1));

        //boolean onSegment = linePoint(x1,y1,x2,y2, closestX,closestY);
        //if (!onSegment) continue;

        float distX = closestX - p.posX_;
        float distY = closestY - p.posY_;
        float distance = (float)Math.sqrt((distX*distX) + (distY*distY));

        if (distance <= p.radius_) {
            return true;
        }

        return false;
    }

    boolean collides(Player p, Path path) {
        for (int i = 0; i < path.vertices.size(); i++) {
            int nextVertexIndex;
            if (i + 1 < path.vertices.size())
                nextVertexIndex = i + 1;
            else
                nextVertexIndex = 0;

            float x1 = path.vertices.get(i).x;
            float y1 = path.vertices.get(i).y;
            float x2 = path.vertices.get(nextVertexIndex).x;
            float y2 = path.vertices.get(nextVertexIndex).y;

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
*/