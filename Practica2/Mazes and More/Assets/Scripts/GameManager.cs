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

#if UNITY_EDITOR
        public int levelToPlay;
#endif
        void Start()
        {
            if (_instance != null)
            {
                _instance.levelManager = levelManager;
                _instance.menuLevelManager = menuLevelManager;
                if (menuLevelManager != null) _instance.menuLevelManager.init(_instance);
                DestroyImmediate(gameObject);

                if (levelManager != null)
                    levelManager.loadLevel(levelPackages[_instance.packageIndex], levelToPlay);

                return;
            }
            else
            {
                _instance = this;
                if (menuLevelManager != null) _instance.menuLevelManager.init(_instance);
                DontDestroyOnLoad(this.gameObject);

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
            }
        }

        private void Update()
        {
            if (levelManager != null)
                if(levelManager.checkFinish())
                    levelPassed();
        }

        public void loadLevelsScene(int n)
        {
            packageIndex = n;
            nNiveles = levelPackages[n].levels.Length;
            SceneManager.LoadScene("MenuNiveles");
        }

        public void sceneLevelPlay(int level)
        {
            levelToPlay = level;
            SceneManager.LoadScene("Game");
        }

        public void saveProgress()
        {
            string json = JsonUtility.ToJson(new GameData(nHints, levelsPassed));
            PlayerPrefs.SetString("progress", json); 
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
            nHints++;
        }

        public int getHints()
        {
            return nHints;
        }
    }
}