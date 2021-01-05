using UnityEngine;

namespace MazesAndMore {
    public class LevelManager : MonoBehaviour
    {
        public TextAsset level; // PROVISIONAL
        public BoardManager boardManager;
        public PlayerController player;

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
            player.setColor(Color.green);
            boardManager.setMap(new Map(level), Color.green);
            boardManager.activateHint(0);
        }
    }
}
