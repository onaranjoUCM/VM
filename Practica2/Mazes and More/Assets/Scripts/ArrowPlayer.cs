using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ArrowPlayer : MonoBehaviour
{
    public SpriteRenderer arrowUp;
    public SpriteRenderer arrowDown;
    public SpriteRenderer arrowRight;
    public SpriteRenderer arrowLeft;

    public void EnableUp()
    {
        arrowUp.gameObject.SetActive(true);
    }

    public void DisableUp()
    {
        arrowUp.gameObject.SetActive(false);
    }

    public void EnableRight()
    {
        arrowRight.gameObject.SetActive(true);
    }

    public void DisableRight()
    {
        arrowRight.gameObject.SetActive(false);
    }

    public void EnableLeft()
    {
        arrowLeft.gameObject.SetActive(true);
    }
    public void DisableLeft()
    {
        arrowLeft.gameObject.SetActive(false);
    }
    public void EnableDown()
    {
        arrowDown.gameObject.SetActive(true);
    }
    public void DisableDown()
    {
        arrowDown.gameObject.SetActive(false);
    }

    public void setColor(Color c)
    {
        arrowUp.color = c;
        arrowDown.color = c;
        arrowLeft.color = c;
        arrowRight.color = c;
    }
}
