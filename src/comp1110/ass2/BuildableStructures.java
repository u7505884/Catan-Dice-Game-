package comp1110.ass2;

public class BuildableStructures {
    protected int x;//basic building number (The word "basic" means there might be advanced no. in the inheritance class
    protected int scores;//the scores owned by current building
    protected boolean whetherHaveBuilt = false;//indicate that whether we have already built this
    protected Resources[] demandOfResources;//indicate that type and amount of resources we need to build current building

    /**
     * Basic getter method to return class variable x.
     *
     * @return An integer x standing for basic building number.
     */
    public int getX(){
        return 0;
    }

    /**
     * Basic getter method to return class variable scores.
     *
     * @return An integer scores standing for the scores owned by current building.
     */
    public int getScores(){
        return 0;
    }

    /**
     * Basic getter method to return class variable whetherHaveBuilt.
     *
     * @return A boolean value standing for whether we have already built current building.
     */
    public boolean isWhetherBuild() {
        return false;
    }

    /**
     * Basic setter method to set class variable whetherHaveBuilt.
     * This method should be applied after each time we finish a construction successfully.
     *
     * @param whetherBuild: The class variable whetherBuild.
     */
    public void setWhetherBuild(boolean whetherBuild) {

    }

    /**
     * Basic getter method to get class variable demandOfResources.
     *
     * @return An array of Resources which stands for type and amount of resources we need
     * to build current building
     */
    public Resources[] getDemandOfResources() {
        return new Resources[0];
    }
}
