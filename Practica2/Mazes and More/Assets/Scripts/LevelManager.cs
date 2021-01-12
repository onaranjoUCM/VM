using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore {
    public class LevelManager : MonoBehaviour
    {
        public TextAsset level; // PROVISIONAL
        public BoardManager boardManager;
        public PlayerController player;
        public Text title;
        public GameObject hints;

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

            hud(pack, level);

            boardManager.setMap(new Map(pack.levels[level]), pack.color);
            boardManager.adjustToWindow();
            player.init();
            boardManager.activateHint(0);   // PROVISIONAL  
        }

        public void hud(LevelPackage pack, int level)
        {
            if(pack.name == "ClassicGroup")
                title.text = "CLASSIC_" + level;
            else
                title.text = "ICEFLOOR_" + level;

            title.transform.position = new Vector3(Screen.width / 6, Screen.height - 60, 0);
            hints.transform.position = new Vector3(Screen.width* 5 / 6, Screen.height - 60, 0);
        }
    }
}
