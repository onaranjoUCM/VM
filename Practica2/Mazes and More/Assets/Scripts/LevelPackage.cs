using UnityEngine;

[CreateAssetMenu(fileName= "Data", menuName = "ScriptableObjects/LevelGroup", order = 1)]
public class LevelPackage : ScriptableObject
{
    public string packageName;
    public TextAsset[] levels;
    public Color color;
    public Color wallsColor;
    public Sprite buttonImage;
    public Sprite buttonPressedImage;
}
