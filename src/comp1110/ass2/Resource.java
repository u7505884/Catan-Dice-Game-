package comp1110.ass2;

import java.util.HashMap;

/**
 * @author Ta-Wei Chen (u7546144)
 */
public enum Resource {
    Ore("Ore", 1),
    Grain("Grain", 2),
    Wool("Wool", 3),
    Timber("Timber", 4),
    Brick("Brick", 5),
    Gold("Gold", 6);

    private String name;
    private int index;

    Resource(String name, int index){
        this.name = name;
        this.index = index;
    }
    public String getName() {
        return name;
    }
    public int getIndex(){
        return this.index;
    }
    public Knight getKnight(Board board){
        return board.knights.get(this.getIndex());
    }
}
