package comp1110.ass2;

public class Order {
    public static boolean rightOrder(String k){

        if(k.contains("J6")){
            if(k.contains("J5") || k.contains("K5"));
            else{return false;}
        }
        if(k.contains("J5")){
            if(k.contains("J4") || k.contains("K4"));
            else{return false;}
        }
        if(k.contains("J4")){
            if(k.contains("J3") || k.contains("K3"));
            else{return false;}
        }
        if(k.contains("J3")){
            if(k.contains("J2") || k.contains("K2"));
            else{return false;}
        }
        if(k.contains("J2")){
            if(k.contains("J1") || k.contains("K1"));
            else{return false;}
        }

        if(k.contains("S11")){
            if(k.contains("S9"));
            else{return false;}
        }
        if(k.contains("S9")){
            if(k.contains("S7"));
            else{return false;}
        }
        if(k.contains("S7")){
            if(k.contains("S5"));
            else{return false;}
        }
        if(k.contains("S5")){
            if(k.contains("S4"));
            else{return false;}
        }
        if(k.contains("S4")){
            if(k.contains("S3") && k.contains("R2") );
            else{return false;}
        }

        if(k.contains("C30")){
            if(k.contains("C20"));
            else{return false;}
        }
        if(k.contains("C20")){
            if(k.contains("C12"));
            else{return false;}
        }
        if(k.contains("C12")){
            if(k.contains("C7"));
            else{return false;}
        }

        if(k.contains("R15")){
            if(k.contains("R14"));
            else{return false;}
        }
        if(k.contains("R14")){
            if(k.contains("R13"));
            else{return false;}
        }
        if(k.contains("R13")){
            if(k.contains("R12"));
            else{return false;}
        }
        if(k.contains("R11")){
            if(k.contains("R10"));
            else{return false;}
        }
        if(k.contains("R10")){
            if(k.contains("R9"));
            else{return false;}
        }
        if(k.contains("R9")){
            if(k.contains("R8"));
            else{return false;}
        }
        if(k.contains("R8")){
            if(k.contains("R7"));
            else{return false;}
        }
        if(k.contains("R12")){
            if(k.contains("R7"));
            else{return false;}
        }
        if(k.contains("R7")){
            if(k.contains("R6"));
            else{return false;}
        }
        if(k.contains("R6")){
            if(k.contains("R5"));
            else{return false;}
        }
        if(k.contains("R5")){
            if(k.contains("R3"));
            else{return false;}
        }
        if(k.contains("R4")){
            if(k.contains("R3"));
            else{return false;}
        }
        if(k.contains("R3")){
            if(k.contains("R2"));
            else{return false;}
        }
        if(k.contains("R2")){
            if(k.contains("R0"));
            else{return false;}
        }
        if(k.contains("R1")){
            if(k.contains("R0"));
            else{return false;}
        }
    return true;
    }
}