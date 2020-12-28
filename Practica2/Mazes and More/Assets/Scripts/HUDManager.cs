using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class HUDManager : MonoBehaviour
{
    public Button botonNivel;
    public Button botonBlock;
    public Canvas canvas;
    public int nNiveles;
    private int c;
    float height;
    float width;
    private bool[] pass;
    private Button[,] botones;

    // Start is called before the first frame update
    void Start()
    {
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

        botones = new Button[5, nNiveles / 5];

        pass = new bool[nNiveles];
        for (int i = 0; i < nNiveles; i++)
        {
            pass[i] = false;
        }
        pass[0] = true;

        putButtons();
    }

    // Update is called once per frame
    void Update()
    {
        
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

                b.gameObject.transform.SetParent(transform);
                b.transform.position = new Vector3(Screen.width * ((0.31f + (i + 1) * 0.03f) * scale), Screen.height * (0.6f - ((j + 1) * 0.08f) * scale), 0);
                

                botones[i, j] = b;

                c++;
            }
        }
    }
}
