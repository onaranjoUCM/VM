using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {
    public class LevelManager : MonoBehaviour
    {
        public TextAsset level; // PROVISIONAL
        public BoardManager boardManager;

        Vector2 currentPos;

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
            currentPos = new Vector2(map.start.x, map.start.y);
        }

        // Update is called once per frame
        void Update()
        {
            if (Input.GetKey("up"))
            {
                /*if (boardManager.canMove(currentPos.x, currentPos.y, 0))    // TODO: Usar enum SIDE en el 3º parametro
                {
                    boardManager.moveUp();
                    currentPos.y += 1;
                }*/
            } 
            else if (Input.GetKey("down"))
            {
                // TODO
            }
            else if (Input.GetKey("left"))
            {
                // TODO
            }
            else if (Input.GetKey("right"))
            {
                // TODO
            }
        }
    }
}
