package comp1110.ass2;

import java.util.HashMap;

public class Board {
    protected final HashMap<Integer, Road> roads = new HashMap<>(); //All roads contained in the board
    protected final HashMap<Integer, Knight> knights = new HashMap<>();//All knights contained in the board
    protected final HashMap<Integer, Settlement> settlements = new HashMap<>();//All settlements contained in the board
    protected final HashMap<Integer, City> cities = new HashMap<>();//All cities contained in the board
    private int round = 0;//indicate which turn we are in
    private int[] scoresRecorder;//record scores we have gotten or lost in each previous round

    public Board(){
        this.initializeBoard();
    }
    public void initializeBoard(){
        Road R0 = new Road(0);
        Road R1 = new Road(1,R0);
        Road R2 = new Road(2,R0);
        Road R3 = new Road(3,R2);
        Road R4 = new Road(4,R3);
        Road R5 = new Road(5,R3);
        Road R6 = new Road(6,R5);
        Road R7 = new Road(7,R6);
        Road R8 = new Road(8,R7);
        Road R9 = new Road(9,R8);
        Road R10 = new Road(10,R9);
        Road R11 = new Road(11,R10);
        Road R12 = new Road(12,R7);
        Road R13 = new Road(13,R12);
        Road R14 = new Road(14,R13);
        Road R15 = new Road(15,R14);
        roads.put(0, R0);
        roads.put(1, R1);
        roads.put(2, R2);
        roads.put(3, R3);
        roads.put(4, R4);
        roads.put(5, R5);
        roads.put(6, R6);
        roads.put(7, R7);
        roads.put(8, R8);
        roads.put(9, R9);
        roads.put(10, R10);
        roads.put(11, R11);
        roads.put(12, R12);
        roads.put(13, R13);
        roads.put(14, R14);
        roads.put(15, R15);

        Settlement S3 = new Settlement(3);
        Settlement S4 = new Settlement(4, S3);
        Settlement S5 = new Settlement(5, S4);
        Settlement S7 = new Settlement(7, S5);
        Settlement S9 = new Settlement(9, S7);
        Settlement S11 = new Settlement(11, S9);
        settlements.put(3,S3);
        settlements.put(4,S4);
        settlements.put(5,S5);
        settlements.put(7,S7);
        settlements.put(9,S9);
        settlements.put(11,S11);

        City C7 = new City(7);
        City C12 = new City(12, C7);
        City C20 = new City(20, C12);
        City C30 = new City(30, C20);
        cities.put(7, C7);
        cities.put(12, C12);
        cities.put(20, C20);
        cities.put(30, C30);

        Knight J1 = new Knight(1);
        Knight J2 = new Knight(2, J1);
        Knight J3 = new Knight(3, J2);
        Knight J4 = new Knight(4, J3);
        Knight J5 = new Knight(5, J4);
        Knight J6 = new Knight(6, J5);
        knights.put(1,J1);
        knights.put(2,J2);
        knights.put(3,J3);
        knights.put(4,J4);
        knights.put(5,J5);
        knights.put(6,J6);

    }
    /**
     * Identify whether we can build current structure by the limit of building constraint
     *
     * @return A boolean value standing for whether it can pass the resources constraint
     */
    public boolean buildingConstraint(BuildableStructures whatWeWantToBuild){
        if(whatWeWantToBuild.getWhetherHaveBuilt()){
            return false;
        }
        //check roads
        HashMap<BuildableStructures, Road> demandOfRoad= new HashMap<>();
        for(Road road: roads.values()){
            demandOfRoad.put(road, (Road) road.getLastBuildableStructure());
        }
        demandOfRoad.put(this.settlements.get(3), null);
        demandOfRoad.put(this.settlements.get(4), this.roads.get(2));
        demandOfRoad.put(this.settlements.get(5), this.roads.get(5));
        demandOfRoad.put(this.settlements.get(7), this.roads.get(7));
        demandOfRoad.put(this.settlements.get(9), this.roads.get(9));
        demandOfRoad.put(this.settlements.get(11), this.roads.get(11));
        demandOfRoad.put(this.cities.get(7), this.roads.get(1));
        demandOfRoad.put(this.cities.get(12), this.roads.get(4));
        demandOfRoad.put(this.cities.get(20), this.roads.get(13));
        demandOfRoad.put(this.cities.get(30), this.roads.get(15));
        Road currentRoad = demandOfRoad.get(whatWeWantToBuild);
        if(currentRoad!=null) {
            while (currentRoad!= null) {
                if (currentRoad.getWhetherHaveBuilt() == false) {
                    return false;
                }
                currentRoad = (Road) currentRoad.getLastBuildableStructure();
            }
        }
        //check buildings
        HashMap<BuildableStructures, BuildableStructures> demandOfBuilding= new HashMap<>();
        for(Settlement settlement: settlements.values()){
            demandOfBuilding.put(settlement, settlement.getLastBuildableStructure());
        }
        for(City city: cities.values()){
            demandOfBuilding.put(city,city.getLastBuildableStructure());
        }
        for(Knight knight: knights.values()){
            demandOfBuilding.put(knight,knight.getLastBuildableStructure());
        }
        BuildableStructures currentBuilding = demandOfBuilding.get(whatWeWantToBuild);
        if(currentBuilding!=null) {
            while(currentBuilding!=null){
                if(currentBuilding.getWhetherHaveBuilt()==false){
                    return false;
                }
                currentBuilding = currentBuilding.getLastBuildableStructure();
            }
        }
        return true;
    }
    /**
     * Identify whether we can build current structure by building constraint and resources constraint
     *
     * @param whatWeWantToBuild: The buildable structure we want to build.
     *      * @param resourcesWeHave: The resources we have at this moment.
     *      * @return true iff the structure can be built, false otherwise.
     */
    public boolean whetherCanBeBuilt(BuildableStructures whatWeWantToBuild, int[] resourcesWeHave){
        return whatWeWantToBuild.resourcesConstraint(resourcesWeHave) && buildingConstraint(whatWeWantToBuild);
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
        return round>=15;
    }

    /**
     * Calculate final scores. What we need to pay attention is, this method
     * should only be used after we have check the validity of it by method
     * whetherShouldFinish().
     *
     */
    public int calculateFinalScores(){
        int sum = 0;
        for(int i : this.scoresRecorder){
            sum += i;
        }
        return sum;
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
