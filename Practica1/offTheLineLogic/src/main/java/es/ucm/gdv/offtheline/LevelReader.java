package es.ucm.gdv.offtheline;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

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

    void loadLevel(int n) {
        JSONObject level = (JSONObject) fullFile.get(n);
        String name = (String) level.get("name");
        String time = (String) level.get("time");

        JSONArray paths = (JSONArray) level.get("paths");
        Iterator<JSONObject> itPath = paths.iterator();
        while (itPath.hasNext()) {
            JSONArray vertices = (JSONArray) itPath.next().get("vertices");
            Iterator<JSONObject> itVertices = vertices.iterator();
            while (itVertices.hasNext()) {
                JSONObject vertex = itVertices.next();
                Long xVertex= (Long) vertex.get("x");
                Long yVertex = (Long) vertex.get("y");
            }
        }

        JSONArray items = (JSONArray) level.get("items");
        Iterator<JSONObject> itItems = items.iterator();
        while (itItems.hasNext()) {
            JSONObject vertex = itItems.next();
            Long xItem = (Long) vertex.get("x");
            Long yItem = (Long) vertex.get("y");
        }
    }
}
