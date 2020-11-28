package es.ucm.gdv.offtheline;

import java.util.ArrayList;

import es.ucm.gdv.engine.*;

public class OffTheLineLogic {
    ArrayList<GameObject> gameObjects;
    Graphics graphics;
    LevelReader lr;
    int nItems;
    boolean levelFinished;
    long lastItemTime;

    int currentLevel = 0;
    int timeToSkipLevel = 3;

    public OffTheLineLogic(Engine e) {
        lr = new LevelReader();
        loadLevel(currentLevel);
        graphics = e.getGraphics();
        lastItemTime = System.nanoTime();
    }

    public void handleInput() {
        // Do something
    }

    public void update(double deltaTime) {
        for (GameObject object : gameObjects) {
            object.update(deltaTime);
        }

        if (levelFinished) {
            if ((System.nanoTime() - lastItemTime) / 1.0E9 > timeToSkipLevel) {
                if (currentLevel < 19)
                    loadLevel(currentLevel++);
                else
                    ;// Pantalla final

                // Si descomentas esto y pones levelFinished a true pasan los niveles solos
                //lastItemTime = System.nanoTime();
            }
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
        if (c.collidesWithCoin() != null) {
            nItems--;
            if (nItems == 0) {
                levelFinished = true;
                lastItemTime = System.nanoTime();
            }
        }

        if (c.collidesWithEnemy())
            ;// die()

        Path path = c.collidesWithPath();
        if (path != null) {
            Player p = (Player) (gameObjects.get(gameObjects.size() - 1));
            p.setPath(path);
        }
    }

    void loadLevel(int level) {
        if (gameObjects != null)
            gameObjects.clear();
        gameObjects = lr.loadLevel(level);
        nItems = lr.nItems;
        levelFinished = false;
        lastItemTime = 0;
    }
}