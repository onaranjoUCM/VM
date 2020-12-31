using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {
    public class LevelManager : MonoBehaviour
    {
        public TextAsset level; // PROVISIONAL
        public BoardManager boardManager;

        // Start is called before the first frame update
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
        }

        // Update is called once per frame
        void Update()
        {
            if (Input.GetKey("up") && boardManager.canMove((int)SIDE.UP)) {
                boardManager.movePlayer((int)SIDE.UP);
            } 
            else if (Input.GetKey("down") && boardManager.canMove((int)SIDE.DOWN)) {
                boardManager.movePlayer((int)SIDE.DOWN);
            }
            else if (Input.GetKey("left") && boardManager.canMove((int)SIDE.LEFT)) {
                boardManager.movePlayer((int)SIDE.LEFT);
            }
            else if (Input.GetKey("right") && boardManager.canMove((int)SIDE.RIGHT)) {
                boardManager.movePlayer((int)SIDE.RIGHT);
            }
        }
    }
}
