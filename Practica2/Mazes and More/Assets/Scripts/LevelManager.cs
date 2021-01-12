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

            //  Descomentar para no tener que ir desde el menu principal
            /*
            boardManager.reset();
            player.setColor(Color.green);
            boardManager.setMap(new Map(level), Color.green);
            boardManager.adjustToWindow();
            player.init();
            boardManager.activateHint(0);   // PROVISIONAL
            */
        }

        public bool checkFinish()
        {
            Tile playerTile = boardManager.getPlayerTile();
            return (playerTile != null && playerTile == boardManager.getFinishTile());
        }

        public void loadLevel(LevelPackage pack, int level)
        {
            boardManager.reset();
            player.setColor(pack.color);
            boardManager.setMap(new Map(pack.levels[level]), pack.color);
            boardManager.adjustToWindow();
            player.init();
            boardManager.activateHint(0);   // PROVISIONAL  
        }
    }
}
