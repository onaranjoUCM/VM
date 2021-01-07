using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {
    public class PlayerController : MonoBehaviour
    {
        public BoardManager boardManager;
        public float speed = 5;

        private Vector3 target;
        private bool moving = false;
        private int direction;

        public ArrowPlayer arrowPrefab;
        GameObject arrow;

        // Start is called before the first frame update
        void Start()
        {
            Invoke("init", 0.01f);
            arrow = Instantiate(arrowPrefab.gameObject, transform.position, Quaternion.identity);
            arrow.transform.SetParent(transform);
            DisableArrows();
        }

        private void init()
        {
            transform.position = boardManager.getPlayerTile().transform.position;
            target = transform.position;            
        }

        // Update is called once per frame
        void Update()
        {
            // Player is moving
            if (moving) {
                // Player has reached its target, so it turns or stops
                if (transform.position == target)
                {
                    moving = false;
                    // The target was a corner, so player must turn around
                    if (boardManager.getPlayerTile().numberOfOpenSides == 2 && !boardManager.getPlayerTile().iceFloor.isVisible)
                    {
                        for (int i = 0; i < 4; i++)
                        {
                            if (boardManager.getPlayerTile().openSides[i] && i != Tile.opposite[direction])
                            {
                                startMoving(i);
                                break;
                            }
                        }
                    }

                    // The target is an ice tile, so player must keep moving until it finds a wall
                    if (boardManager.getPlayerTile().iceFloor.isVisible && boardManager.getPlayerTile().openSides[direction])
                        startMoving(direction);
                }
                else
                    transform.position = Vector3.MoveTowards(transform.position, target, speed * Time.deltaTime);

            // Player is not moving, so it can recieve move commands
            } else {
                ActiveArrows(boardManager.canMove((int)Tile.SIDE.UP), boardManager.canMove((int)Tile.SIDE.DOWN), boardManager.canMove((int)Tile.SIDE.RIGHT), boardManager.canMove((int)Tile.SIDE.LEFT));
                
                if (Input.GetKey("up") && boardManager.canMove((int)Tile.SIDE.UP))
                    startMoving((int)Tile.SIDE.UP);
                else if (Input.GetKey("down") && boardManager.canMove((int)Tile.SIDE.DOWN))
                    startMoving((int)Tile.SIDE.DOWN);
                else if (Input.GetKey("left") && boardManager.canMove((int)Tile.SIDE.LEFT))
                    startMoving((int)Tile.SIDE.LEFT);
                else if (Input.GetKey("right") && boardManager.canMove((int)Tile.SIDE.RIGHT))
                    startMoving((int)Tile.SIDE.RIGHT);
            }
        }

        private void startMoving(int dir)
        {
            DisableArrows();
            moving = true;
            direction = dir;
            target = boardManager.findTarget(dir);
        }

        public void setColor(Color c)
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
            if (up)
            {
                arrow.GetComponent<ArrowPlayer>().EnableUp();
            }
            if (down)
            {
                arrow.GetComponent<ArrowPlayer>().EnableDown();
            }
            if (right)
            {
                arrow.GetComponent<ArrowPlayer>().EnableRight();
            }
            if (left)
            {
                arrow.GetComponent<ArrowPlayer>().EnableLeft();
            }
        }
    }
}
