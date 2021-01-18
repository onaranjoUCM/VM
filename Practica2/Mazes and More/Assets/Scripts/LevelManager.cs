using UnityEngine;
using UnityEngine.SceneManagement;
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
        public GameObject pauseMenu;
        public GameObject shopMenu;
        public GameObject winMenu;

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
        }

        void Update()
        {
            if (!paused)
            {
                playerController.Run();
                if (CheckFinish())
                {
                    SetPaused(true);
                    GameManager.getInstance().LevelPassed();
                    winMenu.gameObject.SetActive(true);
                }
            }

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

        // Button functions
        public void NextLevel()
        {
            winMenu.gameObject.SetActive(false);
            GameManager.getInstance().LoadLevel();
        }

        public void Reset()
        {
            boardManager.Reset(playerController.gameObject);
        }

        public void UseHint()
        {
            GameManager.getInstance().UseHint();
            boardManager.ActivateHint(++hintsUsed);
            SetHud();
        }

        public void AddHints(int n)
        {
            GameManager.getInstance().AddHints(n);
            SetHud();
        }

        public void resumeGame()
        {
            SetPaused(false);
            pauseMenu.SetActive(false);
        }

        public void PausePressed()
        {
            SetPaused(true);
            pauseMenu.SetActive(true);
        }

        public void returnHome()
        {
            SceneManager.LoadScene("Menu");
        }

        public void OpenShop()
        {
            SetPaused(true);
            shopMenu.gameObject.SetActive(true);
        }

        public void CloseShop()
        {
            SetPaused(false);
            shopMenu.gameObject.SetActive(false);
        }

        // GETTERS AND SETTERS
        public void SetPaused(bool p)
        {
            paused = p;
        }
    }
}
