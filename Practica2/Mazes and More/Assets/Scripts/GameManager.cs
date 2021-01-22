using UnityEngine;
using UnityEngine.SceneManagement;

namespace MazesAndMore
{
    public class GameManager : MonoBehaviour
    {
        public LevelManager levelManager;
        public MenuLevelManager menuLevelManager;
        public LevelPackage[] levelPackages;
        
        private static GameManager _instance;

        private int packageIndex;
        int levelToPlay;

        private void Start()
        {
            //PlayerPrefs.DeleteAll();
            if (_instance != null)
            {
                _instance.menuLevelManager = menuLevelManager;
                if (menuLevelManager != null) 
                    _instance.menuLevelManager.init();

                _instance.levelManager = levelManager;
                if (levelManager != null)
                    _instance.LoadLevel();

                DestroyImmediate(gameObject);
                return;
            }
            else
            {
                _instance = this;
                if (menuLevelManager != null) _instance.menuLevelManager.init();

                // Create PlayerPrefs for new players
                if (GetPlayerData() == null)
                    CreateNewData();
                
                DontDestroyOnLoad(this.gameObject);
            }
        }

        // Loads level selection scene
        public void LoadLevelsScene(int n)
        {
            packageIndex = n;
            SceneManager.LoadScene("MenuNiveles");
        }

        // Loads current level from current package
        public void LoadLevel()
        {
            levelManager.LoadLevel(levelPackages[_instance.packageIndex], levelToPlay);
        }

        // Loads Game scene and sets current level
        public void SceneLevelPlay(int level)
        {
            levelToPlay = level;
            SceneManager.LoadScene("Game");
        }

        // Creates starting data for new players
        public void CreateNewData()
        {
            int[] levelsPassed = new int[levelPackages.Length];
            for (int i = 0; i < levelPackages.Length; i++)
                levelsPassed[i] = -1;

            GameData data = new GameData(2, levelsPassed, true);
            SaveProgress(JsonUtility.ToJson(data));
        }

        // Saves hashed player progress in PlayerPrefs
        public void SaveProgress(string json)
        {
            string hashValue = SecureHelper.Hash(json);
            PlayerPrefs.SetString("progress", hashValue + "#" + json);
        }

        // Updates player progress and moves to next level
        public void LevelPassed()
        {
            int[] levelsPassed = GetPlayerData().levelsPassed;
            if (levelsPassed[packageIndex] < levelToPlay)
            {
                levelsPassed[packageIndex] = levelToPlay;
                GameData g = GetPlayerData();
                g.levelsPassed = levelsPassed;
                SaveProgress(JsonUtility.ToJson(g));
            }

            if (levelToPlay + 1 != levelPackages[packageIndex].levels.Length)
                levelToPlay++;
        }

        // Adds "n" hints to player data
        public void AddHints(int n)
        {
            GameData g = GetPlayerData();
            g.nHints += n;
            SaveProgress(JsonUtility.ToJson(g));
        }

        // Disables ads for current player
        public void DisableAds()
        {
            GameData g = GetPlayerData();
            g.adsEnabled = false;
            SaveProgress(JsonUtility.ToJson(g));
        }

        // Security
        private bool VerifyHash(string json)
        {
            string[] subs = json.Split('#');
            string hashValue = SecureHelper.Hash(subs[1]);
            string hashStored = subs[0];

            return hashValue == hashStored;
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

        public GameData GetPlayerData()
        {
            string json = PlayerPrefs.GetString("progress");
            if (json == "")
                return null;

            if (VerifyHash(json))
            {
                string[] subs = json.Split('#');
                return JsonUtility.FromJson<GameData>(subs[1]);
            }
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