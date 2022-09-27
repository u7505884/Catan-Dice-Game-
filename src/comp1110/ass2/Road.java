package comp1110.ass2;

public class Road extends BuildableStructures{

    public Road(int index) {//only used for the first road R0
        super.index = index;
        super.scores = 1;
        super.demandOfResources = new int[]{0,0,0,1,1,0};
        super.whetherHaveBuilt = false;
    }

    public Road(int index, Road lastRoad) {
        super.index = index;
        super.lastBuildableStructure = lastRoad;//lastnode
        super.scores = 1;
        super.demandOfResources = new int[]{0,0,0,1,1,0};
        super.whetherHaveBuilt = false;
    }

    @Override
    public String toString(){
        return "Road" + super.index;
    }


}
