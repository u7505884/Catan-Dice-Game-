package comp1110.ass2;

import java.util.*;

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

        //initial modification on board_state
        if(board_state.strip() == ""){
            return true;
        }
        for(int i = 0; i<board_state.length()-1; i++){//return false when there is ",,"
            if(board_state.charAt(i)==','&&board_state.charAt(i+1)==','){
                return false;
            }
        }
        String[] board_state_collections = board_state.split(",");

        //initialize a new board
        Board board = new Board();

        //check the String board_state
        for(String board_state_element:board_state_collections){
            board_state_element = board_state_element.strip();
            if(board_state_element == null){
                return false;
            }
            try{
                switch(board_state_element.charAt(0)) {
                    case ('R'):
                        if (board.roads.get(Integer.valueOf(board_state_element.substring(1)))==null){
                            return false;
                        }
                        board.roads.remove(Integer.valueOf(board_state_element.substring(1)));
                        break;
                    case ('S'):
                        if (board.settlements.get(Integer.valueOf(board_state_element.substring(1)))==null){
                            return false;
                        }
                        board.settlements.remove(Integer.valueOf(board_state_element.substring(1)));
                        break;
                    case ('C'):
                        if (board.cities.get(Integer.valueOf(board_state_element.substring(1)))==null){
                            return false;
                        }
                        board.cities.remove(Integer.valueOf(board_state_element.substring(1)));
                        break;
                    case ('K'),('J'):
                        if (board.knights.get(Integer.valueOf(board_state_element.substring(1)))==null) {
                            return false;
                        }
                        board.knights.remove(Integer.valueOf(board_state_element.substring(1)));
                        break;
                    default:
                        return false;
                }
            }catch(NumberFormatException ex){
                return false;
            }
        }
        return true;
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
        Dice dice = new Dice();
        int[] randomRes = dice.rollDice(n_dice);
        for (int i = 0; i < 6; i++) {
            resource_state[i] += randomRes[i];}
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
        //initialize board based on the board_state
        Board board = new Board();
        String[] board_state_collection = board_state.split(",");
        ArrayList<String> trash = new ArrayList<>();
        for (String board_state_element : board_state_collection) {
            board_state_element = board_state_element.strip();
            final Integer key = Integer.valueOf(board_state_element.substring(1).strip());
            switch (board_state_element.charAt(0)) {
                case ('R') -> board.roads.get(key).setWhetherBuild(true);
                case ('S') -> board.settlements.get(key).setWhetherBuild(true);
                case ('C') -> board.cities.get(key).setWhetherBuild(true);
                case ('J'), ('K') -> board.knights.get(key).setWhetherBuild(true);
                default -> trash.add(board_state_element.substring(1));
            }
        }
        //check structure
        structure = structure.strip();
        final Integer key = Integer.valueOf(structure.substring(1).strip());
        switch (structure.charAt(0)) {
            case ('R'):
                return board.buildingConstraint(board.roads.get(key));
            case ('S'):
                return board.buildingConstraint(board.settlements.get(key));
            case ('C'):
                return board.buildingConstraint(board.cities.get(key));
            case ('J'), ('K'):
                return board.buildingConstraint(board.knights.get(key));
            default:
                return false;
        }
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
        //initialize a new board
        Board board = new Board();
        //check resources
        int indexOfStructure;
        switch(structure.charAt(0)) {
            case ('R'):
                indexOfStructure = Integer.valueOf(structure.substring(1));
                return board.roads.get(indexOfStructure).resourcesConstraint(resource_state);
            case ('J'):
                indexOfStructure = Integer.valueOf(structure.substring(1));
                return board.knights.get(indexOfStructure).resourcesConstraint(resource_state);
            case ('S'):
                indexOfStructure = Integer.valueOf(structure.substring(1));
                return board.settlements.get(indexOfStructure).resourcesConstraint(resource_state);
            case ('C'):
                indexOfStructure = Integer.valueOf(structure.substring(1));
                return board.cities.get(indexOfStructure).resourcesConstraint(resource_state);
            default:
                return false;
        }
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
        String[] s = action.split(" ");
        ArrayList<String> argument = new ArrayList<String>(Arrays.asList(s));
        String playaction=argument.get(0);
        switch(playaction) {
            case ("build"):
                return (s.length == 2 && checkBuildConstraints(argument.get(1), board_state) && checkResources(argument.get(1), resource_state) ) ? true:false;

            case ("trade"):
                return (s.length == 2 && resource_state[5] >= 2)? true:false;

            case ("swap"):
                KnightDavid k = new KnightDavid();
                return (s.length == 3 && (k.KnightDavidSwap(action, board_state)))? true:false;

            default:
                return false;
        }// FIXME: Task #9
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
        for (String action : actions) {
            String[] s = action.split(" ");
            ArrayList<String> argument = new ArrayList<String>(Arrays.asList(s));
            String playaction = argument.get(0);
            if (canDoAction(action, board_state, resource_state)) {
                switch (playaction) {
                    case ("build"):
                        String structure = argument.get(1);
                        board_state = board_state + "," + structure;
                        String b = (argument.get(1)).substring(0, 1);
                        if (b == "R") {
                            resource_state[3]--;
                            resource_state[4]--;
                        } else if (b == "S") {
                            resource_state[1]--;
                            resource_state[2]--;
                            resource_state[3]--;
                            resource_state[4]--;
                        } else if (b == "C") {
                            resource_state[0] -= 3;
                            resource_state[1] -= 2;
                        } else if (b == "J") {
                            resource_state[0]--;
                            resource_state[1]--;
                            resource_state[2]--;
                        }
                    case ("trade"):
                        if (resource_state[5] >= 2) {
                            int n = Integer.parseInt(argument.get(1));
                            resource_state[5] -= 2;
                            resource_state[n] = resource_state[n] + 1;
                        }
                    case ("swap"):

                    default:
                        return false;
                }

            }else
                return false;
        }
        return true;

/ // FIXME: Task #11
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
