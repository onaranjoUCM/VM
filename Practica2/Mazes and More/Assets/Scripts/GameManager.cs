using UnityEngine;
using UnityEngine.SceneManagement;

namespace MazesAndMore
{
    public class GameManager : MonoBehaviour
    {
        public LevelManager levelManager;
        public MenuLevelManager menuLevelManager;
        static GameManager _instance;
        public LevelPackage[] levelPackages;

        private int nNiveles;
        private int packageIndex;
        private int nHints;
        private int[] levelsPassed;
        public string typeLevel;
        bool init = false;
#if UNITY_EDITOR
        public int levelToPlay;
#endif
        private void Start()
        {
            if (_instance != null)
            {
                _instance.levelManager = levelManager;
                _instance.menuLevelManager = menuLevelManager;
                if (menuLevelManager != null) _instance.menuLevelManager.init(_instance);

                if (levelManager != null)
                    _instance.loadLevel();

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
                    nHints = 0;
                    levelsPassed = new int[levelPackages.Length];
                    for (int i = 0; i < levelPackages.Length; i++)
                        levelsPassed[0] = 0;
                } else
                {
                    nHints = data.nHints;
                    levelsPassed = data.levelsPassed;
                }

                DontDestroyOnLoad(this.gameObject);
            }
        }

        private void Update()
        {
            if (levelManager != null)
            {
                if (levelManager.checkFinish())
                    levelPassed();
            }
        }

        public void loadLevelsScene(int n)
        {
            packageIndex = n;
            nNiveles = levelPackages[n].levels.Length;
            if (n == 0)
                typeLevel = "CLASICO";
            else
                typeLevel = "HIELO";
            SceneManager.LoadScene("MenuNiveles");
        }

        public void loadLevel()
        {
            levelManager.loadLevel(levelPackages[_instance.packageIndex], levelToPlay);
        }

        public int getPackageIndex()
        {
            return packageIndex;
        }

        public void sceneLevelPlay(int level)
        {
            levelToPlay = level;
            SceneManager.LoadScene("Game");
        }

        public void saveProgress()
        {
            GameData g = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress"));
            g.levelsPassed = levelsPassed;
            string json = JsonUtility.ToJson(g);
            PlayerPrefs.SetString("progress", json);

            /*string json = JsonUtility.ToJson(new GameData(nHints, levelsPassed));
            PlayerPrefs.SetString("progress", json);
            Debug.Log(PlayerPrefs.GetString("progress"));*/
        }

        public GameData getProgress()
        {
            return JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress"));
        }

        public void levelPassed()
        {
            if (levelsPassed[packageIndex] < levelToPlay + 1)
            {
                levelsPassed[packageIndex] = levelToPlay;
                saveProgress();
            }

            levelToPlay++;
            levelManager.loadLevel(levelPackages[_instance.packageIndex], levelToPlay);
        }

        public int getnNiveles() 
        {
            return nNiveles;
        }

        public void oneHint()
        {
            //nHints++;
            GameData g = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress"));
            g.nHints++;
            string json = JsonUtility.ToJson(g);
            PlayerPrefs.SetString("progress", json);
        }

        public int getHints()
        {
            return JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress")).nHints;
        }

        public void takeHints()
        {
            GameData g = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress"));
            g.nHints--;
            string json = JsonUtility.ToJson(g);
            PlayerPrefs.SetString("progress", json);
            levelManager.hud();
            levelManager.boardManager.activateHint(0);
        }
    }
}