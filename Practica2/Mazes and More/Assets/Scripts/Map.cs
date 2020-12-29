using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Map
    {
        public int rows;
        public int cols;
        public JSONPoint start;
        public JSONPoint finish;
        public List<JSONPoint> hints;
        public List<JSONWall> walls;
        public List<JSONPoint> ice;
        public List<JSONPoint> enemies;
        public List<JSONPoint> traps;

        public Map(TextAsset json)
        {
            JSONMap jsonMap = (JSONMap)JsonUtility.FromJson<JSONMap>(json.text);
            rows = jsonMap.r;
            cols = jsonMap.c;
            start = jsonMap.s;
            finish = jsonMap.s;
            hints = jsonMap.h;
            walls = jsonMap.w;
            ice = jsonMap.i;
            enemies = jsonMap.e;
            traps = jsonMap.t;
        }
    }

    [System.Serializable]
    public class JSONMap
    {
        public int r;
        public int c;
        public JSONPoint s;
        public JSONPoint f;
        public List<JSONPoint> h;
        public List<JSONWall> w;
        public List<JSONPoint> i;
        public List<JSONPoint> e;
        public List<JSONPoint> t;
    }

    [System.Serializable]
    public class JSONPoint
    {
        public float x = 0;
        public float y = 0;
    }

    [System.Serializable]
    public class JSONWall
    {
        public JSONPoint o;
        public JSONPoint d;
    }
}