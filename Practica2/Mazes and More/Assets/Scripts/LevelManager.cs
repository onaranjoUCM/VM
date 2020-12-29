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
            boardManager.setMap(new Map(level));
        }

        // Update is called once per frame
        void Update()
        {

        }
    }
}
