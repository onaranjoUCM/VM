namespace MazesAndMore
{
    [System.Serializable]
    public class GameData
    {
        public int nHints;
        public int[] levelsPassed;

        public GameData(int n, int[] levels)
        {
            nHints = n;
            levelsPassed = levels;
        }
    }
}