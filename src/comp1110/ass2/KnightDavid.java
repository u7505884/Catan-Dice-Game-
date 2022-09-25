package comp1110.ass2;

import java.util.ArrayList;
import java.util.Arrays;

public class KnightDavid {
    public static boolean KnightDavidSwap(String action, String board_state) {
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
}