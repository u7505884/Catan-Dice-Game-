package comp1110.ass2;

/**
 * @author
 */
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
    public boolean getWhetherHaveSwapped() {
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

    /**
     * Check if we can realize the swap action based on board_state
     * "swap": followed by two digits 0-5 specifying the resource swapped out and the
     * one it is exchanged for. For example, "swap 1 4" means exchanging 1 Grain for 1 Brick.
     *
     * @param action: The string representation of the structure to be built.
     * @param board_state: The string representation of the board state.
     * @return true iff we can realize the swap action based on board_state, false otherwise.
     */
    public static boolean swap(String action, String board_state) {
        if(!action.contains("swap")){
            return false;
        }
        if(action.contains("swap")){
            if(action.charAt(7)=='0'){
                if(board_state.contains("J1")|| board_state.contains("J6")){return true;}
                else{return false;}
            }
            if(action.charAt(7)=='1'){
                if(board_state.contains("J2") || board_state.contains("J6")){return true;}
                else{return false;}
            }
            if(action.charAt(7)=='2'){
                if(board_state.contains("J3") || board_state.contains("J6")){return true;}
                else{return false;}
            }
            if(action.charAt(7)=='3'){
                if(board_state.contains("J4") || board_state.contains("J6")){return true;}
                else{return false;}
            }
            if(action.charAt(7)=='4'){
                if(board_state.contains("J5") || board_state.contains("J6")){return true;}
                else{return false;}
            }
            if(action.charAt(7)=='5'){
                if(board_state.contains("J6")){return true;}
                else{return false;}
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return "Knight" + super.index;
    }
}
