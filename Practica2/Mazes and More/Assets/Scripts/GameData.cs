namespace MazesAndMore
{
    [System.Serializable]
    public class GameData
    {
        public int nHints = 0;
        public int[] levelsPassed;

        public GameData(int n, int[] levels)
        {
            nHints = n;
            levelsPassed = levels;
        }
    }
}