using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Tile : MonoBehaviour
    {
        public SpriteRenderer iceFloor;
        public SpriteRenderer start;
        public SpriteRenderer finish;
        public SpriteRenderer wall_left;
        public SpriteRenderer wall_right;
        public SpriteRenderer wall_up;
        public SpriteRenderer wall_down;
        public SpriteRenderer segment_left;
        public SpriteRenderer segment_right;
        public SpriteRenderer segment_up;
        public SpriteRenderer segment_down;

        [HideInInspector]
        public bool[] openSides = { true, true, true, true };
        [HideInInspector]
        int numberOfOpenSides = 4;
        [HideInInspector]
        public int x;
        [HideInInspector]
        public int y;

        void Start()
        {
#if UNITY_EDITOR
            if (iceFloor == null || start == null || finish == null ||
                wall_left == null || wall_right == null || wall_up == null || wall_down == null || 
                segment_left == null || segment_right == null || segment_up == null || segment_down == null)  {
                Debug.LogError("Missing sprite");
                gameObject.SetActive(false);
                return;
            }
#endif
        }

        // Misc
        public void enableIce() { iceFloor.gameObject.SetActive(true); }
        public void enableStart() { start.gameObject.SetActive(true); }
        public void disableStart() { start.gameObject.SetActive(false); }
        public void enableFinish() { finish.gameObject.SetActive(true); }

        // Walls
        public void enableLeftWall() { 
            wall_left.gameObject.SetActive(true);
            openSides[(int)SIDE.LEFT] = false;
            numberOfOpenSides--;
        }
        public void enableRightWall() { 
            wall_right.gameObject.SetActive(true);
            openSides[(int)SIDE.RIGHT] = false;
            numberOfOpenSides--;
        }
        public void enableUpWall() { 
            wall_up.gameObject.SetActive(true);
            openSides[(int)SIDE.UP] = false;
            numberOfOpenSides--;
        }
        public void enableDownWall() { 
            wall_down.gameObject.SetActive(true);
            openSides[(int)SIDE.DOWN] = false;
            numberOfOpenSides--;
        }

        // Player segments
        public void toggleLeftSegment() { 
            if (segment_left.gameObject.activeSelf)
                segment_left.gameObject.SetActive(false);
            else
                segment_left.gameObject.SetActive(true);
        }

        public void toggleRightSegment()
        {
            if (segment_right.gameObject.activeSelf)
                segment_right.gameObject.SetActive(false);
            else
                segment_right.gameObject.SetActive(true);
        }

        public void toggleUpSegment()
        {
            if (segment_up.gameObject.activeSelf)
                segment_up.gameObject.SetActive(false);
            else
                segment_up.gameObject.SetActive(true);
        }

        public void toggleDownSegment()
        {
            if (segment_down.gameObject.activeSelf)
                segment_down.gameObject.SetActive(false);
            else
                segment_down.gameObject.SetActive(true);
        }
    }
}
