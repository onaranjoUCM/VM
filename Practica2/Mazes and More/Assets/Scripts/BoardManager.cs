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
                for (int j = map.rows - 1; j >= 0; j--)
                {
                    _tiles[i, j] = Instantiate(tilePrefab, new Vector3(i, j, 0), Quaternion.identity);
                    _tiles[i, j].transform.parent = gameObject.transform;
                }
            }

            // Set start and finish
            _tiles[(int)map.start.x, (int)map.start.y].enableStart();
            _tiles[(int)map.finish.x, (int)map.finish.y].enableFinish();

            // Set walls
            setWalls(map);

            // Adjust to window
            Vector3 scale = transform.localScale;
            float scaleFactor = (float)(5.625 / map.cols);
            transform.localScale = new Vector3(scale.x * scaleFactor, scale.y * scaleFactor, scale.z);
            transform.Translate(Vector3.left * (map.cols / 2) * scaleFactor);
            transform.Translate(Vector3.down * (map.rows / 2) * scaleFactor);
        }
        /*
        public bool canMove(int x, int y, int dir)
        {
            return _tiles[x, y].openSides[dir];
        }
        */
        private void setWalls(Map map)
        {
            foreach (JSONWall wall in map.walls)
            {
                int x = (int)wall.o.x;
                int y = (int)wall.o.y;

                // Horizontal wall
                if (y == wall.d.y)
                {
                    if (y > 0)
                        _tiles[x, y - 1].enableUpWall();
                    else
                        _tiles[x, y].enableDownWall();
                }

                // Vertical wall
                if (x == wall.d.x)
                {
                    if (x < map.cols)
                        _tiles[x, y - 1].enableLeftWall();
                    else
                        _tiles[x - 1, y - 1].enableRightWall();
                }
            }
        }
    }
}
