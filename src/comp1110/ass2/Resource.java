package comp1110.ass2;

import java.util.HashMap;

public enum Resource {
    Ore(1),
    Grain(2),
    Wool(3),
    Timber(4),
    Brick(5),
    Gold(6);

    private int index;

    Resource(int index){
        this.index = index;
    }

    public int getIndex(){
        return this.index;
    }
    public Knight getKnight(Board board){
        return board.knights.get(this.getIndex());
    }
}
