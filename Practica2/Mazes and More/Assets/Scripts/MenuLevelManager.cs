using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class MenuLevelManager : MonoBehaviour
    {
        public Button botonNivel;
        public Button botonBlock;
        public Canvas canvas;
        int nNiveles;
        private int c = 0;
        float height;
        float width;
        private bool[] pass;
        private Button[,] botones;
        public Button regalo;
        GameObject texto;
        public Text title;
        public GameObject star;
        int nextlevel;

        public GameObject butonsUI;
        public GameObject UIUp;

        public void init()
        {    
            nNiveles = GameManager.getInstance().GetCurrentLevelPackage().levels.Length;

            botones = new Button[5, nNiveles / 5];

            pass = new bool[nNiveles];
            for (int i = 0; i < nNiveles; i++)
            {
                pass[i] = false;
            }

            GameManager gm = GameManager.getInstance();
            int nPass = gm.GetPlayerData().levelsPassed[gm.GetPackageIndex()];
            for(int i = 0; i <= nPass; i++)
            {
                pass[i] = true;
            }
            nextlevel = nPass + 1;

            Button adsButton = Instantiate(regalo, new Vector3(0, 0, 0), Quaternion.identity);
            adsButton.gameObject.transform.SetParent(UIUp.transform);
            adsButton.transform.position = new Vector3((Screen.width - 20) / 6, Screen.height - 60, 0);
            //adsButton.transform.localScale = Vector3.one;

            title.text = gm.levelPackages[gm.GetPackageIndex()].packageName;
            title.gameObject.transform.SetParent(UIUp.transform);
            title.transform.position = new Vector3((Screen.width) * 3 / 6, Screen.height - 60, 0);

            GameObject starCanvas = Instantiate(star, new Vector3(0, 0, 0), Quaternion.identity);
            starCanvas.gameObject.transform.SetParent(UIUp.transform);
            starCanvas.transform.position = new Vector3((Screen.width - 20) * 5 / 6, Screen.height - 60, 0);

            putButtons();
        }

        void putButtons()
        {
            Camera cam = Camera.main;
            for (int j = 0; j < nNiveles / 5; j++)
            {
                for (int i = 0; i < 5; i++)
                {
                    Button boton;
                    if (pass[c] || c == nextlevel)
                    {
                        //botonNivel.GetComponent<Text>().text = "c";
                        boton = botonNivel;
                    }
                    else
                    {
                        boton = botonBlock;
                    }

                    Button b = Instantiate(boton, new Vector3(0, 0, 0), Quaternion.identity);

                    float scale = width / height;
                    int increX = (Screen.width - 20) / 6;

                    b.gameObject.transform.SetParent(butonsUI.transform);
                    //b.transform.localScale = Vector3.one * 4;


                    botones[i, j] = b;
                    int level = c;
                    GameManager gm = GameManager.getInstance();
                    Color color = gm.GetCurrentLevelPackage().color;
                    int levelEnd = gm.GetPlayerData().levelsPassed[gm.GetPackageIndex()];

                    // TODO: Revisar esto, al crear un usuario nuevo sale el nivel 1 como superado ya
                    if (pass[c] || c == nextlevel)
                    {
                        botones[i, j].GetComponent<Button>().onClick.AddListener(() => gm.SceneLevelPlay(level));
                        int n = c + 1;
                        if(c <= levelEnd)
                            botones[i, j].GetComponent<Image>().color = color;
                        botones[i, j].GetComponentInChildren<Text>().text = n.ToString();
                        //Debug.Log()
                    }

                    c++;
                }
            }
        }
    }
}
