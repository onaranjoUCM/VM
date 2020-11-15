package es.ucm.gdv.offtheline;

import java.util.ArrayList;

import es.ucm.gdv.engine.*;

public class OffTheLineLogic {
    ArrayList<GameObject> gameObjects;
    Graphics graphics;

    public void init(Engine e) {
        gameObjects = new ArrayList<GameObject>();
        graphics = e.getGraphics();
        gameObjects.add(new Player(0, 0, 10, 10, 0.05f));
    }

    public void run() {
        while (true) {
            handleInput();
            update();
            render();
        }
    }

    public void handleInput() {
        // Do something
    }

    public void update() {
        for (GameObject object : gameObjects) {
            object.update();
        }
    }

    public void render() {
        graphics.updateSurface();
        for (GameObject object : gameObjects) {
            object.render(graphics);
        }
    }
}