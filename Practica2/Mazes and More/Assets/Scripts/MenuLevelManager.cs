using System.Collections;
using System.Collections.Generic;
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
        private Text title;

        public void init(GameManager g)
        {
            //SOLUCIONAR ESTO
            height = 600;
            width = 800;

            Camera cam = Camera.main;
            float camW = height * cam.aspect;
            float camH = 2f * cam.orthographicSize;

            float h1 = height / camH;
            float w1 = width / camW;

            float midH = (h1 * (camH / 2)) / 2;
            float midW = (w1 * (camW / 2)) / 2;

            height = midH;
            width = midW;

            gamemanager = g;
            nNiveles = gamemanager.getnNiveles();

            botones = new Button[5, nNiveles / 5];

            pass = new bool[nNiveles];
            for (int i = 0; i < nNiveles; i++)
            {
                pass[i] = false;
            }

            pass[0] = true; //DE PRUEBA
            pass[1] = true;

            Button adsButton = Instantiate(regalo, new Vector3(0, 0, 0), Quaternion.identity);
            adsButton.gameObject.transform.SetParent(transform);
            adsButton.transform.position = new Vector3((Screen.width - 20) / 6, Screen.height - 60, 0);

            /*title.text = "TITULO";
            Text t = Instantiate(title, new Vector3(0, 0, 0), Quaternion.identity);
            t.gameObject.transform.SetParent(transform);
            t.transform.position = new Vector3((Screen.width) / 2, Screen.height - 60, 0);*/

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

                    b.gameObject.transform.SetParent(transform);
                    b.transform.position = new Vector3(increX * (i + 1), Screen.height - 100 - (j + 1) * 60 , 0);


                    botones[i, j] = b;
                    int level = c;
                    if (pass[c])
                        botones[i, j].GetComponent<Button>().onClick.AddListener(() => gamemanager.sceneLevelPlay(level));

                    c++;
                }
            }
        }
    }
}
