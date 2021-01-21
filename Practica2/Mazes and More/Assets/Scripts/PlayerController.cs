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

        private Vector2 fingerDown;
        private Vector2 fingerUp;
        public float SWIPE_THRESHOLD = 20f;

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
            // Input for android
            if (Application.platform == RuntimePlatform.Android)
            {
                foreach (Touch touch in Input.touches)
                {
                    if (touch.phase == TouchPhase.Began)
                    {
                        fingerUp = touch.position;
                        fingerDown = touch.position;
                    }

                    //Detects Swipe while finger is still moving
                    if (touch.phase == TouchPhase.Moved)
                    {
                        fingerDown = touch.position;
                        CheckSwipe();
                    }

                    //Detects swipe after finger is released
                    if (touch.phase == TouchPhase.Ended)
                    {
                        fingerDown = touch.position;
                        CheckSwipe();
                    }
                }
            }
            else 
            {
                // Input for PC
                if ((Input.GetKey("up") || Input.GetKey(KeyCode.W)) && boardManager.CanMove((int)Tile.SIDE.UP))
                    StartMoving((int)Tile.SIDE.UP);
                else if ((Input.GetKey("down") || Input.GetKey(KeyCode.S)) && boardManager.CanMove((int)Tile.SIDE.DOWN))
                    StartMoving((int)Tile.SIDE.DOWN);
                else if ((Input.GetKey("left") || Input.GetKey(KeyCode.A)) && boardManager.CanMove((int)Tile.SIDE.LEFT))
                    StartMoving((int)Tile.SIDE.LEFT);
                else if ((Input.GetKey("right") || Input.GetKey(KeyCode.D)) && boardManager.CanMove((int)Tile.SIDE.RIGHT))
                    StartMoving((int)Tile.SIDE.RIGHT);
            }
        }

        void CheckSwipe()
        {
            //Check if Vertical swipe
            if (VerticalMove() > SWIPE_THRESHOLD && VerticalMove() > HorizontalValMove())
            {
                if (fingerDown.y - fingerUp.y > 0) //up swipe
                    StartMoving((int)Tile.SIDE.UP);
                else if (fingerDown.y - fingerUp.y < 0) //Down swipe
                    StartMoving((int)Tile.SIDE.DOWN);

                fingerUp = fingerDown;
            }

            //Check if Horizontal swipe
            else if (HorizontalValMove() > SWIPE_THRESHOLD && HorizontalValMove() > VerticalMove())
            {
                if (fingerDown.x - fingerUp.x > 0) //Right swipe
                    StartMoving((int)Tile.SIDE.RIGHT);
                else if (fingerDown.x - fingerUp.x < 0) //Left swipe
                    StartMoving((int)Tile.SIDE.LEFT);

                fingerUp = fingerDown;
            }
        }

        float VerticalMove()
        {
            return Mathf.Abs(fingerDown.y - fingerUp.y);
        }

        float HorizontalValMove()
        {
            return Mathf.Abs(fingerDown.x - fingerUp.x);
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
            arrowPrefab.setColor(c);
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
