using UnityEngine;

namespace MazesAndMore {
    public class LevelManager : MonoBehaviour
    {
        public TextAsset level; // PROVISIONAL
        public BoardManager boardManager;
        public GameObject player;

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
            boardManager.activateHint(0);
        }
    }
}
