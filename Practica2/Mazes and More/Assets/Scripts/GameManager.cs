using System.Collections;
using System.Collections.Generic;
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
            }
        }

        public void loadLevelsScene(int n)
        {
            packageIndex = n;
            nNiveles = levelPackages[n].levels.Length;
            Debug.Log(n);
            SceneManager.LoadScene("MenuNiveles");
        }

        public void sceneLevelPlay(int level)
        {
            levelToPlay = level;
            //Debug.Log("Level Play " + level);
            SceneManager.LoadScene("Game");
        }

        public int getnNiveles() 
        {
            return nNiveles;
        }
    }
}