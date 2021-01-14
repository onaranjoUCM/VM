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
        LevelPackage pack;
        int nLevel;

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

        public void loadLevel(LevelPackage pack_, int level_)
        {
            pack = pack_;
            nLevel = level_;
            boardManager.reset();
            player.setColor(pack.color);

            hud();

            boardManager.setMap(new Map(pack.levels[nLevel]), pack.color);
            boardManager.adjustToWindow();
            player.init();
            //boardManager.activateHint(0);   // PROVISIONAL  
        }

        public void hud()
        {
            int i = nLevel + 1;
            if (pack.name == "ClassicGroup")
                title.text = "CLASSIC - " + i;
            else
                title.text = "ICEFLOOR - " + i;

            title.transform.position = new Vector3(Screen.width / 6, Screen.height - 60, 0);
            hints.transform.position = new Vector3(Screen.width * 5 / 6, Screen.height - 60, 0);
            hints.GetComponentInChildren<Text>().text = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress")).nHints.ToString();
        }
    }
}
