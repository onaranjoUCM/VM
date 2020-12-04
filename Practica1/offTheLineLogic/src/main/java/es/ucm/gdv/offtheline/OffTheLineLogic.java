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
    float timeToSkipLevel = 1.5f;
    boolean hardMode = false;
    boolean pauseGame;
    Lives lives_;
    String mode_;

    int W_, H_;

    public static final int PlayEasy = 0;
    public static final int PlayHard = 1;
    public static final int ReturnMenu = 2;

    public OffTheLineLogic(Engine e, InputStream stream, Input i) {
        graphics = e.getGraphics();
        f_ = e.getGraphics().newFont("Bungee-Regular.ttf", 20, true);
        lr = new LevelReader(stream);
        loadMenu();
        lastItemTime = System.nanoTime();
        input = i;
    }

    public void handleInput() {
        if(input.getEvents().size() > 0){
            for(TouchEvent t : input.getEvents()) {
                switch (t.type) {
                    case 1: // Pulsacion
                        boolean tick = false;
                        float increX = (float)640 / W_;
                        float increY = (float)480 / H_;
                        t.posX -= W_ / 2;
                        t.posY -= H_ / 2;
                        if(increX != 1 && increY!=1) {
                            t.posX *= increX;
                            t.posY *= increY;
                        }
                        for (GameObject object : gameObjects){
                            try {
                                Button b = (Button)object;
                                if(b.button_pressed(t.posX, t.posY)) {
                                    System.out.println("Pulsado");
                                    if(b.getId_() == PlayEasy) {
                                        EasyGame();
                                    }
                                    else if(b.getId_() == PlayHard) {
                                        HardGame();
                                    }
                                    else if(b.getId_() == ReturnMenu) {
                                        ReturnMenu();
                                    }
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
            if (Utils.sqrDistanceBetweenTwoPoints(new Vector2(player_.posX_, player_.posY_), new Vector2(0, 0)) > 700 * 700)
                killPlayer();

            checkCollisions(oldPlayerX, oldPlayerY, player_.posX_, player_.posY_);

            if (levelFinished) {
                if ((System.nanoTime() - lastItemTime) / 1.0E9 > timeToSkipLevel) {
                    if (currentLevel < 19)
                        loadLevel(++currentLevel);
                    else
                        WinMenu();
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

        if (player_.collidesWithEnemy(gameObjects))
            killPlayer();

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

        String title = "Level " + (currentLevel + 1) + " - " + lr.name;
        gameObjects.add(new Text(-300,-170, 20, "BungeeHairline-Regular.ttf", title, 255,255,255, graphics));

        player_ = new Player((Path)gameObjects.get(0), 10, 10, 45, hardMode);
        gameObjects.add(player_);

        gameObjects.add(lives_);

        nItems = lr.nItems;
        levelFinished = false;
        lastItemTime = 0;
    }

    void EasyGame(){
        hardMode = false;
        mode_ = "Easy Mode";
        newGame();
    }

    void HardGame(){
        hardMode = true;
        mode_ = "Hard Mode";
        newGame();
    }

    void ReturnMenu(){
        loadMenu();
    }

    void newGame(){
        lives_ = new Lives(50,180, 210, 15, (hardMode) ? 5 : 10);
        currentLevel = 5;
        loadLevel(currentLevel);
    }

    void loadMenu() {
        pauseGame = true;
        gameObjects = new ArrayList<GameObject>();
        gameObjects.add(new Text(-250,-100, 60, "Bungee-Regular.ttf", "OFF THE LINE", 50,50,255, graphics));
        gameObjects.add(new Button(-250,50, 180,20, PlayEasy,"Bungee-Regular.ttf", "EASY MODE", 255,255,255, graphics));
        gameObjects.add(new Button(-250,100, 180,20, PlayHard,"Bungee-Regular.ttf", "HARD MODE", 255,255,255, graphics));
        levelFinished = false;
    }

    void GameOverMenu() {
        gameObjects.add(new MenuBackground(-320, 20, 640, 130, 50, 50, 50));
        gameObjects.add(new Text(-125,-110, 40, "Bungee-Regular.ttf", "GAME OVER", 255,0,0, graphics));
        gameObjects.add(new Text(-60,-70, 20, "Bungee-Regular.ttf", mode_, 255,255,255, graphics));
        gameObjects.add(new Text(-45,-40, 20, "Bungee-Regular.ttf", "Score: "+ (currentLevel + 1), 255,255,255, graphics));
        gameObjects.add(new Button(-100,80, 180,20, ReturnMenu,"Bungee-Regular.ttf", "Return to Menu", 255,255,255, graphics));
        pauseGame = true;
    }

    void WinMenu() {
        gameObjects.add(new MenuBackground(-320, 20, 640, 130, 50, 50, 50));
        gameObjects.add(new Text(-220,-100, 40, "Bungee-Regular.ttf", "CONGRATULATIONS", 255,255,0, graphics));
        gameObjects.add(new Text(-140,-70, 20, "Bungee-Regular.ttf", mode_ + " completed", 255,255,255, graphics));
        gameObjects.add(new Button(-100,-40, 180,20, ReturnMenu,"Bungee-Regular.ttf", "Return to Menu", 255,255,255, graphics));
        pauseGame = true;
        levelFinished = false;
    }

    void killPlayer() {
        lives_.take_life();
        if(lives_.game_Over())
            GameOverMenu();
        else
            loadLevel(currentLevel);
    }
}