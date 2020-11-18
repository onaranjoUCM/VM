package es.ucm.gdv.offtheline;

import java.util.ArrayList;

import es.ucm.gdv.engine.*;

public class OffTheLineLogic {
    ArrayList<GameObject> gameObjects;
    Graphics graphics;

    public OffTheLineLogic(Engine e) {
        gameObjects = new ArrayList<GameObject>();
        graphics = e.getGraphics();
        gameObjects.add(new Player(100, 100, 100, 100, 0.05f));
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
        graphics.clear(255, 255, 255);
        for (GameObject object : gameObjects) {
            object.render(graphics);
        }
    }
}