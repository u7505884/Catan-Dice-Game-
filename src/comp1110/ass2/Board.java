package comp1110.ass2;

import java.util.LinkedList;

public class Board {
    private final LinkedList<Road> roads; //All roads contained in the board
    private final LinkedList<Knight> knights;//All knights contained in the board
    private final LinkedList<Settlement> settlements;//All settlements contained in the board
    private final LinkedList<City> cities;//All cities contained in the board
    private int round;//indicate which turn we are in
    private int[] scoresRecorder;//record scores we have gotten or lost in each previous round

    public Board(LinkedList<Road> roads, LinkedList<Knight> knights, LinkedList<Settlement> settlements, LinkedList<City> cities) {
        this.roads = roads;
        this.knights = knights;
        this.settlements = settlements;
        this.cities = cities;
    }

    /**
     * Check if the building we input can be built. This method should meet
     * the building constraints including building order and resources demand.
     *
     * @param whatWeWantToBuild: The buildable structure we want to build.
     * @param resourcesWeHave: The resources we have at this moment.
     * @return true iff the structure can be built, false otherwise.
     */
    public boolean tryToBuild(BuildableStructures whatWeWantToBuild, Resources[] resourcesWeHave){
        return false;
    }

    /**
     * Build the building we want. What we need to pay attention is, this method
     * should only be used after we have check the validity of the construction by
     * method tryToBuild().
     *
     * @param whatWeWantToBuild: The buildable structure we want to build.
     */
    public void build(BuildableStructures whatWeWantToBuild){}

    /**
     * Check if it is time to finish the whole game. This method should be implemented
     * by the class variable round.
     *
     * @return true iff it is time to finish, false otherwise.
     */
    public boolean whetherShouldFinish(){
        return false;
    }

    /**
     * Calculate final scores. What we need to pay attention is, this method
     * should only be used after we have check the validity of it by method
     * whetherShouldFinish().
     *
     */
    public int calculateFinalScores(){
        return 0;
    }

    /**
     * To process the first roll in each round.
     *
     * @return An array of Resources gotten randomly.
     */
    public Resources[] firstRoll(){
        return new Resources[]{};
    }

    /**
     * To process the second or third roll in each round after the first one.
     * We need to replace certain random results gotten in first roll.
     *
     * @param positionOfResourcesWantToReplace: An array of the positions in
     * the array of Resources we got in first roll.
     * @return New array of Resources after replacement.
     */
    public Resources[] rollAgain(int[] positionOfResourcesWantToReplace){
        return new Resources[]{};
    }

    /**
     * Trade new resource with gold. In this method we should check whether we
     * have enough gold to pay first.
     *
     * @param resourceWanted: The resource we want to trade with our gold
     * @return New array of Resources after replacement.
     */
    public Resources[] tradeByGold(Resources resourceWanted){
        return new Resources[]{};
    }

    /**
     * Swap resource with knight. In this method we should check whether the knight
     * has been used before.
     * !!!In this method, we should pay attention to Knight 6 (The wildcard)!
     *
     * @param knight: The knight we want to trade with.
     * @param resourcePaid: The resource we want to pay in this swap.
     * @return New array of Resources after swap.
     */
    public Resources[] tradeWithKnight(Knight knight, Resources resourcePaid){
        return  new Resources[]{};
    }
}
