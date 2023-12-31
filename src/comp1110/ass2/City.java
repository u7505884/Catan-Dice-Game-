package comp1110.ass2;

/**
 * @author Ziling Ruan(u7505884)
 */
public class City extends BuildableStructures{
    public City(int index) {//only used for the first city C7
        super.index = index;
        super.scores = index;
        super.demandOfResources = new int[]{3,2,0,0,0,0};
        super.whetherHaveBuilt = false;
    }

    public City(int index, City lastCity) {
        super.index = index;
        super.scores = index;
        super.demandOfResources = new int[]{3,2,0,0,0,0};
        super.lastBuildableStructure = lastCity;//lastnode
        super.whetherHaveBuilt = false;
    }

    @Override
    public String toString(){
        return "C" + super.index;
    }
}
