using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Advertisements;
using UnityEngine.UI;

namespace MazesAndMore
{
    [RequireComponent(typeof(Button))]
    public class ButtonAds : MonoBehaviour, IUnityAdsListener
    {
        /*
#if UNITY_IOS
        private string gameId = "3963368";
#elif UNITY_ANDROID
        private string gameId = "3963369";
#endif
        */
        Button myButton;
        public string myPlacementId = "rewardedVideo";

        // Start is called before the first frame update
        void Start()
        {
            myButton = GetComponent<Button>();

            // Map the ShowRewardedVideo function to the button’s click listener:
            if (myButton) myButton.onClick.AddListener(ShowRewardedVideo);

            // Initialize the Ads listener and service:

            Advertisement.Initialize("3963369", true);

        }
        void ShowRewardedVideo()
        {
            Advertisement.AddListener(this);
            Advertisement.Show(myPlacementId);
        }

        public void OnUnityAdsDidError(string message)
        {
            //throw new System.NotImplementedException();
        }

        public void OnUnityAdsDidFinish(string placementId, ShowResult showResult)
        {
            //Accion que se realiza dependiendo de lo que se haga con el anuncio
            if (showResult == ShowResult.Finished)
            {
                GameManager.getInstance().AddHints(1);
            }
            else if (showResult == ShowResult.Skipped)
            {
                Debug.LogWarning("Si skipeas te quedas sin pistas");
            }
            else if (showResult == ShowResult.Failed)
            {
                Debug.LogWarning("The ad did not finish due to an error.");
            }
            Advertisement.RemoveListener(this);
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
        public void OnDestroy()
        {
            Advertisement.RemoveListener(this);
        }
    }
}
