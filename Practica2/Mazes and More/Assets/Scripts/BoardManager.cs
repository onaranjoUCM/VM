using UnityEngine;

namespace MazesAndMore {
    public class BoardManager : MonoBehaviour
    {
        public Tile tilePrefab;

        Map _map;
        Tile playerTile;
        Tile finishTile;

        private Tile[,] _tiles;

        // Creates and configures the graphic components of the map
        public void SetMap(Map map, Color playerColor)
        {
            _map = map;
            _tiles = new Tile[map.cols, map.rows];

            // Instantiate tiles
            for (int i = 0; i < map.cols; i++)
            {
                for (int j = map.rows - 1; j >= 0; j--)
                {
                    _tiles[i, j] = Instantiate(tilePrefab, new Vector3(i, j, -1f), Quaternion.identity);
                    _tiles[i, j].transform.parent = gameObject.transform;
                    _tiles[i, j].x = i;
                    _tiles[i, j].y = j;
                    _tiles[i, j].setPlayerColor(playerColor);
                    _tiles[i, j].setHintColor(Color.yellow);
                }
            }

            // Set ice tiles
            SetIceFloor(map);

            // Set start and finish
            finishTile = _tiles[(int)map.finish.x, (int)map.finish.y];
            finishTile.enableFinish();
            finishTile.setFinishColor(playerColor);
            playerTile = _tiles[(int)map.start.x, (int)map.start.y];

            // Set walls
            SetWalls(map);
            
        }

        // Adjusts size and position of the map to fit screen resolution
        public void AdjustToWindow()
        {
            // TODO: Revisar por qué se desplaza en los niveles mas grandes
            float scaleFactor;
            float w = Screen.width; float h = Screen.height;
            float c = _map.cols; float r = _map.rows;

            if (h > w)
                scaleFactor = (w / h) / c;
            else
                scaleFactor = (h / w) / r;

            scaleFactor *= 9;
            transform.localScale = new Vector3(transform.localScale.x * scaleFactor, transform.localScale.y * scaleFactor, transform.localScale.z);
            transform.Translate(Vector3.left * ((c-1) / 2.0f) * scaleFactor);
            transform.Translate(Vector3.down * (r / 2.0f) * scaleFactor);
        }

        // Returns whether or not the player tile is open in the given direction
        public bool CanMove(int dir)
        {
            // Check LEFT wall
            if (dir == (int)Tile.SIDE.LEFT)
                return playerTile.openSides[(int)Tile.SIDE.LEFT];

            // Since there are only LEFT and UP walls, to check RIGHT
            // we need to check the LEFT wall of the tile on the right
            if (dir == (int)Tile.SIDE.RIGHT && playerTile.x + 1 < _tiles.GetLength(0))
                return _tiles[playerTile.x + 1, playerTile.y].openSides[(int)Tile.SIDE.LEFT];

            // Check UP wall
            if (dir == (int)Tile.SIDE.UP)
                return playerTile.openSides[dir];

            // Since there are only LEFT and UP walls, to check DOWN
            // we need to check the UP wall of the tile underneath
            if (dir == (int)Tile.SIDE.DOWN && playerTile.y - 1 >= 0)
                return _tiles[playerTile.x, playerTile.y - 1].openSides[(int)Tile.SIDE.UP];

            // If you reach here it means you are trying to move RIGHT or DOWN out of the maze
            return false;
        }

        // Moves the player one tile in given direction 
        // and returns the position of the new tile
        public Vector3 MovePlayer(int dir)
        {
            Tile newTile = null;
            if (dir == (int)Tile.SIDE.LEFT)
            {
                newTile = _tiles[playerTile.x - 1, playerTile.y];
                newTile.timesSegmentCrossed[(int)Tile.SIDE.RIGHT]++;
                playerTile.timesSegmentCrossed[(int)Tile.SIDE.LEFT]++;
            }
            else if (dir == (int)Tile.SIDE.RIGHT)
            {
                newTile = _tiles[playerTile.x + 1, playerTile.y];
                newTile.timesSegmentCrossed[(int)Tile.SIDE.LEFT]--;
                playerTile.timesSegmentCrossed[(int)Tile.SIDE.RIGHT]--;
            }
            else if (dir == (int)Tile.SIDE.UP)
            {
                newTile = _tiles[playerTile.x, playerTile.y + 1];
                newTile.timesSegmentCrossed[(int)Tile.SIDE.DOWN]++;
                playerTile.timesSegmentCrossed[(int)Tile.SIDE.UP]++;
            }
            else if (dir == (int)Tile.SIDE.DOWN)
            {
                newTile = _tiles[playerTile.x, playerTile.y - 1];
                newTile.timesSegmentCrossed[(int)Tile.SIDE.UP]--;
                playerTile.timesSegmentCrossed[(int)Tile.SIDE.DOWN]--;
            }

            newTile.checkSegments();
            playerTile.checkSegments();
            playerTile = newTile;

            return newTile.transform.position;
        }

        // Auxiliary method to create and configure the walls
        private void SetWalls(Map map)
        {
            foreach (JSONWall wall in map.walls)
            {
                int x = (wall.o.x < wall.d.x) ? (int)wall.o.x : (int)wall.d.x;
                int y = (wall.o.y > wall.d.y) ? (int)wall.o.y : (int)wall.d.y;
                //int y = (int)wall.o.y;

                // Horizontal wall
                if (wall.o.y == wall.d.y)
                {
                    if (y > 0)
                    {
                        // TODO: El nivel 16 da un error aqui
                        _tiles[x, y - 1].enableUpWall();
                        if (y < map.rows)
                            _tiles[x, y].blockDownWall();
                    }
                    else
                        _tiles[x, y].enableDownWall();
                }

                // Vertical wall
                if (wall.o.x == wall.d.x)
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

        // Auxiliary method to create and configure the ice tiles
        private void SetIceFloor(Map map)
        {
            foreach (JSONPoint iceTile in map.ice)
            {
                _tiles[(int)iceTile.x, (int)iceTile.y].enableIce();
            }
        }

        // Delete all tiles and reset position and scale
        public void ClearAndReset()
        {
            transform.localScale = Vector3.one;
            transform.position = Vector3.zero;

            _map = null;
            if (_tiles != null)
            {
                foreach (Tile t in _tiles)
                {
                    Destroy(t.gameObject);
                }
            }
            _tiles = null;
        }

        // Return player to the start but keep the hints
        public void Reset(GameObject player)
        {
            playerTile = _tiles[(int)_map.start.x, (int)_map.start.y];
            player.transform.position = playerTile.transform.position;
            foreach (Tile t in _tiles)
            {
                if (!t.hintedSegments[(int)Tile.SIDE.LEFT])
                    t.disableSegment((int)Tile.SIDE.LEFT);
                else
                    t.setSegmentColor((int)Tile.SIDE.LEFT, Color.yellow);

                if (!t.hintedSegments[(int)Tile.SIDE.RIGHT])
                    t.disableSegment((int)Tile.SIDE.RIGHT);
                else
                    t.setSegmentColor((int)Tile.SIDE.RIGHT, Color.yellow);

                if (!t.hintedSegments[(int)Tile.SIDE.UP])
                    t.disableSegment((int)Tile.SIDE.UP);
                else
                    t.setSegmentColor((int)Tile.SIDE.UP, Color.yellow);


                if (!t.hintedSegments[(int)Tile.SIDE.DOWN])
                    t.disableSegment((int)Tile.SIDE.DOWN);
                else
                    t.setSegmentColor((int)Tile.SIDE.DOWN, Color.yellow);
            }
        }

        // Activates the n/3 hinted segments
        public void ActivateHint(int n)
        {
            if (n > 0 && n <= 3)
            {
                JSONPoint prevPoint = _map.start;

                float nHints = (float)_map.hints.Count * ((float)n / 3);
                for (int i = 0; i < nHints; i++)
                {
                    JSONPoint p = _map.hints[i];
                    Tile prevTile = _tiles[(int)prevPoint.x, (int)prevPoint.y];
                    Tile pTile = _tiles[(int)p.x, (int)p.y];

                    if (prevPoint.x == p.x) // Vertical segment
                    {
                        if (prevPoint.y < p.y)
                        {
                            prevTile.hintSegment((int)Tile.SIDE.UP);
                            pTile.hintSegment((int)Tile.SIDE.DOWN);
                        }
                        else
                        {
                            prevTile.hintSegment((int)Tile.SIDE.DOWN);
                            pTile.hintSegment((int)Tile.SIDE.UP);
                        }
                    }
                    else
                    {   // Horizontal segment
                        if (prevPoint.x < p.x)
                        {
                            prevTile.hintSegment((int)Tile.SIDE.RIGHT); ;
                            pTile.hintSegment((int)Tile.SIDE.LEFT);
                        }
                        else
                        {
                            prevTile.hintSegment((int)Tile.SIDE.LEFT);
                            pTile.hintSegment((int)Tile.SIDE.RIGHT);
                        }
                    }

                    prevPoint = p;
                }
            }
        }

        // GETTERS AND SETTERS
        public Tile GetTile(int x, int y)
        {
            return _tiles[x, y];
        }

        public Tile GetPlayerTile()
        {
            return playerTile;
        }

        public Tile GetFinishTile()
        {
            return finishTile;
        }
    }
}
