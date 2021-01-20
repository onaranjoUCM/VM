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
        GameManager gamemanager;
        public Button regalo;
        GameObject texto;
        public Text title;
        public GameObject star;

        public GameObject butonsUI;
        public GameObject UIUp;

        public void init(GameManager g)
        {    
            gamemanager = g;
            nNiveles = gamemanager.getnNiveles();

            botones = new Button[5, nNiveles / 5];

            pass = new bool[nNiveles];
            for (int i = 0; i < nNiveles; i++)
            {
                pass[i] = false;
            }

            int nPass = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress")).levelsPassed[gamemanager.GetPackageIndex()];
            for(int i = 0; i <= nPass + 1; i++)
            {
                pass[i] = true;
            }

            Button adsButton = Instantiate(regalo, new Vector3(0, 0, 0), Quaternion.identity);
            adsButton.gameObject.transform.SetParent(UIUp.transform);
            adsButton.transform.position = new Vector3((Screen.width - 20) / 6, Screen.height - 60, 0);
            //adsButton.transform.localScale = Vector3.one;

            title.text = gamemanager.levelPackages[gamemanager.GetPackageIndex()].packageName;
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
                    if (pass[c])
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
                    Color color = gamemanager.levelPackages[gamemanager.GetPackageIndex()].color;
                    int levelEnd = JsonUtility.FromJson<GameData>(PlayerPrefs.GetString("progress")).levelsPassed[gamemanager.GetPackageIndex()];

                    // TODO: Revisar esto, al crear un usuario nuevo sale el nivel 1 como superado ya
                    if (pass[c])
                    {
                        botones[i, j].GetComponent<Button>().onClick.AddListener(() => gamemanager.SceneLevelPlay(level));
                        int n = c + 1;
                        if(c <= levelEnd)
                            botones[i, j].GetComponent<Image>().color = color;
                        botones[i, j].GetComponentInChildren<Text>().text = n.ToString();
                    }

                    c++;
                }
            }
        }
    }
}
