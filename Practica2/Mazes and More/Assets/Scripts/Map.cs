using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Map
    {
        JSONMap map;
        JSONPoint test;

        public Map(string json)
        {
            //map = JsonUtility.FromJson<JSONMap>("/Levels/classic/0.json");
            test = JsonUtility.FromJson<JSONPoint>("/Levels/classic/0.json");
        }
    }

    [System.Serializable]
    public class JSONMap
    {
        int r;
        int c;
        //JSONPoint s;
        //JSONPoint f;
        //List<JSONPoint> h;
        //List<JSONWall> w;
        //List<JSONPoint> i;
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