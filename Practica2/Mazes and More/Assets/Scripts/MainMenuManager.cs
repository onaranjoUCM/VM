using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class MainMenuManager : MonoBehaviour
    {
        public Canvas mainMenu;
        public GameObject mainMenubutton;
        public Canvas startMenu;
        public Button levelPackButtonPrefab;

        public void Play()
        {
            startMenu.gameObject.SetActive(false);

            LevelPackage[] levelPackages = GameManager.getInstance().levelPackages;
            for (int i = 0; i < levelPackages.Length; i++)
            {
                LevelPackage lp = levelPackages[i];
                Button b = Instantiate(levelPackButtonPrefab, new Vector3(0, 0, 0), Quaternion.identity);
                b.transform.SetParent(mainMenubutton.transform);
                b.transform.localPosition = new Vector3(0, 100 - 200 * i, 0);
                b.transform.localScale = Vector3.one*4;
                b.GetComponent<Image>().sprite = lp.buttonImage;

                GameManager gm = GameManager.getInstance();
                int nPass = gm.GetPlayerData().levelsPassed[i] + 1;
                int porcen = nPass * 100 / GameManager.getInstance().GetCurrentLevelPackage(i).levels.Length;
                b.GetComponentInChildren<Text>().text = lp.packageName + "  " + porcen + "%";
                b.GetComponentInChildren<Text>().color = Color.white;
                b.GetComponentInChildren<Text>().fontStyle = FontStyle.Bold;

                int n = i;
                b.onClick.AddListener(() => GameManager.getInstance().LoadLevelsScene(n));
            }

            mainMenu.gameObject.SetActive(true);
        }

        public void ToggleSound()
        {

        }

        public void ShowRankings()
        {

        }

        public void LikeApp()
        {

        }

        public void openSettings()
        {

        }

        public void openShop()
        {

        }
    }
}