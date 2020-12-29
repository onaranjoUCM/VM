using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class GameManager : MonoBehaviour
    {
        public LevelManager levelManager;
        static GameManager _instance;
        public LevelPackage[] levelPackages;

#if UNITY_EDITOR
        public int levelToPlay;
#endif
        // Start is called before the first frame update
        void Start()
        {
            if (_instance != null)
            {
                _instance.levelManager = levelManager;
                DestroyImmediate(gameObject);
                return;
            }

            // Resto de la inicializacion
            Map map = new Map(levelPackages[0].levels[0]);
        }

        // Update is called once per frame
        void Update()
        {

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