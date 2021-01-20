using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Advertisements;
using UnityEngine.UI;

public class Ads : MonoBehaviour, IUnityAdsListener
{
    string gameId = "3963369";
    bool testMode = true;

    void Start()
    {
        Advertisement.Initialize(gameId, testMode);
    }

    public void ShowInterstitialAd(string placementId)
    {
        Advertisement.AddListener(this);
        // Check if UnityAds ready before calling Show method:
        if (Advertisement.IsReady(placementId))
        {
            Advertisement.Show(placementId);
        }
        else
        {
            Debug.Log("Interstitial ad not ready at the moment! Please try again later!");
        }
    }

    public void ShowRewardedVideo(string placement)
    {
        // Check if UnityAds ready before calling Show method:
        if (Advertisement.IsReady(placement))
        {
            Advertisement.Show(placement);
        }
        else
        {
            Debug.Log("Rewarded video is not ready at the moment! Please try again later!");
        }
    }

    // Implement IUnityAdsListener interface methods:
    public void OnUnityAdsDidFinish(string placementId, ShowResult showResult)
    {
        // Define conditional logic for each ad completion status:
        if (showResult == ShowResult.Finished)
        {
            Debug.LogWarning("Close");
        }
        else if (showResult == ShowResult.Skipped)
        {
            Debug.LogWarning("SKIPEADO");
        }
        else if (showResult == ShowResult.Failed)
        {
            Debug.LogWarning("The ad did not finish due to an error.");
        }
        Advertisement.RemoveListener(this);
    }

    public void OnDestroy()
    {
        //Advertisement.RemoveListener(this);
    }

    public void OnUnityAdsReady(string placementId)
    {
        //throw new System.NotImplementedException();
    }

    public void OnUnityAdsDidError(string message)
    {
        //throw new System.NotImplementedException();
    }

    public void OnUnityAdsDidStart(string placementId)
    {
        //throw new System.NotImplementedException();
    }
}
