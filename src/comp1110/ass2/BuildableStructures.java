package comp1110.ass2;

/**
 * @author
 */
public class BuildableStructures {
    protected int index;//basic building number (The word "basic" means there might be advanced no. in the inheritance class
    protected int scores;//the scores owned by current building
    protected boolean whetherHaveBuilt;//indicate that whether we have already built this
    protected int[] demandOfResources;//indicate that type and amount of resources we need to build current building
    protected BuildableStructures lastBuildableStructure;
    /**
     * Basic getter method to return class variable x.
     *
     * @return An integer x standing for basic building number.
     */
    public int getX(){
        return this.index;
    }

    /**
     * Basic getter method to return class variable scores.
     *
     * @return An integer scores standing for the scores owned by current building.
     */
    public int getScores(){
        return this.scores;
    }

    /**
     * Basic setter method to set class variable whetherHaveBuilt.
     * This method should be applied after each time we finish a construction successfully.
     *
     * @param whetherBuild: The class variable whetherBuild.
     */
    public void setWhetherBuild(boolean whetherBuild) {
        this.whetherHaveBuilt = whetherBuild;
    }

    /**
     * Basic getter method to get class variable demandOfResources.
     *
     * @return An array of Resources which stands for type and amount of resources we need
     * to build current building
     */
    public int[] getDemandOfResources() {
        return demandOfResources;
    }

    /**
     * Basic getter method to get class variable lastBuildableStructure.
     *
     * @return the lastBuildableStructure
     */
    public BuildableStructures getLastBuildableStructure() {
        return lastBuildableStructure;
    }

    /**
     * Basic getter method to get class variable whetherHaveBuilt.
     *
     * @return A boolean value standing for whether we have already built current building.
     */
    public boolean getWhetherHaveBuilt() {
        return whetherHaveBuilt;
    }



}
