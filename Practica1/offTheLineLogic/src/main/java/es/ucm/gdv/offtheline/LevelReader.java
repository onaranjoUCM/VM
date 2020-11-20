package es.ucm.gdv.offtheline;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LevelReader {
    JSONParser parser;
    JSONArray fullFile;

    LevelReader() {
        parser = new JSONParser();
        try (Reader reader = new FileReader("levels.json")) {
            fullFile = (JSONArray) parser.parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    ArrayList<GameObject> loadLevel(int levelIndex) {
        boolean playerAdded = false;
        float playerX = 0; float playerY = 0;
        ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

        JSONObject level = (JSONObject) fullFile.get(levelIndex);
        String name = (String) level.get("name");
        String time = (String) level.get("time");

        // PATHS
        JSONArray paths = (JSONArray) level.get("paths");
        Iterator<JSONObject> itPath = paths.iterator();
        while (itPath.hasNext()) {
            List<float[]> vertexList = new ArrayList<>();
            List<float[]> directionsList = new ArrayList<>();
            JSONObject path = itPath.next();

            // VERTICES
            JSONArray vertices = (JSONArray) path.get("vertices");
            Iterator<JSONObject> itVertices = vertices.iterator();
            while (itVertices.hasNext()) {
                JSONObject vertex = itVertices.next();
                float xVertex = (Long) vertex.get("x");
                float yVertex = (Long) vertex.get("y");
                float tuple[] = {xVertex, yVertex};
                vertexList.add(tuple);

                if(!playerAdded) {
                    playerX = xVertex; playerY = yVertex;
                    playerAdded = true;
                }
            }

            // DIRECTIONS
            JSONArray directions = (JSONArray) path.get("directions");
            if (directions != null) {
                Iterator<JSONObject> itDirections = directions.iterator();
                while (itDirections.hasNext()) {
                    JSONObject vertex = itDirections.next();
                    float xDir = (Long) vertex.get("x");
                    float yDir = (Long) vertex.get("y");
                    float tuple[] = {xDir, yDir};
                    vertexList.add(tuple);
                }
            }

            gameObjects.add(new Path(vertexList, directionsList));
        }

        // ITEMS
        JSONArray items = (JSONArray) level.get("items");
        Iterator<JSONObject> itItems = items.iterator();
        while (itItems.hasNext()) {
            JSONObject vertex = itItems.next();
            float xItem = (Long) vertex.get("x");
            float yItem = (Long) vertex.get("y");
            gameObjects.add(new Coin(xItem, yItem, 10, 10, 0.05f, 0, 45));
        }

        // ENEMIES
        JSONArray enemies = (JSONArray) level.get("enemies");
        if (enemies != null) {
            Iterator<JSONObject> itEnemies = enemies.iterator();
            while (itEnemies.hasNext()) {
                JSONObject enemy = itEnemies.next();
                float xEnemy = (Long) enemy.get("x");
                float yEnemy = (Long) enemy.get("y");
                float length = (Long) enemy.get("length");
                float angle = (Long) enemy.get("angle");

                float speed;
                try {
                    speed = (Long) enemy.get("speed");
                } catch (NullPointerException e) {
                    speed = 0;
                }

                JSONObject offset;
                float offsetX;
                float offsetY;
                try {
                    offset = (JSONObject) enemy.get("offset");
                    offsetX = (Long)offset.get("x");
                    offsetY = (Long)offset.get("y");
                } catch (NullPointerException e) {
                    offsetX = 0;
                    offsetY = 0;
                }

                float time1;
                try {
                    time1 = (Long) enemy.get("time1");
                } catch (NullPointerException e) {
                    time1 = 0;
                }

                float time2;
                try {
                    time2 = (Long) enemy.get("time2");
                } catch (NullPointerException e) {
                    time2 = 0;
                }

                gameObjects.add(new Enemy(xEnemy, yEnemy, (int)length, angle, speed, offsetX, offsetY, time1, time2));
            }
        }

        // Add player last to render on top of everything
        gameObjects.add(new Player(playerX, playerY, 10, 10, 0.05f, 0, 45));

        return gameObjects;
    }
}
