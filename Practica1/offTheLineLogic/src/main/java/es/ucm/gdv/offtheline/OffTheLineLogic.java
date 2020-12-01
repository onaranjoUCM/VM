package es.ucm.gdv.offtheline;

import java.io.InputStream;
import java.util.ArrayList;

import es.ucm.gdv.engine.*;

public class OffTheLineLogic {
    ArrayList<GameObject> gameObjects;
    Graphics graphics;
    Input input;
    LevelReader lr;
    Player player;

    int nItems;
    boolean levelFinished;
    long lastItemTime;

    int currentLevel = 2;
    int timeToSkipLevel = 3;

    int W_,H_;

    public OffTheLineLogic(Engine e, InputStream stream, Input i) {
        lr = new LevelReader(stream);
        loadLevel(currentLevel);
        graphics = e.getGraphics();
        lastItemTime = System.nanoTime();
        input = i;//new Input();
    }

    public void handleInput() {
        if(input.getEvents().size() > 0){
            for(TouchEvent t : input.getEvents()) {
                switch (t.type) {
                    case 1: // Pulsacion
                        t.posX -= W_/2;
                        t.posY -= H_/2;
                        ((Player) gameObjects.get(gameObjects.size() - 1)).jump();
                        System.out.println("Eje X: " + t.posX);
                        System.out.println("Eje Y: " + t.posY);
                        break;
                    default:
                        break;
                }
            }
            input.getEvents().clear();
        }
    }

    public void update(double deltaTime) {
        float oldPlayerX = player.posX_;
        float oldPlayerY = player.posY_;

        for (GameObject object : gameObjects) {
            object.update(deltaTime);
        }

        checkCollisions(oldPlayerX, oldPlayerY, player.posX_, player.posY_);

        if (levelFinished) {
            if ((System.nanoTime() - lastItemTime) / 1.0E9 > timeToSkipLevel) {
                if (currentLevel < 19)
                    loadLevel(currentLevel++);
                else
                    ;// Pantalla final
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
        W_ = graphics.getWidth();
        H_ = graphics.getHeight();
    }

    void checkCollisions(float startX, float startY, float endX, float endY) {
        player.collidesWithPath(gameObjects);
    }

    void loadLevel(int level) {
        if (gameObjects != null)
            gameObjects.clear();
        gameObjects = lr.loadLevel(level);
        player = (Player)gameObjects.get(gameObjects.size() - 1);
        nItems = lr.nItems;
        levelFinished = false;
        lastItemTime = 0;
    }
}