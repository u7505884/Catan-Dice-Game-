package comp1110.ass2;

public class Knight extends BuildableStructures {
    protected boolean whetherHaveSwapped ;//indicate whether we have used the chance to swap resource from current knight

    public Knight(int index) {//only used for the first knight K1
        super.index = index;
        super.scores = index;
        super.demandOfResources = new int[]{1,1,1,0,0,0};
        this.whetherHaveSwapped = false;
        super.whetherHaveBuilt = false;
    }

    public Knight(int index, Knight lastKnight) {
        super.index = index;
        super.scores = index;
        super.demandOfResources = new int[]{1,1,1,0,0,0};
        super.lastBuildableStructure = lastKnight;//lastnode
        this.whetherHaveSwapped = false;
        super.whetherHaveBuilt = false;
    }

    /**
     * Basic getter method to get class variable whetherHaveSwapped.
     *
     * @return A boolean value which indicates whether we have used the chance to
     * swap resource from current knight.
     */
    public boolean isWhetherHaveSwapped() {
        return whetherHaveSwapped;
    }

    /**
     * Basic setter method to set class variable whetherHaveSwapped.
     * This method should be applied after each time we swap resource with a knight.
     *
     * @param whetherHaveSwapped: The class variable whetherHaveSwapped.
     */
    public void setWhetherHaveSwapped(boolean whetherHaveSwapped) {
        this.whetherHaveSwapped = whetherHaveSwapped;
    }

    @Override
    public String toString(){
        return "Knight" + super.index;
    }
}
