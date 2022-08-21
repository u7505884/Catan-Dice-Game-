package comp1110.ass2;

public class Road extends BuildableStructures{
    private int y;//this is an advanced building number to indicate its position in the board efficiently.

    public Road(int x, int y, int scores, Resources[] demandOfResources) {
        super.x = x;
        this.y = y;
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
