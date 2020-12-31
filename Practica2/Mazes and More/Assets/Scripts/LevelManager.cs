using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {
    public class LevelManager : MonoBehaviour
    {
        public TextAsset level; // PROVISIONAL
        public BoardManager boardManager;

        public GameObject player;
        public float playerSpeed = 5;
        private Vector3 target;

        void Start()
        {
#if UNITY_EDITOR
            if (boardManager == null)  {
                Debug.LogError("Missing board manager");
                gameObject.SetActive(false);
                return;
            }
#endif
            boardManager.init(this);
            Map map = new Map(level);
            boardManager.setMap(map);

            player.transform.position = boardManager.getTile((int)map.start.x, (int)map.start.y).transform.position;
            target = player.transform.position;
        }

        void Update()
        {
            if (player.transform.position == target)
            {
                if (Input.GetKey("up") && boardManager.canMove((int)SIDE.UP))
                {
                    target = boardManager.movePlayer((int)SIDE.UP);
                }
                else if (Input.GetKey("down") && boardManager.canMove((int)SIDE.DOWN))
                {
                    target = boardManager.movePlayer((int)SIDE.DOWN);
                }
                else if (Input.GetKey("left") && boardManager.canMove((int)SIDE.LEFT))
                {
                    target = boardManager.movePlayer((int)SIDE.LEFT);
                }
                else if (Input.GetKey("right") && boardManager.canMove((int)SIDE.RIGHT))
                {
                    target = boardManager.movePlayer((int)SIDE.RIGHT);
                }
            }

            float step = playerSpeed * Time.deltaTime;
            player.transform.position = Vector3.MoveTowards(player.transform.position, target, step);
        }
    }
}
