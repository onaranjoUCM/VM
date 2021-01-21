namespace MazesAndMore
{
    [System.Serializable]
    public class GameData
    {
        public int nHints = 0;
        public int[] levelsPassed;
        public bool adsEnabled;

        public GameData(int n, int[] levels, bool ads)
        {
            nHints = n;
            levelsPassed = levels;
            adsEnabled = ads;
        }
    }
}