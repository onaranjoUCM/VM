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
        private string packLevelName;
        private string[] levelPackageNames;

#if UNITY_EDITOR
        public int levelToPlay;
#endif
        // Start is called before the first frame update
        void Start()
        {
            if (_instance != null)
            {
                _instance.levelManager = levelManager;
                _instance.menuLevelManager = menuLevelManager;
                if (menuLevelManager != null) _instance.menuLevelManager.init(_instance);
                DestroyImmediate(this.gameObject);
                return;
            }
            else
            {
                _instance = this;
                levelPackageNames = new string[levelPackages.Length];
                if (menuLevelManager != null) _instance.menuLevelManager.init(_instance);
                DontDestroyOnLoad(this.gameObject);
            }
        }

        // Update is called once per frame
        void Update()
        {

        }

        public void sceneNivelesClassic()
        {
            nNiveles = levelPackages[0].levels.Length;
            packLevelName = "ClassicLevels";
            SceneManager.LoadScene("MenuNiveles");
        }
        public void sceneNivelesHielo()
        {
            nNiveles = levelPackages[1].levels.Length;
            packLevelName = "IceFloorLevels";
            SceneManager.LoadScene("MenuNiveles");
        }

        public void sceneLevelPlay(int n)
        {
            levelToPlay = n;
            Debug.Log("Level Play " + n);
            //SceneManager.LoadScene("Game");
        }

        public int getnNiveles() 
        {
            return nNiveles;
        }

        /*
        private void StartNewScene()
        {
            if (levelManager != null)
            {

            }
        }
        */
    }
}