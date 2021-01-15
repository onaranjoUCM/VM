using UnityEngine;

namespace MazesAndMore {
    public class PlayerController : MonoBehaviour
    {
        public BoardManager boardManager;
        public ArrowPlayer arrowPrefab;
        public float speed = 5;

        GameObject arrow;

        private Vector3 target;
        private bool moving = false;
        private int direction;

        public void Init()
        {
            transform.position = boardManager.GetPlayerTile().transform.position;
            target = transform.position; 
            arrow = Instantiate(arrowPrefab.gameObject, transform.position, Quaternion.identity);
            arrow.transform.SetParent(transform);
            DisableArrows();
        }

        public void Run()
        {
            // Player is moving
            if (moving) {
                // Player has reached its target, so it turns or stops
                if (transform.position == target)
                {
                    moving = false;
                    // The target was a corner, so player must turn around
                    if (boardManager.GetPlayerTile().numberOfOpenSides == 2 && !boardManager.GetPlayerTile().iceFloor.isVisible)
                    {
                        for (int i = 0; i < 4; i++)
                        {
                            if (boardManager.GetPlayerTile().openSides[i] && i != Tile.opposite[direction])
                            {
                                StartMoving(i);
                                break;
                            }
                        }
                    }

                    // The target is an ice tile, so player must keep moving until it finds a wall
                    if (boardManager.GetPlayerTile().iceFloor.isVisible && boardManager.GetPlayerTile().openSides[direction])
                        StartMoving(direction);
                }
                else
                    transform.position = Vector3.MoveTowards(transform.position, target, speed * Time.deltaTime);

            // Player is not moving, so it can recieve move commands
            } else {
                ActiveArrows(boardManager.CanMove((int)Tile.SIDE.UP), boardManager.CanMove((int)Tile.SIDE.DOWN), boardManager.CanMove((int)Tile.SIDE.RIGHT), boardManager.CanMove((int)Tile.SIDE.LEFT));
                CheckInput();
            }
        }

        private void CheckInput()
        {
            if (Input.GetKey("up") && boardManager.CanMove((int)Tile.SIDE.UP))
                StartMoving((int)Tile.SIDE.UP);
            else if (Input.GetKey("down") && boardManager.CanMove((int)Tile.SIDE.DOWN))
                StartMoving((int)Tile.SIDE.DOWN);
            else if (Input.GetKey("left") && boardManager.CanMove((int)Tile.SIDE.LEFT))
                StartMoving((int)Tile.SIDE.LEFT);
            else if (Input.GetKey("right") && boardManager.CanMove((int)Tile.SIDE.RIGHT))
                StartMoving((int)Tile.SIDE.RIGHT);
        }

        private void StartMoving(int dir)
        {
            DisableArrows();
            moving = true;
            direction = dir;
            target = boardManager.MovePlayer(dir);
        }

        public void SetColor(Color c)
        {
            gameObject.GetComponent<SpriteRenderer>().color = c;
        }

        private void DisableArrows()
        {
            arrow.GetComponent<ArrowPlayer>().DisableUp();
            arrow.GetComponent<ArrowPlayer>().DisableDown();
            arrow.GetComponent<ArrowPlayer>().DisableRight();
            arrow.GetComponent<ArrowPlayer>().DisableLeft();

        }

        private void ActiveArrows(bool up, bool down, bool right, bool left)
        {
            if (up) arrow.GetComponent<ArrowPlayer>().EnableUp();
            if (down) arrow.GetComponent<ArrowPlayer>().EnableDown();
            if (right) arrow.GetComponent<ArrowPlayer>().EnableRight();
            if (left) arrow.GetComponent<ArrowPlayer>().EnableLeft();
        }
    }
}
