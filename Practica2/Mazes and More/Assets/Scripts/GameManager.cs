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
        
        private static GameManager _instance;

        private int packageIndex;
        private int[] levelsPassed;
        int levelToPlay;

        private void Awake()
        {
            //PlayerPrefs.DeleteAll();
            if (_instance != null)
            {
                _instance.levelManager = levelManager;
                _instance.menuLevelManager = menuLevelManager;

                if (menuLevelManager != null) 
                    _instance.menuLevelManager.init();

                if (levelManager != null)
                    _instance.LoadLevel();

                DestroyImmediate(gameObject);
                return;
            }
            else
            {
                _instance = this;
                if (menuLevelManager != null) _instance.menuLevelManager.init();

                // Load game progress from PlayerPrefs
                LoadData();
                
                DontDestroyOnLoad(this.gameObject);
            }
        }

        public void LoadLevelsScene(int n)
        {
            packageIndex = n;
            SceneManager.LoadScene("MenuNiveles");
        }

        public void LoadLevel()
        {
            levelManager.LoadLevel(levelPackages[_instance.packageIndex], levelToPlay);
        }

        public void LoadData()
        {
            GameData data = GetPlayerProgress();

            if (data == null)
            {
                levelsPassed = new int[levelPackages.Length];
                for (int i = 0; i < levelPackages.Length; i++)
                    levelsPassed[i] = -1;

                data = new GameData(2, levelsPassed);
                SaveProgress(JsonUtility.ToJson(data));
            }
            else
            {
                levelsPassed = data.levelsPassed;
            }
        }

        public void SceneLevelPlay(int level)
        {
            levelToPlay = level;
            SceneManager.LoadScene("Game");
        }

        public void SaveProgress(string json)
        {
            PlayerPrefs.SetString("progress", json);

            string hashValue = SecureHelper.Hash(json);
            PlayerPrefs.SetString("HASH", hashValue);
        }

        public void LevelPassed()
        {
            if (levelsPassed[packageIndex] < levelToPlay)
            {
                levelsPassed[packageIndex] = levelToPlay;
                GameData g = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress"));
                g.levelsPassed = levelsPassed;
                SaveProgress(JsonUtility.ToJson(g));
            }

            if (levelToPlay + 1 != levelPackages[packageIndex].levels.Length)
                levelToPlay++;
        }

        public void AddHints(int n)
        {
            GameData g = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress"));
            g.nHints += n;
            SaveProgress(JsonUtility.ToJson(g));
        }

        // Security
        private bool VerifyHash(string json)
        {
            string defaultValue = "NO_HASH_GENERATED";
            string hashValue = SecureHelper.Hash(json);
            string hashStored = PlayerPrefs.GetString("HASH", defaultValue);

            return hashValue == hashStored || hashStored == defaultValue;
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

        public LevelPackage GetCurrentLevelPackage()
        {
            return levelPackages[packageIndex];
        }

        public LevelPackage GetCurrentLevelPackage(int i)
        {
            return levelPackages[i];
        }

        public GameData GetPlayerProgress()
        {
            string json = PlayerPrefs.GetString("progress");

            if (VerifyHash(json))
                return JsonUtility.FromJson<GameData>(json);
            else
            {
                Debug.LogError("Invalid data, hash mismatch");
                return null;
            }
        }
        public int GetPackageIndex()
        {
            return packageIndex;
        }
    }
}