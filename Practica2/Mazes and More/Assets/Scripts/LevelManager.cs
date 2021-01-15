using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore {
    public class LevelManager : MonoBehaviour
    {
        public TextAsset level; // PROVISIONAL
        public BoardManager boardManager;
        public PlayerController playerController;
        public Text title;
        public GameObject hintsButton;
        public GameObject pauseButton;

        LevelPackage pack;
        int nLevel;
        int hintsUsed;
        bool paused;

        void Start()
        {
#if UNITY_EDITOR
            if (boardManager == null)  {
                Debug.LogError("Missing board manager");
                gameObject.SetActive(false);
                return;
            }
#endif
            boardManager.Init(this);

            //  PROVISIONAL: Descomentar para no tener que ir desde el menu principal
            /*
            boardManager.reset();
            player.setColor(Color.green);
            boardManager.setMap(new Map(level), Color.green);
            boardManager.adjustToWindow();
            player.init();
            boardManager.activateHint(0);   // PROVISIONAL
            */
        }

        void Update()
        {
            if (!paused)
                playerController.Run();
        }

        // Returns whether or not the player has reached the finish
        public bool CheckFinish()
        {
            Tile playerTile = boardManager.GetPlayerTile();
            return (playerTile != null && playerTile == boardManager.GetFinishTile());
        }

        // Loads a level from given package
        public void LoadLevel(LevelPackage pack_, int level_)
        {
            paused = false;
            hintsUsed = 0;
            pack = pack_;
            nLevel = level_;
            boardManager.ClearAndReset();
            playerController.SetColor(pack.color);

            SetHud();

            boardManager.SetMap(new Map(pack.levels[nLevel]), pack.color);
            boardManager.AdjustToWindow();
            playerController.Init();
        }

        // Configures HUD according to level and screen size
        public void SetHud()
        {
            title.text = pack.packageName + " - " + (nLevel + 1).ToString();
            title.transform.position = new Vector3(Screen.width / 6, Screen.height - 60, 0);

            hintsButton.transform.position = new Vector3(Screen.width * 5 / 6, Screen.height - 60, 0);
            hintsButton.GetComponentInChildren<Text>().text = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress")).nHints.ToString();

            pauseButton.transform.position = new Vector3(Screen.width * 4 / 6, Screen.height - 60, 0);
        }

        public void UseHint()
        {
            boardManager.ActivateHint(++hintsUsed);
        }

        // Pauses the game and enables the pause menu
        public void PausePressed()
        {
            SetPaused(true);
            // TODO: Mostrar menu de pausa
        }

        // GETTERS AND SETTERS
        public void SetPaused(bool p)
        {
            paused = p;
        }
    }
}
