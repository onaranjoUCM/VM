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
            //Asignar los botones de los niveles que han sido completados
            for (int i = 0; i <= nPass; i++)
            {
                pass[i] = true;
            }
            nextlevel = nPass + 1; //indicar cual es el siguiente nivel

            title.text = gm.levelPackages[gm.GetPackageIndex()].packageName;
            //Mostrar los botones en pantalla
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
                    //Dependiendo de si ha sido completado o no se asigna un tipo de boton u otro
                    if (pass[c] || c == nextlevel)
                        boton = botonNivel;
                    else
                        boton = botonBlock;

                    Button b = Instantiate(boton, new Vector3(0, 0, 0), Quaternion.identity);
                    
                    float scale = width / height;
                    int increX = (Screen.width - 20) / 6;

                    float w = Screen.width; float h = Screen.height;

                    float incX = w / 720;
                    float incY = h / 1280;

                    if (720 * incY < w)
                        b.transform.localScale = new Vector3(transform.localScale.x * incY, transform.localScale.y * incY, transform.localScale.z);
                    else
                        b.transform.localScale = new Vector3(transform.localScale.x * incX, transform.localScale.y * incX, transform.localScale.z);

                    /*if (h > w)
                        scale = (w / h) / 5;
                    else
                        scale = (h / w) / (nNiveles / 5);

                    scale *= 8;*/


                    b.gameObject.transform.SetParent(butonsUI.transform);

                    botones[i, j] = b;
                    int level = c;
                    GameManager gm = GameManager.getInstance();
                    Color color = gm.GetCurrentLevelPackage().color;
                    int levelEnd = gm.GetPlayerData().levelsPassed[gm.GetPackageIndex()];

                    if (pass[c] || c == nextlevel) //Ver si el nivel del boton se ha completado o es el siguiente nivel
                    {
                        botones[i, j].GetComponent<Button>().onClick.AddListener(() => gm.SceneLevelPlay(level));
                        int n = c + 1;
                        if (c <= levelEnd) //Si el nivel ha sido completado se cambia a color verde
                        {
                            botones[i, j].GetComponent<Image>().color = color;
                            botones[i, j].GetComponentInChildren<Text>().color = Color.white;
                        }
                        botones[i, j].GetComponentInChildren<Text>().text = n.ToString();
                    }

                    //Ajustar el tamaño del UI dependiendo de el numero de botones
                    RectTransform rt = butonsUI.GetComponent<RectTransform>();
                    if (j > 6)
                    {
                        rt.sizeDelta = new Vector2(rt.sizeDelta.x, rt.sizeDelta.y + 75 / 2);
                        rt.position = new Vector2(rt.position.x, rt.position.y - 75 / 2);
                    }

                    c++;
                }
            }
        }
    }
}
