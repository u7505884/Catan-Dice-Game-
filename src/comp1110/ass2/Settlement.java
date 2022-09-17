package comp1110.ass2;

public class Settlement extends BuildableStructures{

    public Settlement(int index) {//only used for S3
        super.index = index;
        super.scores = index;
        super.demandOfResources = new int[] {0,1,1,1,1,0};
        super.whetherHaveBuilt = false;
    }
    public Settlement(int index, Settlement lastSettlement) {
        super.index = index;
        super.scores = index;
        super.demandOfResources = new int[] {0,1,1,1,1,0};
        super.lastBuildableStructure = lastSettlement;
        super.whetherHaveBuilt = false;
    }

    @Override
    public String toString(){
        return "Settlement" + super.index;
    }
}
