using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {
    public class BoardManager : MonoBehaviour
    {
        public Tile tilePrefab;

        private Tile[,] _tiles;
        private LevelManager _levelManager;

        public void init(LevelManager levelManager)
        {
            _levelManager = levelManager;
        }

        public void setMap(Map map)
        {
            _tiles = new Tile[map.cols, map.rows];

            // Instantiate tiles
            for (int i = 0; i < map.cols; i++)
            {
                for (int j = 0; j < map.rows; j++)
                {
                    _tiles[i, j] = Instantiate(tilePrefab, new Vector3(i, j, 0), Quaternion.identity);
                    _tiles[i, j].transform.parent = gameObject.transform;
                    _tiles[i, j].enableIce(); // PROVISIONAL
                }
            }

            // Set start and finish
            _tiles[(int)map.start.x, (int)map.start.y].enableStart();
            _tiles[(int)map.finish.x, (int)map.finish.y].enableFinish();

            // Set walls
            foreach (JSONWall wall in map.walls)
            {
                if (wall.o.x == wall.d.x) { // Horizontal wall
                    // TODO
                } else {                    // Vertical wall
                    // TODO
                }
            }

            // Adjust to window
            transform.Translate(Vector3.left * (map.cols / 2));
            transform.Translate(Vector3.down * (map.rows / 2));
        }
    }
}
