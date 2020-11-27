package es.ucm.gdv.offtheline;

import java.util.ArrayList;

import es.ucm.gdv.engine.*;

public class OffTheLineLogic {
    ArrayList<GameObject> gameObjects;
    Graphics graphics;

    public OffTheLineLogic(Engine e) {
        LevelReader lr = new LevelReader();
        gameObjects = lr.loadLevel(4);
        graphics = e.getGraphics();
    }

    public void handleInput() {
        // Do something
    }

    public void update(double deltaTime) {
        for (GameObject object : gameObjects) {
            object.update(deltaTime);
        }
    }

    public void render() {
        graphics.clear(0, 0, 0);
        graphics.translate(graphics.getWidth() / 2, graphics.getHeight()/2);
        adjustToWindow();

        for (GameObject object : gameObjects) {
            object.render(graphics);
        }
    }

    void adjustToWindow() {
        float w = graphics.getWidth();
        float h = graphics.getHeight();

        float inc = (w < h) ? w / 800 : h / 600;

        graphics.scale(1f * inc, -1f * inc);
    }

    void checkCollisions(Collision c) {
        if (c.collidesWithCoin() != null)
            ;// pickCoin()

        if (c.collidesWithEnemy())
            ;// die()

        Path path = c.collidesWithPath();
        if (path != null) {
            Player p = (Player) (gameObjects.get(gameObjects.size() - 1));
            p.setPath(path);
        }
    }
}