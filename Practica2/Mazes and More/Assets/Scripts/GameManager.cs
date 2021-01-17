using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class GameManager : MonoBehaviour
    {
        public LevelManager levelManager;
        public MenuLevelManager menuLevelManager;
        public LevelPackage[] levelPackages;
        public GameObject hintsButton;
        
        private static GameManager _instance;

        private int nNiveles;
        private int packageIndex;
        private int[] levelsPassed;

#if UNITY_EDITOR
        public int levelToPlay;
#endif
        private void Awake()
        {
            //PlayerPrefs.DeleteAll();
            if (_instance != null)
            {
                _instance.levelManager = levelManager;
                _instance.menuLevelManager = menuLevelManager;
                _instance.hintsButton = hintsButton;

                if (menuLevelManager != null) 
                    _instance.menuLevelManager.init(_instance);

                if (levelManager != null)
                    _instance.LoadLevel();

                // REVISAR (NO FUNCIONA)
                if (hintsButton != null)
                    hintsButton.GetComponent<Button>().onClick.AddListener(() => takeHints());

                DestroyImmediate(gameObject);
                return;
            }
            else
            {
                _instance = this;
                if (menuLevelManager != null) _instance.menuLevelManager.init(_instance);

                // Load game progress from PlayerPrefs
                GameData data = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress"));
                if (data == null)
                {
                    levelsPassed = new int[levelPackages.Length];
                    for (int i = 0; i < levelPackages.Length; i++)
                        levelsPassed[i] = 0;

                    data = new GameData(2, levelsPassed);
                    PlayerPrefs.SetString("progress", JsonUtility.ToJson(data));
                } else
                {
                    levelsPassed = data.levelsPassed;
                }
                

                DontDestroyOnLoad(this.gameObject);
            }
        }

        private void Update()
        {
            if (Input.GetKeyDown(KeyCode.H))
                takeHints();
        }

        public void LoadLevelsScene(int n)
        {
            packageIndex = n;
            nNiveles = levelPackages[n].levels.Length;
            SceneManager.LoadScene("MenuNiveles");
        }

        public void LoadLevel()
        {
            levelManager.LoadLevel(levelPackages[_instance.packageIndex], levelToPlay);
        }

        public int GetPackageIndex()
        {
            return packageIndex;
        }

        public void SceneLevelPlay(int level)
        {
            levelToPlay = level;
            SceneManager.LoadScene("Game");
        }

        public void SaveProgress()
        {
            GameData g = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress"));
            g.levelsPassed = levelsPassed;
            PlayerPrefs.SetString("progress", JsonUtility.ToJson(g));
        }

        public void LevelPassed()
        {
            if (levelsPassed[packageIndex] < levelToPlay + 1)
            {
                levelsPassed[packageIndex] = levelToPlay;
                SaveProgress();
            }

            levelToPlay++;
        }

        public void AddHints(int n)
        {
            GameData g = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress"));
            g.nHints += n;
            string json = JsonUtility.ToJson(g);
            PlayerPrefs.SetString("progress", json);
        }

        public void takeHints()
        {
            GameData g = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress"));
            g.nHints--;
            string json = JsonUtility.ToJson(g);
            PlayerPrefs.SetString("progress", json);
            levelManager.SetHud();
            levelManager.UseHint();
        }

        // GETTERS AND SETTERS
        public static GameManager getInstance()
        {
            if (_instance == null)
            {
                _instance = new GameManager();
            }
            return _instance;
        }

        public int getnNiveles() 
        {
            return nNiveles;
        }

        public int getHints()
        {
            return JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress")).nHints;
        }
    }
}