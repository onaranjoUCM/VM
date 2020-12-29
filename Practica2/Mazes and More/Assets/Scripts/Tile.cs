using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Tile : MonoBehaviour
    {
        public SpriteRenderer iceFloor;
        public SpriteRenderer goal;

        void Start()
        {
#if UNITY_EDITOR
            if (iceFloor == null)  {
                Debug.LogError("Missing ice_floor sprite");
                gameObject.SetActive(false);
                return;
            }

            if (goal == null)  {
                Debug.LogError("Missing goal sprite");
                gameObject.SetActive(false);
                return;
            }
#endif
        }

        public void enableIce()
        {
            iceFloor.gameObject.SetActive(true);
        }

        public void disableIce()
        {
            iceFloor.gameObject.SetActive(false);
        }

        public void enableStart()
        {
            goal.gameObject.SetActive(true);
        }

        public void disableStart()
        {
            goal.gameObject.SetActive(false);
        }

        public void enableFinish()
        {
            goal.gameObject.SetActive(true);
        }

        public void disableFinish()
        {
            goal.gameObject.SetActive(false);
        }

        public void enableTopWall()
        {

        }

        public void enableBottomWall()
        {

        }

        public void enableWestWall()
        {

        }

        public void enableEastWall()
        {

        }
    }
}
