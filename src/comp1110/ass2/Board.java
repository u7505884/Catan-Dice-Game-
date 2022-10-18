package comp1110.ass2;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author
 */
public class Board {
    protected final HashMap<Integer, Road> roads = new HashMap<>(); //All roads contained in the board
    protected final HashMap<Integer, Knight> knights = new HashMap<>();//All knights contained in the board
    protected final HashMap<Integer, Settlement> settlements = new HashMap<>();//All settlements contained in the board
    protected final HashMap<Integer, City> cities = new HashMap<>();//All cities contained in the board
    private boolean whetherNewStructureWasBuilt = false;//indicate whether we have build new building in current single round
    private int round = 0;//indicate which turn we are in
    private int[] scoresRecorder = new int[15];//record scores we have gotten or lost in each previous round
    protected int[] currentResource = new int[6];//record current resource we have
    private final static Dice dice = new Dice();
    /**
     * Constructor
     */
    public Board(){
        currentResource = dice.rollDice(6).clone();//initialize current resources
        this.initializeBoard();
    }

    public HashMap<Integer, Road> getRoads() {
        return roads;
    }

    public HashMap<Integer, Knight> getKnights() {
        return knights;
    }

    public HashMap<Integer, Settlement> getSettlements() {
        return settlements;
    }

    public HashMap<Integer, City> getCities() {
        return cities;
    }

    public int getRound() {
        return round;
    }

    public int[] getScoresRecorder() {
        return scoresRecorder;
    }

    public int[] getCurrentResource() {
        return currentResource;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setScoresRecorder(int[] scoresRecorder) {
        this.scoresRecorder = scoresRecorder;
    }

    public void setCurrentResource(int[] currentResource) {
        this.currentResource = currentResource;
    }

    /**
     * Initialize the board, including create all roads, settlements, cities and knights
     */
    public void initializeBoard(){
        //initialize all buildings on board
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
     * roll dice to replace target dice(s)
     *
     * @param dicesWeWantToRoll: an array stands for the dice(s) we want to re-roll.
     *                         E.g.we want to re-roll two ore, then it should be {2,0,0,0,0,0}
     */
    public void roll(int[] dicesWeWantToRoll){
        int sum = Arrays.stream(dicesWeWantToRoll).sum();
        int[] newRes = dice.rollDice(sum);
        for(int i = 0; i < 6; i++){
            currentResource[i] -= dicesWeWantToRoll[i];
            currentResource[i] += newRes[i];
        }
    }

    /**
     * Identify whether we can build current structure by the limit of building constraint
     *
     * @param whatWeWantToBuild: the building we want to construct now
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
     * Identify whether we can build current structure with current resources
     *
     * @param whatWeWantToBuild:  the building we want to construct now
     * @return A boolean value standing for whether it can pass the resources constraint
     */

    public boolean resourcesConstraint(BuildableStructures whatWeWantToBuild ) {
        int[]temp = currentResource.clone();
        if (whatWeWantToBuild.getWhetherHaveBuilt()) {
            return false;
        } else {
            for (int resource : temp) {
                if (resource < 0)
                    return false;
            }
            //Subtract the resources consumed for construction from the current resources
            for (int i = 0; i < temp.length; i++) {
                temp[i] -= whatWeWantToBuild.demandOfResources[i];
            }
            //check whether there is negative number after assumption of deduction
            for (int resource : temp) {
                if (resource < 0)
                    return false;
            }
            return true;
        }
    }

    /**
     * Identify whether we can build current structure by building constraint and resources constraint
     *
     * @param whatWeWantToBuild: The buildable structure we want to build.
     * @return true iff the structure can be built, false otherwise.
     */
    public boolean whetherCanBeBuilt(BuildableStructures whatWeWantToBuild){
        return resourcesConstraint(whatWeWantToBuild) && buildingConstraint(whatWeWantToBuild);
    }

    /**
     * if we can build what we want (whetherCanBeBuilt() return true), then
     * Build the building we want.
     * refresh scoresRecorder and whetherNewStructureWasBuilt
     *
     * @param whatWeWantToBuild: The buildable structure we want to build
     */
    public void build(BuildableStructures whatWeWantToBuild){
        if(whetherCanBeBuilt(whatWeWantToBuild)){
            // build
            whatWeWantToBuild.setWhetherBuild(true);
            // record your score
            scoresRecorder[round] += whatWeWantToBuild.getScores();
            //consume resources
            int[] consumptionOfResources = whatWeWantToBuild.demandOfResources;
            for(int i = 0; i < 6; i++){
                currentResource[i] -= consumptionOfResources[i];
            }
            // record whether we have avoided penalty of no new building
            whetherNewStructureWasBuilt = true;
            System.out.println(whatWeWantToBuild+" has been built");
        }else {
            System.out.println("We can not build " + whatWeWantToBuild +"!");
        }

    }
    /**
     * Identify whether we can trade one resource by gold
     *
     * @return true iff we have at least two gold, false otherwise.
     */
    public boolean whetherCanBeTraded(){
        return currentResource[5]>=2;
    }

    /**
     * if we can trade what we want (whetherCanTrade() return true), then trade it
     *
     * @param newResource the resource we are trying to acquire by trade
     */
    public void trade(Resource newResource){
        if(whetherCanBeTraded()){
            currentResource[5]-=2;
            currentResource[newResource.getIndex()-1]++;
            System.out.println("Trade for " + newResource + " succeed!");
        }else {
            System.out.println("Trade for " + newResource + " failed!");
        }
    }

    /**
     * Identify whether we can swap one resource with resource-specific knights
     *
     * @return true iff the associated knight was built but not used and we have at least
     * one the resource we plan to swap out, false otherwise.
     */
    public boolean whetherCanBeSwapped(Resource resourceAsPrice, Resource newResource){
        if(newResource.getKnight(this).getWhetherHaveBuilt()){
            if(newResource.getKnight(this).getWhetherHaveSwapped()){
                if(currentResource[resourceAsPrice.getIndex()-1]>=1){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Identify whether we can swap one resource with wildcard knight(knight with index 6)
     *
     * @return true iff the associated knight was built without use and we have at least
     * one resource we plan to swap out, false otherwise.
     */
    public boolean whetherCanBeSwappedWithWildcardKnight(Resource resourceAsPrice, Resource newResource){
        if(knights.get(6).getWhetherHaveBuilt()){
            if(knights.get(6).getWhetherHaveSwapped()){
                if(currentResource[resourceAsPrice.getIndex()-1]>=1){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * if we can swap what we want (whetherCanSwap() or whetherCanSwapWithWildcardKnight() return true),
     * then swap it
     *
     * @param resourceAsPrice the resource we swap out
     * @param newResource the resource we are trying to acquire by swap
     */
    public void swap(Resource resourceAsPrice, Resource newResource){
        if(whetherCanBeSwapped(resourceAsPrice, newResource)){//swap with resource-specific knights
            newResource.getKnight(this).setWhetherHaveSwapped(true);
            currentResource[newResource.getIndex()-1]++;
            currentResource[resourceAsPrice.getIndex()-1]--;
            System.out.println("Swap for " + newResource + " succeed!");
        }else if (whetherCanBeSwappedWithWildcardKnight(resourceAsPrice, newResource)){//swap with wildcard knight
            this.knights.get(6).setWhetherHaveSwapped(true);
            currentResource[newResource.getIndex()-1]++;
            currentResource[5]--;
            System.out.println("Swap for " + newResource + " succeed but wildcard knight can not be used anymore!");
        }else{
            System.out.println("Swap for " + newResource + " failed!");
        }
    }

    /**
     * Calculate final scores. What we need to pay attention is, this method
     * should only be used after we have check the validity of it by method
     * whetherShouldFinish().
     *
     */
    public int calculateCurrentFinalScore(){
        int sum = 0;
        for(int i : this.scoresRecorder){
            sum += i;
        }
        return sum;
    }

    /**
     * Check whether player build any structure first. If not there will be a penalty of -2 points
     * Then refresh round and check whether we should finish game
     * If not (means we should enter next round), then we should roll all dices and refresh whetherNewStructureWasBuilt.
     */
    public void nextRound(){
        if(!whetherNewStructureWasBuilt){
            scoresRecorder[round] -= 2;
        }
        round++;
        if(round>=15){
            System.out.println("Final score " + calculateCurrentFinalScore());
            return;
        }else {
            currentResource = dice.rollDice(6).clone();
            whetherNewStructureWasBuilt = false;
        }

    }
}
