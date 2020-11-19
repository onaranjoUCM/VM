package es.ucm.gdv.offtheline;

import java.util.ArrayList;

import es.ucm.gdv.engine.*;

public class OffTheLineLogic {
    ArrayList<GameObject> gameObjects;
    Graphics graphics;

    public OffTheLineLogic(Engine e) {
        gameObjects = new ArrayList<GameObject>();
        graphics = e.getGraphics();
        gameObjects.add(new Player(60, 200, 100, 100, 0.01f));
        gameObjects.add(new Coin(100, 100, 10, 10, 0.05f, 0, 45));
        gameObjects.add(new Enemy(100, 100, 100, 0.001f, 45f, 0, 0,0));
        gameObjects.add(new Path(400, 300, 500, 200, 0));
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
        for (GameObject object : gameObjects) {
            object.render(graphics);
        }
    }
}