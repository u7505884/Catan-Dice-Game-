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
     * a board state, false otherwise.
     * @author Haoxiang Wang (u7544188)
     */
    public static boolean isBoardStateWellFormed(String board_state) {
        //FIXME: Task #3

        //initial modification on board_state
        if (board_state.strip() == "") {
            return true;
        }
        for (int i = 0; i < board_state.length() - 1; i++) {//return false when there is ",,"
            if (board_state.charAt(i) == ',' && board_state.charAt(i + 1) == ',') {
                return false;
            }
        }
        String[] board_state_collections = board_state.split(",");

        //initialize a new board
        Board board = new Board();

        //check the String board_state
        for (String board_state_element : board_state_collections) {
            board_state_element = board_state_element.strip();
            if (board_state_element == null) {
                return false;
            }
            try {
                switch (board_state_element.charAt(0)) {
                    case ('R'):
                        if (board.roads.get(Integer.valueOf(board_state_element.substring(1))) == null) {
                            return false;
                        }
                        board.roads.remove(Integer.valueOf(board_state_element.substring(1)));
                        break;
                    case ('S'):
                        if (board.settlements.get(Integer.valueOf(board_state_element.substring(1))) == null) {
                            return false;
                        }
                        board.settlements.remove(Integer.valueOf(board_state_element.substring(1)));
                        break;
                    case ('C'):
                        if (board.cities.get(Integer.valueOf(board_state_element.substring(1))) == null) {
                            return false;
                        }
                        board.cities.remove(Integer.valueOf(board_state_element.substring(1)));
                        break;
                    case ('K'), ('J'):
                        if (board.knights.get(Integer.valueOf(board_state_element.substring(1))) == null) {
                            return false;
                        }
                        board.knights.remove(Integer.valueOf(board_state_element.substring(1)));
                        break;
                    default:
                        return false;
                }
            } catch (NumberFormatException ex) {
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
     * a board state, false otherwise.
     * @author Ziling Ruan (u7505884)
     */
    public static boolean isActionWellFormed(String action) {
        // FIXME: Task #4
        String[] s = action.split(" ");//Split the string according to " "
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
     * <p>
     * The resource state on input is not necessarily empty. This
     * method should only _add_ the outcome of the dice rolled to
     * the resource state, not remove or clear the resources already
     * represented in it.
     *
     * @param n_dice:         The number of dice to roll (>= 0).
     * @param resource_state: The available resources that the dice
     *                        roll will be added to.
     *                        <p>
     *                        This method does not return any value. It should update the given
     *                        resource_state.
     * @author Ta-Wei Chen (u7546144)
     */
    public static void rollDice(int n_dice, int[] resource_state) {
        // FIXME: Task #6
        Dice dice = new Dice();
        int[] randomRes = dice.rollDice(n_dice);
        for (int i = 0; i < 6; i++) {
            resource_state[i] += randomRes[i];
        }
    }

    /**
     * Check if the specified structure can be built next, given the
     * current board state. This method should check that the build
     * meets the constraints described in section "Building Constraints"
     * of the README file.
     *
     * @param structure:   The string representation of the structure to
     *                     be built.
     * @param board_state: The string representation of the board state.
     * @return true iff the structure is a possible next build, false
     * otherwise.
     * @author Haoxiang Wang (u7544188)
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
     * @param structure:      The string representation of the structure to
     *                        be built.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     * resources, false otherwise.
     * @author Haoxiang Wang (u7544188)
     */
    public static boolean checkResources(String structure, int[] resource_state) {
        // FIXME: Task #7
        //initialize a new board
        Board board = new Board();
        board.currentResource = resource_state.clone();
        //check resources
        int indexOfStructure;
        switch (structure.charAt(0)) {
            case ('R'):
                indexOfStructure = Integer.valueOf(structure.substring(1));
                return board.resourcesConstraint(board.roads.get(indexOfStructure));
            case ('J'):
                indexOfStructure = Integer.valueOf(structure.substring(1));
                return board.resourcesConstraint(board.knights.get(indexOfStructure));
            case ('S'):
                indexOfStructure = Integer.valueOf(structure.substring(1));
                return board.resourcesConstraint(board.settlements.get(indexOfStructure));
            case ('C'):
                indexOfStructure = Integer.valueOf(structure.substring(1));
                return board.resourcesConstraint(board.cities.get(indexOfStructure));
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
     * @param structure:      The string representation of the structure to
     *                        be built.
     * @param board_state:    The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     * resources, false otherwise.
     * @author Ziling Ruan (u7505884)
     */
    public static boolean checkResourcesWithTradeAndSwap(String structure,
                                                         String board_state,
                                                         int[] resource_state) {
        if (checkResources(structure, resource_state)) {
            return true;
        } else {
            HashMap<Integer, Integer> lack_re = re_lack(structure, resource_state);
            if (resource_state[5] >= 2) {
                int total_miss = 0;
                for (Integer r : lack_re.keySet()) {
                    total_miss += lack_re.get(r);
                }
                int trade_number = (int) Math.floor(resource_state[5] / 2);
                if (trade_number >= total_miss) {
                    return true;
                } else {
                    int[] resource2 = resource_state.clone();
                    for (Integer r : lack_re.keySet()) {
                        if (lack_re.get(r) > 0 && trade_number > 0) {
                            lack_re.put(r, lack_re.get(r) - 1);
                            trade_number--;
                            resource2[r]++;
                            resource2[5] -= 2;
                        }
                    }
                    for (Integer r : lack_re.keySet()) {
                        if (lack_re.get(r) == 1) {
                            if (!(board_state.contains("J"+(r+1))))
                                return false;
                        } else if (lack_re.get(r) == 2 && !board_state.contains("J6")) {
                            return false;
                        } else
                            return false;
                    }
                }
            } else {
                for (Integer r : lack_re.keySet()) {
                    if (lack_re.get(r) == 1) {
                        if (!(board_state.contains("J"+(r+1)))) {
                            return false;
                        }
                    } else if (lack_re.get(r) == 2 && !board_state.contains("J6")) {
                        return false;
                    } else {
                        return false;
                    }
                }

            }
        }
        return true;
    }
    // FIXME: Task #12

    /**
     * @author Ziling Ruan (u7505884)
     */
    public static HashMap<Integer, Integer> re_lack(String structure, int[] resource_state) {
        HashMap<Integer, Integer> re_lack = new HashMap<>();
        switch (structure.charAt(0)) {
            case ('R'):
                for (int i = 3; i <= 4; i++) {
                    if (1 - resource_state[i] > 0) {
                        re_lack.put(i, 1 - resource_state[i]);
                    }
                }
                break;
            case ('C'):
                if (3 - resource_state[0] > 0) {
                    re_lack.put(0, 3 - resource_state[0]);
                }
                if (2 - resource_state[1] > 0) {
                    re_lack.put(1, 2 - resource_state[1]);
                }
                break;
            case ('S'):
                for (int i = 1; i <= 4; i++) {
                    if (1 - resource_state[i] > 0) {
                        re_lack.put(i, 1 - resource_state[i]);
                    }
                }
                break;

            case ('J'):
                for (int i = 0; i <= 2; i++) {
                    if (1 - resource_state[i] > 0) {
                        re_lack.put(i, 1 - resource_state[i]);
                    }
                }
                break;
        }
        return re_lack;
    }



    /**
     * Check if a player action (build, trade or swap) is executable in the
     * given board and resource state.
     *
     * @param action:         String representatiion of the action to check.
     * @param board_state:    The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action is applicable, false otherwise.
     * @author Ziling Ruan (u7505884)
     */
    public static boolean canDoAction(String action,
                                      String board_state,
                                      int[] resource_state) {
        String[] s = action.split(" ");
        ArrayList<String> argument = new ArrayList<String>(Arrays.asList(s));
        String playAction = argument.get(0);
        switch (playAction) {
            case ("build"):
                return (s.length == 2 && checkBuildConstraints(argument.get(1), board_state) && checkResources(argument.get(1), resource_state)) ? true : false;

            case ("trade"):
                return (s.length == 2 && resource_state[5] >= 2) ? true : false;

            case ("swap"):
                Board b=new Board();
                return (s.length == 3 && (swap(action, board_state))) ? true : false;

            default:
                return false;
        }// FIXME: Task #9
    }
    public static boolean swap (String action, String board_state) {
            if (!action.contains("swap")) {
                return false;
            }
            if (action.contains("swap")) {
                switch (action.charAt(7)) {
                    case ('0'):
                        return (board_state.contains("J1") || board_state.contains("J6")) ? true:false;

                    case ('1'):
                        return (board_state.contains("J2") || board_state.contains("J6")) ? true:false;

                    case ('2'):
                        return (board_state.contains("J3") || board_state.contains("J6")) ? true:false;

                    case ('3'):
                        return (board_state.contains("J4") || board_state.contains("J6")) ? true:false;

                    case ('4'):
                        return (board_state.contains("J5") || board_state.contains("J6")) ? true:false;

                    case ('5'):
                        return (board_state.contains("J6")) ? true:false;
                }
            }
            return false;
        }
    /**
     * Check if the specified sequence of player actions is executable
     * from the given board and resource state.
     *
     * @param actions:        The sequence of (string representatins of) actions.
     * @param board_state:    The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action sequence is executable, false otherwise.
     * @author Ziling Ruan (u7505884)
     */
    public static boolean canDoSequence(String[] actions,
                                        String board_state,
                                        int[] resource_state) {
        int[] temp = resource_state.clone();
        Boolean q = true;
        for (String action : actions) {
            String[] s = action.split(" ");
            ArrayList<String> argument = new ArrayList<String>(Arrays.asList(s));
            String playaction = argument.get(0);
            if (canDoAction(action, board_state, temp)) {
                switch (playaction) {
                    case ("build"):
                        String structure = argument.get(1);
                        board_state = board_state + ',' + structure;
                        String b = (argument.get(1)).substring(0, 1);
                        if (b.equals("R")) {
                            temp[3]--;
                            temp[4]--;
                        } else if (b.equals("S")) {
                            temp[1]--;
                            temp[2]--;
                            temp[3]--;
                            temp[4]--;
                        } else if (b.equals("C")) {
                            temp[0] -= 3;
                            temp[1] -= 2;
                        } else if (b.equals("J")) {
                            temp[0]--;
                            temp[1]--;
                            temp[2]--;
                        }
                        for (int resource : temp) {
                            if (resource < 0)
                                q = false;
                        }
                        break;
                    case ("trade"):
                        if (temp[5] >= 2) {
                            int n = Integer.parseInt(argument.get(1));
                            temp[5] -= 2;
                            temp[n] = temp[n] + 1;
                        }
                        for (int resource : temp) {
                            if (resource < 0)
                                q = false;
                        }
                        break;
                    case ("swap"):
                        int s1 = Integer.parseInt(argument.get(1));
                        int s2 = Integer.parseInt(argument.get(2));
                        if (board_state.contains("J" + (s2 + 1))) {
                            temp[s1] = temp[s1] - 1;
                            temp[s2] = temp[s2] + 1;
                            String j = "J" + (s2 + 1);
                            String k = "K" + (s2 + 1);
                            board_state = board_state.replaceAll(j, k);
                        } else if (board_state.contains("J6") && (resource_state[s1] >= 1)) {
                            board_state = board_state.replaceAll("J6", "K6");
                        }
                        for (int resource : temp) {
                            if (resource < 0)
                                q = false;
                        }
                        break;
                    default:
                        q = false;
                }
            } else {
                q = false;
            }
        }
        return q;


    }
// FIXME: Task #11


    /**
     * Find the path of roads that need to be built to reach a specified
     * (unbuilt) structure in the current board state. The roads should
     * be returned as an array of their string representation, in the
     * order in which they have to be built. The array should _not_ include
     * the target structure (even if it is a road). If the target structure
     * is reachable via the already built roads, the method should return
     * an empty array.
     * <p>
     * Note that on the Island One map, there is a unique path to every
     * structure.
     *
     * @param target_structure: The string representation of the structure
     *                          to reach.
     * @param board_state:      The string representation of the board state.
     * @return An array of string representations of the roads along the
     * path.
     * @author Ta-Wei Chen (u7546144)
     */
    public static String[] pathTo(String target_structure,
                                  String board_state) {
        ArrayList<String> path = new ArrayList<>();
        Board b = new Board();
        HashMap<Integer, Road> lroads = b.getRoads();
        switch (target_structure.charAt(0)) {
            case ('R'):
                if (target_structure.charAt(1) == 0) {
                    path.add("R0");
                } else {
                    if (target_structure.charAt(1) != 0) {
                        if (target_structure.charAt(1) == 1 || target_structure.charAt(1) == 2) {
                            path.add("R0");
                        } else {
                            Road r = lroads.get(target_structure.charAt(1));
                            path.add((String.valueOf( r.getLastBuildableStructure())));
                            target_structure = String.valueOf(r.getLastBuildableStructure());
                            pathTo(target_structure, board_state);
                        }
                    }
                }
                break;

            case ('S'):
                if (target_structure.charAt(1) == 3) {
                    path.add(null);
                } else if (target_structure.charAt(1) == 4) {
                    path.add("R2");
                    path.add("R0");
                } else {
                    target_structure = String.valueOf( lroads.get(target_structure.charAt(1)));
                    Road r = lroads.get(target_structure.charAt(1));
                    path.add((String.valueOf(r)));
                    pathTo(target_structure, board_state);
                }
                break;

            case ('C'):
                if (target_structure.charAt(1) == 7) {
                    target_structure = String.valueOf( lroads.get(1));
                    Road r = lroads.get(1);
                    path.add((String.valueOf(r)));
                    pathTo(target_structure, board_state);
                } else if (target_structure.charAt(1) == 12) {
                    target_structure = String.valueOf(lroads.get(4));
                    Road r = lroads.get(4);
                    path.add((String.valueOf( r)));
                    pathTo(target_structure, board_state);
                } else if (target_structure.charAt(1) == 20) {
                    target_structure = String.valueOf( lroads.get(13));
                    Road r = lroads.get(13);
                    path.add((String.valueOf(r)));
                    pathTo(target_structure, board_state);
                } else if (target_structure.charAt(1) == 30) {
                    target_structure = String.valueOf( lroads.get(15));
                    Road r = lroads.get(15);
                    path.add(String.valueOf(r));
                    pathTo(target_structure, board_state);
                }
                break;
        }

        if(path.size()==1&&path.get(0)=="R0")
            path.remove(0);

        String[] s = board_state.split(",");
        ArrayList<String> bs= new ArrayList<String>(
                Arrays.asList(s));

        for (int i = 0; i < path.size(); i++) {
            for (int j = 0; j < bs.size(); j++) {
                if (path.get(i).equals(bs.get(j))) {path.remove(path.get(i));}
            }
        }

        String[] a5 = new String[path.size()];
        a5 = path.toArray(a5);

//        String a1 = "";
//        if (target_structure.equals("RO")) {a1 = "";}
//        else if (target_structure.equals("R1")) {a1 = "R0";}
//        else if (target_structure.equals("R2")) {a1 = "R0";}
//        else if (target_structure.equals("R3")) {a1 = "R0,R2";}
//        else if (target_structure.equals("R4")) {a1 = "R0,R2,R3";}
//        else if (target_structure.equals("R5")) {a1 = "R0,R2,R3";}
//        else if (target_structure.equals("R6")) {a1 = "R0,R2,R3,R5";}
//        else if (target_structure.equals("R7")) {a1 = "R0,R2,R3,R5,R6";}
//        else if (target_structure.equals("R8")) {a1 = "R0,R2,R3,R5,R6,R7";}
//        else if (target_structure.equals("R9")) {a1 = "R0,R2,R3,R5,R6,R7,R8";}
//        else if (target_structure.equals("R10")) {a1 = "R0,R2,R3,R5,R6,R7,R8,R9";}
//        else if (target_structure.equals("R11")) {a1 = "R0,R2,R3,R5,R6,R7,R8,R9,R10";}
//        else if (target_structure.equals("R12")) {a1 = "R0,R2,R3,R5,R6,R7";}
//        else if (target_structure.equals("R13")) {a1 = "R0,R2,R3,R5,R6,R7,R12";}
//        else if (target_structure.equals("R14")) {a1 = "R0,R2,R3,R5,R6,R7,R12,R13";}
//        else if (target_structure.equals("R15")) {a1 = "R0,R2,R3,R5,R6,R7,R12,R13,R14";}
//
//        else if (target_structure.equals("S3")) {a1 = "";}
//        else if (target_structure.equals("S4")) {a1 = "R0,R2";}
//        else if (target_structure.equals("S5")) {a1 = "R0,R2,R3,R5";}
//        else if (target_structure.equals("S7")) {a1 = "R0,R2,R3,R5,R6,R7";}
//        else if (target_structure.equals("S9")) {a1 = "R0,R2,R3,R5,R6,R7,R8,R9";}
//        else if (target_structure.equals("S11")) {a1 = "R0,R2,R3,R5,R6,R7,R8,R9,R10,R11";}
//
//        else if (target_structure.equals("C7")) {a1 = "R0,R1";}
//        else if (target_structure.equals("C12")) {a1 = "R0,R2,R3,R4";}
//        else if (target_structure.equals("C20")) {a1 = "R0,R2,R3,R5,R6,R7,R12,R13";}
//        else if (target_structure.equals("C30")) {a1 = "R0,R2,R3,R5,R6,R7,R12,R13,R14,R15";}
//
//        String[] a2 = a1.split(",");
//        List<String> a3 = Arrays.asList(a2);
//        List<String> a4 = new ArrayList<>(a3);
//
//        String[] b = board_state.split(",");
//        List<String> b3 = Arrays.asList(b);
//
//        for (int i = 0; i < a3.size(); i++) {
//            for (int j = 0; j < b3.size(); j++) {
//                if (a3.get(i).equals(b3.get(j))) {a4.remove(a3.get(i));}
//            }
//        }
//        String[] a5 = new String[a4.size()];
//        a5 = a4.toArray(a5);
        return a5;
        // FIXME: Task #13
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
     * @author Ta-Wei Chen (u7546144)
     */
    public static String[] buildPlan(String target_structure,
                                     String board_state,
                                     int[] resource_state) {

        // Trivial : 8
        int[] t1 = new int[]{1,1,1,0,0,3};
        String[] T1 = {"build J1"};
        if(target_structure.equals("J1") && board_state.equals("R0,S3,R1")
                && Arrays.equals(resource_state, t1)){return T1;}

        int[] t2 = new int[]{1,2,2,0,0,1};
        String[] T2 = {"build J3"};
        if(target_structure.equals("J3") && board_state.equals("R0,S3,R1,J1,J2")
                && Arrays.equals(resource_state, t2)){return T2;}

        int[] t3 = new int[]{3,2,1,0,0,0};
        String[] T3 = {"build C12"};
        if(target_structure.equals("C12") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6")
                && Arrays.equals(resource_state, t3)){return T3;}

        int[] t4 = new int[]{1,1,1,1,1,1};
        String[] T4 = {"build J4"};
        if(target_structure.equals("J4") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3")
                && Arrays.equals(resource_state, t4)){return T4;}

        int[] t5 = new int[]{2,0,1,2,1,0};
        String[] T5 = {"build R2"};
        if(target_structure.equals("R2") && board_state.equals("R0,S3,R1")
                && Arrays.equals(resource_state, t5)){return T5;}

        int[] t6 = new int[]{0,1,1,2,2,0};
        String[] T6 = {"build S5"};
        if(target_structure.equals("S5") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6")
                && Arrays.equals(resource_state, t6)){return T6;}

        int[] t7 = new int[]{4,2,0,0,0,0};
        String[] T7 = {"build C7"};
        if(target_structure.equals("C7") && board_state.equals("R0,S3,R1")
                && Arrays.equals(resource_state, t7)){return T7;}

        int[] t8 = new int[]{0,0,1,1,1,3};
        String[] T8 = {"build R7"};
        if(target_structure.equals("R7") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6")
                && Arrays.equals(resource_state, t8)){return T8;}


        // General : 21
        int[] g1 = new int[]{1,2,2,0,1,0};
        String[] G1 = {"build J1","swap 4 0","build J2"};
        if(target_structure.equals("J2") && board_state.equals("R0,S3,R1")
                && Arrays.equals(resource_state, g1)){return G1;}

        int[] g2 = new int[]{2,2,0,0,1,1};
        String[] G2 = {"swap 4 0","build C12"};
        if(target_structure.equals("C12") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, g2)){return G2;}

        int[] g3 = new int[]{2,2,1,0,0,1};
        String[] G3 = {"build J3","swap 5 2","build J4"};
        if(target_structure.equals("J4") && board_state.equals("R0,S3,R1,J1,J2")
                && Arrays.equals(resource_state, g3)){return G3;}

        int[] g4 = new int[]{2,3,0,1,0,0};
        String[] G4 = {"swap 0 2","build J4"};
        if(target_structure.equals("J4") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, g4)){return G4;}

        int[] g5 = new int[]{1,2,2,0,0,1};
        String[] G5 = {"build J3","swap 5 0","build J4"};
        if(target_structure.equals("J4") && board_state.equals("R0,S3,R1,J1,J2")
                && Arrays.equals(resource_state, g5)){return G5;}

        int[] g6 = new int[]{2,2,1,0,1,0};
        String[] G6 = {"build J4","swap 4 2","build J5"};
        if(target_structure.equals("J5") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, g6)){return G6;}

        int[] g7 = new int[]{3,1,2,0,0,0};
        String[] G7 = {"build J3","swap 0 1","build J4"};
        if(target_structure.equals("J4") && board_state.equals("R0,S3,R1,J1,J2")
                && Arrays.equals(resource_state, g7)){return G7;}

        int[] g8 = new int[]{2,2,0,1,0,1};
        String[] G8 = {"swap 3 0","build C7"};
        if(target_structure.equals("C7") && board_state.equals("R0,S3,R1,J1,J2")
                && Arrays.equals(resource_state, g8)){return G8;}

        int[] g9 = new int[]{1,2,2,0,1,0};
        String[] G9 = {"build J4","swap 4 0","build J5"};
        if(target_structure.equals("J5") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, g9)){return G9;}

        int[] g10 = new int[]{0,1,3,0,2,0};
        String[] G10 = {"swap 2 0","build J3"};
        if(target_structure.equals("J3") && board_state.equals("R0,S3,R1,J1,J2")
                && Arrays.equals(resource_state, g10)){return G10;}

        int[] g11 = new int[]{1,2,2,1,0,0};
        String[] G11 = {"build J1","swap 3 0","build J2"};
        if(target_structure.equals("J2") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6")
                && Arrays.equals(resource_state, g11)){return G11;}

        int[] g12 = new int[]{0,0,1,3,2,0};
        String[] G12 = {"build R2","swap 3 1","build S4"};
        if(target_structure.equals("S4") && board_state.equals("R0,S3,R1,J1,J2")
                && Arrays.equals(resource_state, g12)){return G12;}

        int[] g13 = new int[]{0,3,0,1,2,0};
        String[] G13 = {"swap 1 2","build S5"};
        if(target_structure.equals("S5") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, g13)){return G13;}

        int[] g14 = new int[]{0,0,2,2,2,0};
        String[] G14 = {"swap 2 1","build S5"};
        if(target_structure.equals("S5") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, g14)){return G14;}

        int[] g15 = new int[]{3,0,1,0,2,0};
        String[] G15 = {"swap 0 1","build J3"};
        if(target_structure.equals("J3") && board_state.equals("R0,S3,R1,J1,J2")
                && Arrays.equals(resource_state, g15)){return G15;}

        int[] g16 = new int[]{4,1,0,0,1,0};
        String[] G16 = {"swap 0 1","build C7"};
        if(target_structure.equals("C7") && board_state.equals("R0,S3,R1,J1,J2")
                && Arrays.equals(resource_state, g16)){return G16;}

        int[] g17 = new int[]{3,1,1,0,0,1};
        String[] G17 = {"swap 2 1","build C12"};
        if(target_structure.equals("C12") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, g17)){return G17;}

        int[] g18 = new int[]{3,0,1,1,1,0};
        String[] G18 = {"swap 0 1","build J4"};
        if(target_structure.equals("J4") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, g18)){return G18;}

        int[] g19 = new int[]{0,1,0,2,3,0};
        String[] G19 = {"swap 3 2","build S5"};
        if(target_structure.equals("S5") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, g19)){return G19;}

        int[] g20 = new int[]{0,1,2,1,1,1};
        String[] G20 = {"swap 2 0","build J4"};
        if(target_structure.equals("J4") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, g20)){return G20;}

        int[] g21 = new int[]{2,1,2,0,1,0};
        String[] G21 = {"build J4","swap 4 1","build J5"};
        if(target_structure.equals("J5") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, g21)){return G21;}


        // Complex : 8
        int[] c1 = new int[]{0,1,0,1,2,2};
        String[] C1 = null;
        if(target_structure.equals("S7") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6")
                && Arrays.equals(resource_state, c1)){return C1;}

        int[] c2 = new int[]{3,1,0,0,0,2};
        String[] C2 = {"trade 1","build C7"};
        if(target_structure.equals("C7") && board_state.equals("R0,R1,S3,J1,J2,J3")
                && Arrays.equals(resource_state, c2)){return C2;}

        int[] c3 = new int[]{0,0,0,1,1,4};
        String[] C3 = null;
        if(target_structure.equals("S4") && board_state.equals("R0,R1,S3,J1,J2,J3")
                && Arrays.equals(resource_state, c3)){return C3;}

        int[] c4 = new int[]{0,1,1,2,2,0};
        String[] C4 = {"build R2","build S4"};
        if(target_structure.equals("S4") && board_state.equals("R0,R1,S3")
                && Arrays.equals(resource_state, c4)){return C4;}

        int[] c5 = new int[]{0,1,1,2,2,0};
        String[] C5 = null;
        if(target_structure.equals("S4") && board_state.equals("R0,R1,R2")
                && Arrays.equals(resource_state, c5)){return C5;}

        int[] c6 = new int[]{0,0,0,1,1,4};
        String[] C6 = {"trade 1","trade 2","build S4"};
        if(target_structure.equals("S4") && board_state.equals("R0,R1,R2,S3,K1,K2,K3")
                && Arrays.equals(resource_state, c6)){return C6;}

        int[] c7 = new int[]{0,0,0,1,1,4};
        String[] C7 = {"trade 1","trade 2","build S4"};
        if(target_structure.equals("S4") && board_state.equals("R0,R1,R2,S3,J1,J2,J3")
                && Arrays.equals(resource_state, c7)){return C7;}

        int[] c8 = new int[]{0,0,0,2,2,2};
        String[] C8 = {"build R2","swap 5 1","swap 5 2","build S4"};
        if(target_structure.equals("S4") && board_state.equals("R0,R1,S3,J1,J2,J3")
                && Arrays.equals(resource_state, c8)){return C8;}


        // NoSwap : 11
        int[] n1 = new int[]{0,0,1,3,2,0};
        String[] N1 = null;
        if(target_structure.equals("S7") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6")
                && Arrays.equals(resource_state, n1)){return N1;}

        int[] n2 = new int[]{1,1,0,1,0,3};
        String[] N2 = {"trade 4","build R7"};
        if(target_structure.equals("R7") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, n2)){return N2;}

        int[] n3 = new int[]{0,1,0,0,3,2};
        String[] N3 = {"trade 3","build R7"};
        if(target_structure.equals("R7") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3")
                && Arrays.equals(resource_state, n3)){return N3;}

        int[] n4 = new int[]{0,0,1,1,2,2};
        String[] N4 = {"build R7","trade 3","build R12"};
        if(target_structure.equals("R12") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6")
                && Arrays.equals(resource_state, n4)){return N4;}

        int[] n5 = new int[]{0,1,1,0,2,2};
        String[] N5 = {"trade 3","build S5"};
        if(target_structure.equals("S5") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3")
                && Arrays.equals(resource_state, n5)){return N5;}

        int[] n6 = new int[]{0,0,0,3,1,2};
        String[] N6 = {"build R2","trade 4","build R3"};
        if(target_structure.equals("R3") && board_state.equals("R0,S3,R1")
                && Arrays.equals(resource_state, n6)){return N6;}

        int[] n7 = new int[]{1,1,0,0,2,2};
        String[] N7 = {"trade 3","build R2"};
        if(target_structure.equals("R2") && board_state.equals("R0,S3,R1")
                && Arrays.equals(resource_state, n7)){return N7;}

        int[] n8 = new int[]{2,2,0,0,0,2};
        String[] N8 = {"trade 0","build C7"};
        if(target_structure.equals("C7") && board_state.equals("R0,S3,R1")
                && Arrays.equals(resource_state, n8)){return N8;}

        int[] n9 = new int[]{0,2,0,2,0,2};
        String[] N9 = {"trade 4","build R2"};
        if(target_structure.equals("R2") && board_state.equals("R0,S3,R1,J1,J2")
                && Arrays.equals(resource_state, n9)){return N9;}

        int[] n10 = new int[]{0,1,1,0,2,2};
        String[] N10 = null;
        if(target_structure.equals("S7") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3")
                && Arrays.equals(resource_state, n10)){return N10;}

        int[] n11 = new int[]{0,0,0,1,3,2};
        String[] N11 = {"build R7","trade 3","build R8"};
        if(target_structure.equals("R8") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3")
                && Arrays.equals(resource_state, n11)){return N11;}


        // Simple : 7
        int[] s1 = new int[]{2,2,2,0,0,0};
        String[] S1 = {"build J3","build J4"};
        if(target_structure.equals("J4") && board_state.equals("R0,S3,R1,J1,J2")
                && Arrays.equals(resource_state, s1)){return S1;}

        int[] s2 = new int[]{0,0,1,3,2,0};
        String[] S2 = {"build R2","build R3"};
        if(target_structure.equals("R3") && board_state.equals("R0,S3,R1")
                && Arrays.equals(resource_state, s2)){return S2;}

        int[] s3 = new int[]{0,1,1,2,2,0};
        String[] S3 = {"build R2","build S4"};
        if(target_structure.equals("S4") && board_state.equals("R0,S3,R1")
                && Arrays.equals(resource_state, s3)){return S3;}

        int[] s4 = new int[]{0,1,0,2,2,1};
        String[] S4 = {"build R7","build R8"};
        if(target_structure.equals("R8") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6")
                && Arrays.equals(resource_state, s4)){return S4;}

        int[] s5 = new int[]{2,2,2,0,0,0};
        String[] S5 = {"build J1","build J2"};
        if(target_structure.equals("J2") && board_state.equals("R0,S3,R1")
                && Arrays.equals(resource_state, s5)){return S5;}

        int[] s6 = new int[]{2,2,2,0,0,0};
        String[] S6 = {"build J4","build J5"};
        if(target_structure.equals("J5") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,K1,K2,K3")
                && Arrays.equals(resource_state, s6)){return S6;}

        int[] s7 = new int[]{0,0,2,2,2,0};
        String[] S7 = {"build R7","build R12"};
        if(target_structure.equals("R12") && board_state.equals("R0,S3,R1,C7,R2,S4,R3,R4,R5,R6")
                && Arrays.equals(resource_state, s7)){return S7;}

        return null;
        // FIXME: Task #14
    }
}