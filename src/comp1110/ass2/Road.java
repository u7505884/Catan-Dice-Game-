package comp1110.ass2;

public class Road extends BuildableStructures{
    private Road lastRoad;

    public Road(int x, Road lastRoad, int scores, Resources[] demandOfResources) {
        super.x = x;
        this.lastRoad = lastRoad;
        super.scores = scores;
        super.demandOfResources = demandOfResources;
    }
    /**
     * Basic getter method to get class variable y.
     *
     * @return An Integer which is an advanced building number to indicate its position in the
     * board efficiently.
     */
    public int getY() {
        return 0;
    }
}
