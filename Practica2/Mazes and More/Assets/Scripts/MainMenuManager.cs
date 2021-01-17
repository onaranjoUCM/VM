using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class MainMenuManager : MonoBehaviour
    {
        public Canvas mainMenu;
        public Canvas startMenu;
        public Button levelPackButtonPrefab;

        public void Play()
        {
            startMenu.gameObject.SetActive(false);

            LevelPackage[] levelPackages = GameManager.getInstance().levelPackages;
            for (int i = 0; i < levelPackages.Length; i++)
            {
                LevelPackage lp = levelPackages[i];
                Button b = Instantiate(levelPackButtonPrefab, new Vector3(0, -50 * i, 0), Quaternion.identity);
                b.transform.SetParent(mainMenu.transform);
                b.transform.localScale = Vector3.one;
                b.GetComponent<Image>().sprite = lp.buttonImage;
                b.GetComponentInChildren<Text>().text = lp.packageName;
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