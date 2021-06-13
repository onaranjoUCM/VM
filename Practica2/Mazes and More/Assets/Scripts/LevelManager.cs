using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

namespace MazesAndMore {
    public class LevelManager : MonoBehaviour
    {
        public BoardManager boardManager;
        public PlayerController playerController;
        public Text title;
        public GameObject hintsButton;
        public GameObject topMenu;
        public GameObject pauseMenu;
        public GameObject shopMenu;
        public GameObject winMenu;
        public GameObject ads;

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
                    topMenu.gameObject.SetActive(false);
                    boardManager.gameObject.SetActive(false);

                    if (GameManager.getInstance().GetPlayerData().adsEnabled)
                        ads.AddComponent<Ads>().ShowInterstitialAd("nextlevel");

                    winMenu.gameObject.SetActive(true);
                }
            }

        }

        // Returns whether or not the player has reached the finish tile
        public bool CheckFinish()
        {
            return playerController.getWin();
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

            SetHudText();

            boardManager.SetMap(new Map(pack.levels[nLevel]), pack.color);
            playerController.PutArrow();
            boardManager.AdjustToWindow();
            playerController.Init();
        }

        // Configures HUD text according to level name and number of hints
        public void SetHudText()
        {
            title.text = pack.packageName + " - " + (nLevel + 1).ToString();
            hintsButton.GetComponentInChildren<Text>().text = GameManager.getInstance().GetPlayerData().nHints.ToString();
        }

        // BUTTON FUNCTIONS
        public void NextLevel()
        {
            winMenu.gameObject.SetActive(false);
            boardManager.gameObject.SetActive(true);
            topMenu.gameObject.SetActive(true);
            GameManager.getInstance().LoadLevel();
        }

        public void Reset()
        {
            boardManager.Reset(playerController.gameObject);
        }

        public void UseHint()
        {
            GameData g = GameManager.getInstance().GetPlayerData();
            if (g.nHints > 0)
            {
                GameManager.getInstance().AddHints(-1);
                boardManager.ActivateHint(++hintsUsed);
                SetHudText();
            }
            else
                OpenShop();
        }

        public void AddHints(int n)
        {
            GameManager.getInstance().AddHints(n);
            SetHudText();
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

        public void DisableAds()
        {
            GameManager.getInstance().DisableAds();
        }

        // GETTERS AND SETTERS
        public void SetPaused(bool p)
        {
            paused = p;
        }
    }
}
