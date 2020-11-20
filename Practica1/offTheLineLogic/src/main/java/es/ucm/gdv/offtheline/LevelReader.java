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

    ArrayList<GameObject> loadLevel(int n) {
        boolean playerAdded = false;
        float playerX = 0; float playerY = 0;
        ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

        JSONObject level = (JSONObject) fullFile.get(n);
        String name = (String) level.get("name");
        String time = (String) level.get("time");

        // PATHS
        JSONArray paths = (JSONArray) level.get("paths");
        Iterator<JSONObject> itPath = paths.iterator();
        while (itPath.hasNext()) {
            JSONArray vertices = (JSONArray) itPath.next().get("vertices");
            Iterator<JSONObject> itVertices = vertices.iterator();
            List<float[]> vertexList = new ArrayList<>();
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
            gameObjects.add(new Path(vertexList));
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
                int length = (int) enemy.get("length");
                float angle = (Long) enemy.get("angle");
                gameObjects.add(new Enemy(xEnemy, yEnemy, length, 0.001f, angle, 0, 0, 0));
            }
        }

        // Add player last to render on top of everything
        gameObjects.add(new Player(playerX, playerY, 10, 10, 0.05f, 0, 45));

        return gameObjects;
    }
}
