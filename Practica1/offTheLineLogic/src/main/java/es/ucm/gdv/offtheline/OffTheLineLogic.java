package es.ucm.gdv.offtheline;

import java.util.ArrayList;

import es.ucm.gdv.engine.*;

public class OffTheLineLogic {
    ArrayList<GameObject> gameObjects;
    Graphics graphics;

    public OffTheLineLogic(Engine e) {
        LevelReader lr = new LevelReader();
        gameObjects = lr.loadLevel(0);
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
        graphics.translate(graphics.getWidth()/2, graphics.getHeight()/2);
        for (GameObject object : gameObjects) {
            object.render(graphics);
        }
    }
}