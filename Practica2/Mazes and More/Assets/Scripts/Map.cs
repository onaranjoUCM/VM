using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Map
    {
        JSONMap jsonMap;

        public Map(TextAsset json)
        {
            jsonMap = (JSONMap)JsonUtility.FromJson<JSONMap>(json.text);
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