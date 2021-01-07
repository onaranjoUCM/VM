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

        // Start is called before the first frame update
        void Start()
        {
            Invoke("init", 0.01f);
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
            moving = true;
            direction = dir;
            target = boardManager.findTarget(dir);
        }

        public void setColor(Color c)
        {
            gameObject.GetComponent<SpriteRenderer>().color = c;
        }
    }
}
