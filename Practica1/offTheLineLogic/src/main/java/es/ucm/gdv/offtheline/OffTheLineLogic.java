package es.ucm.gdv.offtheline;

import java.io.InputStream;
import java.util.ArrayList;

import es.ucm.gdv.engine.*;

public class OffTheLineLogic {
    ArrayList<GameObject> gameObjects;
    Graphics graphics;
    Input input;
    LevelReader lr;
    Player player_;
    Font f_;

    int nItems;
    boolean levelFinished;
    long lastItemTime;

    int currentLevel = 0;
    int timeToSkipLevel = 2;
    boolean hardMode = false;
    boolean pauseGame;
    Lives lives_;

    int W_, H_;

    public OffTheLineLogic(Engine e, InputStream stream, Input i) {
        graphics = e.getGraphics();
        f_ = e.getGraphics().newFont("Bungee-Regular.ttf", 20, true);
        lr = new LevelReader(stream);
        //loadLevel(currentLevel);
        loadMenu();
        lastItemTime = System.nanoTime();
        input = i;//new Input();


    }

    public void handleInput() {
        if(input.getEvents().size() > 0){
            for(TouchEvent t : input.getEvents()) {
                switch (t.type) {
                    case 1: // Pulsacion
                        boolean tick = false;
                        t.posX -= W_/2;
                        t.posY -= H_/2;
                        /*float increX = (float)W_/640;
                        float increY = (float)H_/480;
                        t.posX -= W_/2;
                        t.posY -= H_/2;
                        t.posX /= increX;
                        t.posY /= increY;*/
                        for (GameObject object : gameObjects){
                            try {
                                Button b = (Button)object;
                                if(b.button_pressed(t.posX, t.posY)){
                                    System.out.println("Pulsado");
                                    newGame();
                                    tick = true;
                                    break;
                                }
                            }
                            catch (Exception e) {
                                continue;
                            }
                        }
                        if(!pauseGame && !tick)
                            player_.jump();
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
        float oldPlayerX = 0, oldPlayerY = 0;
        if(!pauseGame) {
            oldPlayerX = player_.posX_;
            oldPlayerY = player_.posY_;
        }

        for (GameObject object : gameObjects) {
            object.update(deltaTime);
        }

        if(!pauseGame) {
            checkCollisions(oldPlayerX, oldPlayerY, player_.posX_, player_.posY_);

            if (levelFinished) {
                if ((System.nanoTime() - lastItemTime) / 1.0E9 > timeToSkipLevel) {
                    if (currentLevel < 19)
                        loadLevel(++currentLevel);
                    else
                        ;// Pantalla final
                }
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

        float inc = (w < h) ? w / 640 : h / 480;

        graphics.scale(1f * inc, -1f * inc);
        W_ = graphics.getWidth();
        H_ = graphics.getHeight();
    }

    void checkCollisions(float startX, float startY, float endX, float endY) {
        player_.collidesWithPath(gameObjects);

        if (player_.collidesWithEnemy(gameObjects)) {
            lives_.take_life();
            Path firstPath = (Path)gameObjects.get(0);
            player_.setPosition(firstPath.segments.get(0).pointA_);
            player_.setClockWise(true);
        }

        Coin c = player_.collidesWithCoin(gameObjects);
        if (c != null) {
            nItems--;
            gameObjects.remove(c);
            if (nItems == 0) {
                levelFinished = true;
                lastItemTime = System.nanoTime();
            }
        }
    }

    void loadLevel(int level) {
        pauseGame = false;
        if (gameObjects != null)
            gameObjects.clear();

        gameObjects = lr.loadLevel(level, hardMode);

        // Add player last to render on top of everything
        player_ = new Player((Path)gameObjects.get(0), 10, 10, 0.05f, 45, hardMode);
        gameObjects.add(player_);

        nItems = lr.nItems;
        levelFinished = false;
        lastItemTime = 0;
    }

    void newGame(){
        loadLevel(currentLevel);
        lives_ = new Lives(50,150, 100, 20, (hardMode) ? 5 : 5);
        gameObjects.add(lives_);
    }

    void loadMenu(){
        pauseGame = true;
        gameObjects = new ArrayList<GameObject>();
        gameObjects.add(new Text(-250,-100, 60, "Bungee-Regular.ttf", "OFF THE LINE", 50,50,255, graphics));
        gameObjects.add(new Button(-250,50, 180,20,"Bungee-Regular.ttf", "EASY MODE", 255,255,255, graphics));
        gameObjects.add(new Button(-250,100, 180,20, "Bungee-Regular.ttf", "HARD MODE", 255,255,255, graphics));
        levelFinished = false;
    }

    void GameOverMenu(){
        gameObjects.add(new Text(-125,-100, 40, "Bungee-Regular.ttf", "GAME OVER", 255,0,0, graphics));
    }

    void WinMenu(){
        gameObjects.add(new Text(-50,-100, 40, "Bungee-Regular.ttf", "WIN", 255,255,0, graphics));
    }
}