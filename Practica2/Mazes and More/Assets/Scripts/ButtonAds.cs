﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Advertisements;
using UnityEngine.UI;

namespace MazesAndMore
{
    [RequireComponent(typeof(Button))]
    public class ButtonAds : MonoBehaviour, IUnityAdsListener
    {

#if UNITY_IOS
        private string gameId = "3963368";
#elif UNITY_ANDROID
        private string gameId = "3963369";
#endif

        Button myButton;
        public string myPlacementId = "rewardedVideo";
        public GameObject gameManager;
        GameManager manager;

        // Start is called before the first frame update
        void Start()
        {
            manager = gameManager.GetComponent<GameManager>();

            myButton = GetComponent<Button>();

            // Set interactivity to be dependent on the Placement’s status:
            myButton.interactable = Advertisement.IsReady(myPlacementId);

            // Map the ShowRewardedVideo function to the button’s click listener:
            if (myButton) myButton.onClick.AddListener(ShowRewardedVideo);

            // Initialize the Ads listener and service:
            Advertisement.AddListener(this);
            Advertisement.Initialize(gameId, true);
        }
        void ShowRewardedVideo()
        {
            Advertisement.Show(myPlacementId);
        }

        public void OnUnityAdsDidError(string message)
        {
            //throw new System.NotImplementedException();
        }

        public void OnUnityAdsDidFinish(string placementId, ShowResult showResult)
        {
            // Define conditional logic for each ad completion status:
            if (showResult == ShowResult.Finished)
            {
                manager.oneHint();
                Debug.LogWarning("+1 pista, Pistas totales: " + manager.getHints());
            }
            else if (showResult == ShowResult.Skipped)
            {
                Debug.LogWarning("Si skipeas te quedas sin pistas");
            }
            else if (showResult == ShowResult.Failed)
            {
                Debug.LogWarning("The ad did not finish due to an error.");
            }
        }

        public void OnUnityAdsDidStart(string placementId)
        {
            //throw new System.NotImplementedException();
        }

        public void OnUnityAdsReady(string placementId)
        {
            // If the ready Placement is rewarded, activate the button: 
            if (placementId == myPlacementId)
            {
                myButton.interactable = true;
            }
        }
    }
}