﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Tile : MonoBehaviour
    {
        public enum SIDE { LEFT, RIGHT, UP, DOWN, ALL }
        public static int[] opposite = { 1, 0, 3, 2 };
        Color playerColor;
        Color hintColor;

        public SpriteRenderer iceFloor;
        public SpriteRenderer start;
        public SpriteRenderer finish;

        public SpriteRenderer wall_left;
        public SpriteRenderer wall_right;
        public SpriteRenderer wall_up;
        public SpriteRenderer wall_down;
        
        public SpriteRenderer[] segments;

        [HideInInspector]
        public int[] timesSegmentCrossed = { 0, 0, 0, 0 };
        [HideInInspector]
        public bool[] openSides = { true, true, true, true };
        [HideInInspector]
        public bool[] hintedSegments = { false, false, false, false };
        [HideInInspector]
        public int numberOfOpenSides = 4;
        [HideInInspector]
        public int x;
        [HideInInspector]
        public int y;

        void Start()
        {
#if UNITY_EDITOR
            if (iceFloor == null || start == null || finish == null ||
                wall_left == null || wall_right == null || wall_up == null || wall_down == null || 
                segments[0] == null || segments[1] == null || segments[2] == null || segments[3] == null)  {
                Debug.LogError("Missing sprite");
                gameObject.SetActive(false);
                return;
            }
#endif
        }

        public void checkSegments() {
            for (int i = 0; i < segments.Length; i++)
            {
                if (timesSegmentCrossed[i] != 0)
                {
                    enableSegment(i);
                    segments[i].color = playerColor;
                }
                else
                    if (hintedSegments[i])
                    segments[i].color = hintColor;
                else
                    disableSegment(i);
            }
        }

        public void setSegmentColor(int side, Color c)
        {
            if (side == (int)SIDE.ALL)
            {
                foreach (SpriteRenderer segment in segments)
                    segment.color = c;
            }
            else
            {
                segments[side].color = c;
            }
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
            blockRightWall();
        }
        public void enableUpWall() { 
            wall_up.gameObject.SetActive(true);
            openSides[(int)SIDE.UP] = false;
            numberOfOpenSides--;
        }
        public void enableDownWall() { 
            wall_down.gameObject.SetActive(true);
            blockDownWall();
        }

        public void blockDownWall()
        {
            openSides[(int)SIDE.DOWN] = false;
            numberOfOpenSides--;
        }

        public void blockRightWall()
        {
            openSides[(int)SIDE.RIGHT] = false;
            numberOfOpenSides--;
        }

        // Player segments
        public void enableSegment(int dir) { segments[dir].gameObject.SetActive(true); }
        public void disableSegment(int dir) { segments[dir].gameObject.SetActive(false); }

        // Hints
        public void hintSegment(int side)
        {
            hintedSegments[side] = true;
            enableSegment(side);
        }

        public void setPlayerColor(Color c)
        {
            playerColor = c;
        }

        public void setHintColor(Color c)
        {
            hintColor = c;
        }
    }
}