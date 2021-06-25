namespace MazesAndMore
{
    [System.Serializable]
    public class GameData
    {
        public int nHints = 0;
        public int[] levelsPassed;
        public bool adsEnabled;
        public int activePack;
        public int activeLevel;

        public GameData(int n, int[] levels, bool ads)
        {
            nHints = n;
            levelsPassed = levels;
            adsEnabled = ads;
            activePack = 0;
            activeLevel = 0;
        }
    }
}