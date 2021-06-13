package es.ucm.gdv.offtheline;

import java.io.InputStream;
import java.util.ArrayList;

import es.ucm.gdv.engine.*;

public class OffTheLineLogic {
    // Logic components
    Engine engine;
    Graphics graphics;
    Input input;
    LevelReader lr;

    // GameObjects
    ArrayList<GameObject> gameObjects;
    Player player_;
    Font f_;
    Lives lives_;

    // Level progression control
    int nItems;
    boolean levelFinished;
    long lastItemTime;
    int currentLevel = 0;
    float timeToSkipLevel = 1.5f;

    // Game configuration
    boolean hardMode = false;
    boolean gamePaused;
    String mode_;
    Engine.Vector2 wSize;

    // Button function IDs
    public static final int PlayEasy = 0;
    public static final int PlayHard = 1;
    public static final int ReturnMenu = 2;

    public OffTheLineLogic(Engine e, InputStream stream) {
        engine = e;
        graphics = e.getGraphics();
        input = e.getInput();
        f_ = e.getGraphics().newFont("Bungee-Regular.ttf", 20, true);
        lr = new LevelReader(stream);
        loadMenu();
        lastItemTime = System.nanoTime();
    }

    // ==================== MAIN LOOP FUNCTIONS ====================

    public void handleInput() {
        if(input.getEvents().size() > 0){
            for(TouchEvent t : input.getEvents()) {
                if (t.type == 1) { // Mouse click or finger tap
                    if (gamePaused) {
                        checkButtonClick(t);
                    } else {
                        player_.jump();
                    }
                }
            }
            input.getEvents().clear();
        }
    }

    public void update(double deltaTime) {
        // Update every gameobject
        for (GameObject object : gameObjects) {
            object.update(deltaTime);
        }

        if(!gamePaused) {
            // Player jumps off the map
            if (Utils.sqrDistanceBetweenTwoPoints(new Vector2(player_.posX_, player_.posY_), new Vector2(0, 0)) > 700 * 700)
                killPlayer();

            // Check player collisions
            checkCollisions();

            // Check if level is complete and if it should move to next level
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

        wSize = graphics.adjustToWindow();

        for (GameObject object : gameObjects) {
            object.render(graphics);
        }
    }

    // ==================== AUXILIARY FUNCTIONS ====================

    // Checks if click events are made over button boundaries
    private void checkButtonClick(TouchEvent t) {
        Engine.Vector2 coords = new Engine.Vector2(t.posX, t.posY);
        Engine.Vector2 adaptedCoords = engine.transformCoordinates(coords, wSize);

        // Check click coordinates with every button
        for (GameObject object : gameObjects) {
            try {
                Button b = (Button)object;
                if(b.button_pressed((int)adaptedCoords.x, (int)adaptedCoords.y)) {
                    if(b.getId_() == PlayEasy) {
                        EasyGame();
                    }
                    else if(b.getId_() == PlayHard) {
                        HardGame();
                    }
                    else if(b.getId_() == ReturnMenu) {
                        loadMenu();
                    }
                    break;
                }
            }
            catch (Exception e) {
                continue;
            }
        }
    }

    // Checks player collisions
    void checkCollisions() {
        // Check collisions with paths
        player_.collidesWithPath(gameObjects);

        // Check collisions with enemies
        if (player_.collidesWithEnemy(gameObjects))
            killPlayer();

        // Check collisions with coins
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

    // Loads a given level
    void loadLevel(int level) {
        // Reset variables
        gamePaused = false;
        levelFinished = false;
        lastItemTime = 0;

        // Clear gameObjects list
        if (gameObjects != null)
            gameObjects.clear();

        // Load level gameObjects
        gameObjects = lr.loadLevel(level, hardMode);
        nItems = lr.nItems;

        // Create and add level name label
        String title = "Level " + (currentLevel + 1) + " - " + lr.name;
        gameObjects.add(new Text(-300,-170, 20, "BungeeHairline-Regular.ttf", title, 255,255,255, graphics));

        // Create and add player
        player_ = new Player((Path)gameObjects.get(0), 10, 10, 45, hardMode);
        gameObjects.add(player_);

        // Add lives since they persist between levels
        gameObjects.add(lives_);

    }

    // Removes a life and checks if game is over
    void killPlayer() {
        lives_.take_life();
        if(lives_.game_Over())
            GameOverMenu();
        else
            loadLevel(currentLevel);
    }

    // ==================== BUTTON FUNCTIONS ====================

    // Sets mode to easy (Called from buttons)
    void EasyGame(){
        hardMode = false;
        mode_ = "Easy Mode";
        newGame();
    }

    // Sets mode to hard (Called from buttons)
    void HardGame(){
        hardMode = true;
        mode_ = "Hard Mode";
        newGame();
    }

    // Loads a new game
    void newGame(){
        lives_ = new Lives(50,180, 210, 15, (hardMode) ? 5 : 10);
        currentLevel = 0;
        loadLevel(currentLevel);
    }

    // ==================== MENU FUNCTIONS ====================

    // Loads main menu (Called from buttons and at the start of the game)
    void loadMenu() {
        gamePaused = true;
        gameObjects = new ArrayList<GameObject>();
        gameObjects.add(new Text(-250,-100, 60, "Bungee-Regular.ttf", "OFF THE LINE", 50,50,255, graphics));
        gameObjects.add(new Text(-250,-50, 20, "Bungee-Regular.ttf", "A GAME COPIED TO BRYAN PERFETTO", 50,50,255, graphics));
        gameObjects.add(new Button(-250,50, 180,20, PlayEasy,"Bungee-Regular.ttf", "EASY MODE", 255,255,255, graphics));
        gameObjects.add(new Text(-120,50, 12, "Bungee-Regular.ttf", "(SLOW SPEED, 10 LIVES)", 100,100,100, graphics));
        gameObjects.add(new Button(-250,100, 180,20, PlayHard,"Bungee-Regular.ttf", "HARD MODE", 255,255,255, graphics));
        gameObjects.add(new Text(-110,100, 12, "Bungee-Regular.ttf", "(FAST SPEED, 5 LIVES)", 100,100,100, graphics));
        levelFinished = false;
    }

    // Shows GAME OVER menu
    void GameOverMenu() {
        gameObjects.add(new MenuBackground(-320, 20, 640, 130, 50, 50, 50));
        gameObjects.add(new Text(-125,-110, 40, "Bungee-Regular.ttf", "GAME OVER", 255,0,0, graphics));
        gameObjects.add(new Text(-60,-70, 20, "Bungee-Regular.ttf", mode_, 255,255,255, graphics));
        gameObjects.add(new Text(-45,-40, 20, "Bungee-Regular.ttf", "Score: "+ (currentLevel + 1), 255,255,255, graphics));
        gameObjects.add(new Button(-100,80, 180,20, ReturnMenu,"Bungee-Regular.ttf", "Return to Menu", 255,255,255, graphics));
        gamePaused = true;
    }

    // Shows WIN menu
    void WinMenu() {
        gameObjects.add(new MenuBackground(-320, 20, 640, 130, 50, 50, 50));
        gameObjects.add(new Text(-220,-100, 40, "Bungee-Regular.ttf", "CONGRATULATIONS", 255,255,0, graphics));
        gameObjects.add(new Text(-140,-70, 20, "Bungee-Regular.ttf", mode_ + " completed", 255,255,255, graphics));
        gameObjects.add(new Button(-100,-40, 180,20, ReturnMenu,"Bungee-Regular.ttf", "Return to Menu", 255,255,255, graphics));
        gamePaused = true;
        levelFinished = false;
    }
}