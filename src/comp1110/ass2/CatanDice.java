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
        if(board_state.strip() == ""){
            return true;
        }
        for(int i = 0; i<board_state.length()-1; i++){//return false when there is ",,"
            if(board_state.charAt(i)==','&&board_state.charAt(i+1)==','){
                return false;
            }
        }
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
                if(board_state_element == null){
                    return false;
                }
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
                    return false;
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

        Random random = new Random();

        for (int i = 0; i < n_dice; i++) {

            int number = random.nextInt(1, 7);

            switch (number) {
                case 1:
                    resource_state[0] += 1;
                    break;
                case 2:
                    resource_state[1] += 1;
                    break;
                case 3:
                    resource_state[2] += 1;
                    break;
                case 4:
                    resource_state[3] += 1;
                    break;
                case 5:
                    resource_state[4] += 1;
                    break;
                case 6:
                    resource_state[5] += 1;
                    break;
            }
        }
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
        //roads needed for cities, settlements and roads
        String[] ArrayS3 = new String[]{};
        ArrayList<String> S3 = new ArrayList<String>(Arrays.asList(ArrayS3));
        String[] ArrayS4 = new String[]{"R0","R2"};
        ArrayList<String> S4 = new ArrayList<String>(Arrays.asList(ArrayS4));
        String[] ArrayS5 = new String[]{"R0","R2","R3","R5"};
        ArrayList<String> S5 = new ArrayList<String>(Arrays.asList(ArrayS5));
        String[] ArrayS7 = new String[]{"R0","R2","R3","R5","R6","R7"};
        ArrayList<String> S7 = new ArrayList<String>(Arrays.asList(ArrayS7));
        String[] ArrayS9 = new String[]{"R0","R2","R3","R5","R6","R7","R8","R9"};
        ArrayList<String> S9 = new ArrayList<String>(Arrays.asList(ArrayS9));
        String[] ArrayS11 = new String[]{"R0","R2","R3","R5","R6","R7","R8","R9","R10","R11"};
        ArrayList<String> S11 = new ArrayList<String>(Arrays.asList(ArrayS11));
        Map<Integer, ArrayList<String> > settlementsDemandRoads= new HashMap<>();
        settlementsDemandRoads.put(3,S3);
        settlementsDemandRoads.put(4,S4);
        settlementsDemandRoads.put(5,S5);
        settlementsDemandRoads.put(7,S7);
        settlementsDemandRoads.put(9,S9);
        settlementsDemandRoads.put(11,S11);

        String[] ArrayC7 = new String[]{"R0","R1"};
        ArrayList<String> C7 = new ArrayList<String>(Arrays.asList(ArrayC7));
        String[] ArrayC12 = new String[]{"R0","R2","R3","R4"};
        ArrayList<String> C12 = new ArrayList<String>(Arrays.asList(ArrayC12));
        String[] ArrayC20 = new String[]{"R0","R2","R3","R5","R6","R7","R12","R13"};
        ArrayList<String> C20 = new ArrayList<String>(Arrays.asList(ArrayC20));
        String[] ArrayC30 = new String[]{"R0","R2","R3","R5","R6","R7","R12","R13","R14","R15"};
        ArrayList<String> C30 = new ArrayList<String>(Arrays.asList(ArrayC30));
        Map<Integer, ArrayList<String> > citiesDemandRoads= new HashMap<>();
        citiesDemandRoads.put(7,C7);
        citiesDemandRoads.put(12,C12);
        citiesDemandRoads.put(20,C20);
        citiesDemandRoads.put(30,C30);

        String[] ArrayR0 = new String[]{};
        ArrayList<String> R0 = new ArrayList<String>(Arrays.asList(ArrayR0));
        String[] ArrayR1 = new String[]{"R0"};
        ArrayList<String> R1 = new ArrayList<String>(Arrays.asList(ArrayR1));
        String[] ArrayR2 = new String[]{"R0"};
        ArrayList<String> R2 = new ArrayList<String>(Arrays.asList(ArrayR2));
        String[] ArrayR3 = new String[]{"R0","R2"};
        ArrayList<String> R3 = new ArrayList<String>(Arrays.asList(ArrayR3));
        String[] ArrayR4 = new String[]{"R0","R2","R3"};
        ArrayList<String> R4 = new ArrayList<String>(Arrays.asList(ArrayR4));
        String[] ArrayR5 = new String[]{"R0","R2","R3"};
        ArrayList<String> R5 = new ArrayList<String>(Arrays.asList(ArrayR5));
        String[] ArrayR6 = new String[]{"R0","R2","R3","R5"};
        ArrayList<String> R6 = new ArrayList<String>(Arrays.asList(ArrayR6));
        String[] ArrayR7 = new String[]{"R0","R2","R3","R5","R6"};
        ArrayList<String> R7 = new ArrayList<String>(Arrays.asList(ArrayR7));
        String[] ArrayR8 = new String[]{"R0","R2","R3","R5","R6","R7"};
        ArrayList<String> R8 = new ArrayList<String>(Arrays.asList(ArrayR8));
        String[] ArrayR9 = new String[]{"R0","R2","R3","R5","R6","R7","R8"};
        ArrayList<String> R9 = new ArrayList<String>(Arrays.asList(ArrayR9));
        String[] ArrayR10 = new String[]{"R0","R2","R3","R5","R6","R7","R8","R9"};
        ArrayList<String> R10 = new ArrayList<String>(Arrays.asList(ArrayR10));
        String[] ArrayR11 = new String[]{"R0","R2","R3","R5","R6","R7","R8","R9","R10"};
        ArrayList<String> R11 = new ArrayList<String>(Arrays.asList(ArrayR11));
        String[] ArrayR12 = new String[]{"R0","R2","R3","R5","R6","R7",};
        ArrayList<String> R12 = new ArrayList<String>(Arrays.asList(ArrayR12));
        String[] ArrayR13 = new String[]{"R0","R2","R3","R5","R6","R7","R12"};
        ArrayList<String> R13 = new ArrayList<String>(Arrays.asList(ArrayR13));
        String[] ArrayR14 = new String[]{"R0","R2","R3","R5","R6","R7","R12","R13"};
        ArrayList<String> R14 = new ArrayList<String>(Arrays.asList(ArrayR14));
        String[] ArrayR15 = new String[]{"R0","R2","R3","R5","R6","R7","R12","R13","R14","R15"};
        ArrayList<String> R15 = new ArrayList<String>(Arrays.asList(ArrayR15));
        Map<Integer, ArrayList<String> > roadsDemandRoads= new HashMap<>();
        roadsDemandRoads.put(0,R0);
        roadsDemandRoads.put(1,R1);
        roadsDemandRoads.put(2,R2);
        roadsDemandRoads.put(3,R3);
        roadsDemandRoads.put(4,R4);
        roadsDemandRoads.put(5,R5);
        roadsDemandRoads.put(6,R6);
        roadsDemandRoads.put(7,R7);
        roadsDemandRoads.put(8,R8);
        roadsDemandRoads.put(9,R9);
        roadsDemandRoads.put(10,R10);
        roadsDemandRoads.put(11,R11);
        roadsDemandRoads.put(12,R12);
        roadsDemandRoads.put(13,R13);
        roadsDemandRoads.put(14,R14);
        roadsDemandRoads.put(15,R15);


        String[] ArrayR = new String[]{"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
        ArrayList<String> R = new ArrayList<String>(Arrays.asList(ArrayR));
        String[] ArrayS = new String[]{"3","4","5","7","9","11"};
        ArrayList<String> S = new ArrayList<String>(Arrays.asList(ArrayS));
        String[] ArrayC = new String[]{"7","12","20","30"};
        ArrayList<String> C = new ArrayList<String>(Arrays.asList(ArrayC));
        String[] ArrayJ = new String[]{"1","2","3","4","5","6"};
        ArrayList<String> J = new ArrayList<String>(Arrays.asList(ArrayJ));

        String[] board_state_collection = board_state.split(",");

        ArrayList<String> board_state_collections = new ArrayList<String>(Arrays.asList(board_state_collection));
        ArrayList<String> board_state_R = new ArrayList<>();
        ArrayList<String> board_state_S = new ArrayList<>();
        ArrayList<String> board_state_C = new ArrayList<>();
        ArrayList<String> board_state_J = new ArrayList<>();
        ArrayList<String> trash = new ArrayList<>();

        for(String board_state_element:board_state_collections){
            board_state_element = board_state_element.strip();
            switch(board_state_element.charAt(0)){
                case('R')->board_state_R.add(board_state_element.substring(1));
                case('S')->board_state_S.add(board_state_element.substring(1));
                case('C')->board_state_C.add(board_state_element.substring(1));
                case('J'), ('K') ->board_state_J.add(board_state_element.substring(1));
                default->trash.add(board_state_element.substring(1));
            }
        }

        ArrayList<String> board_state_R_left = R;
        ArrayList<String> board_state_S_left = S;
        ArrayList<String> board_state_C_left = C;
        ArrayList<String> board_state_J_left = J;

        board_state_R_left.removeAll(board_state_R);
        board_state_S_left.removeAll(board_state_S);
        board_state_C_left.removeAll(board_state_C);
        board_state_J_left.removeAll(board_state_J);
        int min = Integer.MAX_VALUE;
        switch(structure.charAt(0)){
            case('R'):
                return board_state_collections.containsAll(roadsDemandRoads.get(Integer.valueOf(structure.substring(1))));

            case('S'):
                for(String element :board_state_S_left){
                    int currentValue = Integer.valueOf(element);
                    if(currentValue < min){
                        min = currentValue;
                    }
                }
                if(Integer.valueOf(structure.substring(1)) == min&&board_state_collections.containsAll(settlementsDemandRoads.get(Integer.valueOf(structure.substring(1))))){
                    return true;
                }else{
                    return false;
                }

            case('C'):
                for(String element :board_state_C_left){
                    int currentValue = Integer.valueOf(element);
                    if(currentValue < min){
                        min = currentValue;
                    }
                }

                if(Integer.valueOf(structure.substring(1)) == min && board_state_collections.containsAll(citiesDemandRoads.get(Integer.valueOf(structure.substring(1))))) {
                    return true;
                }else {
                    return false;
                }

            case('J'):
                for(String element :board_state_J_left){
                    int currentValue = Integer.valueOf(element);
                    if(currentValue < min){
                        min = currentValue;
                    }
                }
                if(Integer.valueOf(structure.substring(1)) == min){
                    return true;
                }else{
                    return false;
                }


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
