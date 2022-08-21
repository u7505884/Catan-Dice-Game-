package comp1110.ass2;

public class Knight extends BuildableStructures {
    private boolean whetherHaveSwapped = false;//indicate whether we have used the chance to swap resource from current knight

    public Knight(int x, int scores, Resources[] demandOfResources) {
        super.x = x;
        super.scores = scores;
        super.demandOfResources = demandOfResources;
    }

    /**
     * Basic getter method to get class variable whetherHaveSwapped.
     *
     * @return A boolean value which indicates whether we have used the chance to
     * swap resource from current knight.
     */
    public boolean isWhetherHaveSwapped() {
        return false;
    }

    /**
     * Basic setter method to set class variable whetherHaveSwapped.
     * This method should be applied after each time we swap resource with a knight.
     *
     * @param whetherHaveSwapped: The class variable whetherHaveSwapped.
     */
    public void setWhetherHaveSwapped(boolean whetherHaveSwapped) {

    }
}
