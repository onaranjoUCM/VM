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

    float readFloat(Object o) {
        try {
            return (Long) o;
        } catch(ClassCastException e) {
            double aux = (double)o;
            return (float)aux;
        }
    }

    ArrayList<GameObject> loadLevel(int levelIndex) {
        boolean playerAdded = false;
        Path playerPath = null;
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
                float xVertex = readFloat(vertex.get("x"));
                float yVertex = readFloat(vertex.get("y"));
                float tuple[] = {xVertex, yVertex};
                vertexList.add(tuple);
            }

            // DIRECTIONS
            JSONArray directions = (JSONArray) path.get("directions");
            if (directions != null) {
                Iterator<JSONObject> itDirections = directions.iterator();
                while (itDirections.hasNext()) {
                    JSONObject vertex = itDirections.next();
                    float xDir = readFloat(vertex.get("x"));
                    float yDir = readFloat(vertex.get("y"));
                    float tuple[] = {xDir, yDir};
                    directionsList.add(tuple);
                }
            }

            Path aux = new Path(vertexList, directionsList);
            if(!playerAdded) {
                playerPath = aux;
                playerAdded = true;
            }
            gameObjects.add(aux);
        }

        // ITEMS
        JSONArray items = (JSONArray) level.get("items");
        Iterator<JSONObject> itItems = items.iterator();
        while (itItems.hasNext()) {
            JSONObject item = itItems.next();
            float xItem = readFloat(item.get("x"));
            float yItem = readFloat(item.get("y"));

            float radius;
            try {
                radius = readFloat(item.get("radius"));
            } catch (NullPointerException e) {
                radius = 0;
            }

            float speed;
            try {
                speed = readFloat(item.get("speed"));
            } catch (NullPointerException e) {
                speed = 0;
            }

            float angle;
            try {
                angle = readFloat(item.get("angle"));
            } catch (NullPointerException e) {
                angle = 0;
            }

            gameObjects.add(new Coin(xItem, yItem, radius, speed, angle));
        }

        // ENEMIES
        JSONArray enemies = (JSONArray) level.get("enemies");
        if (enemies != null) {
            Iterator<JSONObject> itEnemies = enemies.iterator();
            while (itEnemies.hasNext()) {
                JSONObject enemy = itEnemies.next();
                float xEnemy = readFloat(enemy.get("x"));
                float yEnemy = readFloat(enemy.get("y"));
                float length = readFloat(enemy.get("length"));
                float angle = readFloat(enemy.get("angle"));

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
                    offsetX = readFloat(offset.get("x"));
                    offsetY = readFloat(offset.get("y"));
                } catch (NullPointerException e) {
                    offsetX = 0;
                    offsetY = 0;
                }

                float time1;
                try {
                    time1 = readFloat(enemy.get("time1"));
                } catch (NullPointerException e) {
                    time1 = 0;
                }

                float time2;
                try {
                    time2 = readFloat(enemy.get("time2"));
                } catch (NullPointerException e) {
                    time2 = 0;
                }

                gameObjects.add(new Enemy(xEnemy, yEnemy, (int)length, angle, speed, offsetX, offsetY, time1, time2));
            }
        }

        // Add player last to render on top of everything
        gameObjects.add(new Player(playerPath, 10, 10, 0.05f, 45));
        gameObjects.add(new Lives(50,-150, 100, 20, 5));

        return gameObjects;
    }
}
