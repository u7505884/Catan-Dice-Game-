package comp1110.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CatanDice {

    /**
     * Check if the string encoding of a board state is well formed.
     * Note that this does not mean checking if the state is valid
     * (represents a state that the player could get to in game play),
     * only that the string representation is syntactically well formed.
     *
     * @param board_state: The string representation of the board state.
     * @return true iff the string is a well-formed representation of
     *         a board state, false otherwise.
     */
    public static boolean isBoardStateWellFormed(String board_state) {
        //FIXME: Task #3
        String[] ArrayR = new String[]{"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
        ArrayList<String> R = new ArrayList<String>(Arrays.asList(ArrayR));
        String[] ArrayS = new String[]{"3","4","5","7","9","11"};
        ArrayList<String> S = new ArrayList<String>(Arrays.asList(ArrayS));
        String[] ArrayC = new String[]{"7","12","20","30"};
        ArrayList<String> C = new ArrayList<String>(Arrays.asList(ArrayC));
        String[] ArrayK = new String[]{"1","2","3","4","5","6"};
        ArrayList<String> K = new ArrayList<String>(Arrays.asList(ArrayK));
        String[] ArrayJ = new String[]{"1","2","3","4","5","6"};
        ArrayList<String> J = new ArrayList<String>(Arrays.asList(ArrayJ));
        String[] board_state_collections = board_state.split(",");

        int indicator = 1;

            for(String board_state_element:board_state_collections){
                board_state_element = board_state_element.strip();
                try{
                    switch(board_state_element.charAt(0)) {
                        case ('R'):
                            if (R.contains(board_state_element.substring(1))) {
                                indicator *= 1;
                            } else {
                                indicator *= 0;
                            }
                            break;
                        case ('S'):
                            if (S.contains(board_state_element.substring(1))) {
                                indicator *= 1;
                            } else {
                                indicator *= 0;
                            }
                            break;
                        case ('C'):
                            if (C.contains(board_state_element.substring(1))) {
                                indicator *= 1;
                            } else {
                                indicator *= 0;
                            }
                            break;
                        case ('K'):
                            if (K.contains(board_state_element.substring(1))) {
                                indicator *= 1;
                                J.remove(board_state_element.substring(1));
                            } else {
                                indicator *= 0;
                            }
                            break;
                        case ('J'):
                            if (J.contains(board_state_element.substring(1))) {
                                indicator *= 1;
                                K.remove(board_state_element.substring(1));
                            } else {
                                indicator *= 0;
                            }
                            break;
                        default:
                            return false;
                    }
                }catch(StringIndexOutOfBoundsException ex){
                    continue;
                }
            }


        if(indicator == 1){
            return true;
        }else{
            return false;
        }
    }



    /**
     * Check if the string encoding of a player action is well formed.
     *
     * @param action: The string representation of the action.
     * @return true iff the string is a well-formed representation of
     *         a board state, false otherwise.
     */
    public static boolean isActionWellFormed(String action) {
        // FIXME: Task #4
        String[] s = action.split(" ");
        ArrayList<String> argument = new ArrayList<String>(Arrays.asList(s));
        switch (argument.get(0)) {
            case ("build"):
                if (argument.size() == 2)
                    return isBoardStateWellFormed(argument.get(1));
                else
                    return false;
            case ("trade"):
                if (argument.size() == 2)
                    return Integer.parseInt(argument.get(1)) >= 0 & Integer.parseInt(argument.get(1)) <= 5;
                else
                    return false;
            case ("swap"):
                if (argument.size() == 3)
                    return (Integer.parseInt(argument.get(1)) >= 0 & Integer.parseInt(argument.get(1)) <= 5)
                            & (Integer.parseInt(argument.get(2)) >= 0 & Integer.parseInt(argument.get(2)) <= 5);
                else
                    return false;
            default:
                return false;
        }
    }

    /**
     * Roll the specified number of dice and add the result to the
     * resource state.
     *
     * The resource state on input is not necessarily empty. This
     * method should only _add_ the outcome of the dice rolled to
     * the resource state, not remove or clear the resources already
     * represented in it.
     *
     * @param n_dice: The number of dice to roll (>= 0).
     * @param resource_state: The available resources that the dice
     *        roll will be added to.
     *
     * This method does not return any value. It should update the given
     * resource_state.
     */
    public static void rollDice(int n_dice, int[] resource_state) {
	// FIXME: Task #6
    }

    /**
     * Check if the specified structure can be built next, given the
     * current board state. This method should check that the build
     * meets the constraints described in section "Building Constraints"
     * of the README file.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param board_state: The string representation of the board state.
     * @return true iff the structure is a possible next build, false
     *         otherwise.
     */
    public static boolean checkBuildConstraints(String structure, String board_state) {
	// FIXME: Task #8
        return false;
    }

    /**
     * Check if the available resources are sufficient to build the
     * specified structure, without considering trades or swaps.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     *         resources, false otherwise.
     */
    public static boolean checkResources(String structure, int[] resource_state) {
	// FIXME: Task #7
        int[] roadResources = new int[]{0,0,0,1,1,0};
        int[] KnightResources = new int[]{1,1,1,0,0,0};
        int[] SettlementResources = new int[]{0,1,1,1,1,0};
        int[] CityResources = new int[]{3,2,0,0,0,0};
        switch(structure.charAt(0)) {
            case ('R'):
                for(int i = 0; i < 6; i++){
                    if(resource_state[i]<roadResources[i]){
                        return false;
                    }
                }
                break;
            case ('J'):
                for(int i = 0; i < 6; i++){
                    if(resource_state[i]<KnightResources[i]){
                        return false;
                    }
                }
                break;
            case ('S'):
                for(int i = 0; i < 6; i++){
                    if(resource_state[i]<SettlementResources[i]){
                        return false;
                    }
                }
                break;
            case ('C'):
                for(int i = 0; i < 6; i++){
                    if(resource_state[i]<CityResources[i]){
                        return false;
                    }
                }
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * Check if the available resources are sufficient to build the
     * specified structure, considering also trades and/or swaps.
     * This method needs access to the current board state because the
     * board state encodes which Knights are available to perform swaps.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     *         resources, false otherwise.
     */
    public static boolean checkResourcesWithTradeAndSwap(String structure,
							 String board_state,
							 int[] resource_state) {
	return false; // FIXME: Task #12
    }

    /**
     * Check if a player action (build, trade or swap) is executable in the
     * given board and resource state.
     *
     * @param action: String representatiion of the action to check.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action is applicable, false otherwise.
     */
    public static boolean canDoAction(String action,
				      String board_state,
				      int[] resource_state) {
	 return false; // FIXME: Task #9
    }

    /**
     * Check if the specified sequence of player actions is executable
     * from the given board and resource state.
     *
     * @param actions: The sequence of (string representatins of) actions.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action sequence is executable, false otherwise.
     */
    public static boolean canDoSequence(String[] actions,
					String board_state,
					int[] resource_state) {
	 return false; // FIXME: Task #11
    }

    /**
     * Find the path of roads that need to be built to reach a specified
     * (unbuilt) structure in the current board state. The roads should
     * be returned as an array of their string representation, in the
     * order in which they have to be built. The array should _not_ include
     * the target structure (even if it is a road). If the target structure
     * is reachable via the already built roads, the method should return
     * an empty array.
     * 
     * Note that on the Island One map, there is a unique path to every
     * structure. 
     *
     * @param target_structure: The string representation of the structure
     *        to reach.
     * @param board_state: The string representation of the board state.
     * @return An array of string representations of the roads along the
     *         path.
     */
    public static String[] pathTo(String target_structure,
				  String board_state) {
	String[] result = {};
	return result; // FIXME: Task #13
    }

    /**
     * Generate a plan (sequence of player actions) to build the target
     * structure from the given board and resource state. The plan may
     * include trades and swaps, as well as bulding other structures if
     * needed to reach the target structure or to satisfy the build order
     * constraints.
     *
     * However, the plan must not have redundant actions. This means it
     * must not build any other structure that is not necessary to meet
     * the building constraints for the target structure, and it must not
     * trade or swap for resources if those resources are not needed.
     *
     * If there is no valid build plan for the target structure from the
     * specified state, return null.
     *
     * @param target_structure: The string representation of the structure
     *        to be built.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return An array of string representations of player actions. If
     *         there exists no valid build plan for the target structure,
     *         the method should return null.
     */
    public static String[] buildPlan(String target_structure,
				     String board_state,
				     int[] resource_state) {
	 return null; // FIXME: Task #14
    }

}
