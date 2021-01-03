using UnityEngine;

namespace MazesAndMore {
    public class BoardManager : MonoBehaviour
    {
        public Tile tilePrefab;

        private Tile[,] _tiles;
        private LevelManager _levelManager;

        Tile playerTile;

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
                    _tiles[i, j].x = i;
                    _tiles[i, j].y = j;
                }
            }

            // Set start and finish
            _tiles[(int)map.finish.x, (int)map.finish.y].enableFinish();
            playerTile = _tiles[(int)map.start.x, (int)map.start.y];

            // Set walls
            setWalls(map);

            // Adjust to window
            Vector3 scale = transform.localScale;
            float scaleFactor = (float)(5.625 / map.cols);
            transform.localScale = new Vector3(scale.x * scaleFactor, scale.y * scaleFactor, scale.z);
            transform.Translate(Vector3.left * (map.cols / 2) * scaleFactor);
            transform.Translate(Vector3.down * (map.rows / 2) * scaleFactor);
        }

        public bool canMove(int dir)
        {
            // Check LEFT wall
            if (dir == (int)Utils.SIDE.LEFT)
                return playerTile.openSides[(int)Utils.SIDE.LEFT];

            // Since there are only LEFT and UP walls, to check RIGHT
            // we need to check the LEFT wall of the tile on the right
            if (dir == (int)Utils.SIDE.RIGHT && playerTile.x + 1 < _tiles.GetLength(0))
                    return _tiles[playerTile.x + 1, playerTile.y].openSides[(int)Utils.SIDE.LEFT];

            // Check UP wall
            if (dir == (int)Utils.SIDE.UP)
                return playerTile.openSides[dir];

            // Since there are only LEFT and UP walls, to check DOWN
            // we need to check the UP wall of the tile underneath
            if (dir == (int)Utils.SIDE.DOWN && playerTile.y - 1 >= 0)
                return _tiles[playerTile.x, playerTile.y - 1].openSides[(int)Utils.SIDE.UP];

            // If we reach here it means we are trying to move RIGHT or DOWN out of the maze
            return false;
        }

        public Vector3 movePlayer(int dir, float speed)
        {
            Tile newTile = null;
            if (dir == (int)Utils.SIDE.LEFT)
            {
                newTile = _tiles[playerTile.x - 1, playerTile.y];
                newTile.toggleRightSegment();
                playerTile.toggleLeftSegment();
            }
            else if (dir == (int)Utils.SIDE.RIGHT)
            {
                newTile = _tiles[playerTile.x + 1, playerTile.y];
                newTile.toggleLeftSegment();
                playerTile.toggleRightSegment();
            }
            else if (dir == (int)Utils.SIDE.UP)
            {
                newTile = _tiles[playerTile.x, playerTile.y + 1];
                newTile.toggleDownSegment();
                playerTile.toggleUpSegment();
            }
            else if (dir == (int)Utils.SIDE.DOWN)
            {
                newTile = _tiles[playerTile.x, playerTile.y - 1];
                newTile.toggleUpSegment();
                playerTile.toggleDownSegment();
            }

            playerTile = newTile;

            return newTile.transform.position;
        }

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
                    {
                        _tiles[x, y - 1].enableUpWall();
                        if (y < map.rows)
                            _tiles[x, y].blockDownWall();
                    }
                    else
                        _tiles[x, y].enableDownWall();
                }

                // Vertical wall
                if (x == wall.d.x)
                {
                    if (x < map.cols)
                    {
                        _tiles[x, y - 1].enableLeftWall();
                        if (x - 1 >= 0)
                            _tiles[x - 1, y - 1].blockRightWall();
                    }
                    else
                        _tiles[x - 1, y - 1].enableRightWall();
                }
            }
        }

        public Tile getTile(int x, int y)
        {
            return _tiles[x, y];
        }

        public Tile getPlayerTile()
        {
            return playerTile;
        }
    }
}
