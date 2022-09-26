package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TestBuildableStructures {
    private void setterTest(BuildableStructures b, boolean whetherBuild)
    {
        b.setWhetherBuild(whetherBuild);
        assertEquals(whetherBuild,b.whetherHaveBuilt, "Setter method did not set value correctly.");
    }

    private void getterIndexTest(BuildableStructures b, int x) {
        b.index = x;
        assertEquals(x, b.getX(), "Getter method did not return value correctly.");
    }
    private void getterScoresTest(BuildableStructures b, int scores) {
        b.scores = scores;
        assertEquals(scores, b.getScores(), "Getter method did not return value correctly.");
    }
    private void getterDORTest(BuildableStructures b,  int[] demandOfResources) {
        b.demandOfResources = demandOfResources;
        assertEquals(demandOfResources, b.getDemandOfResources(), "Getter method did not return value correctly.");
    }
    private void getterLBSTest(BuildableStructures b, BuildableStructures lastBuildableStructure) {
        b.lastBuildableStructure = lastBuildableStructure;
        assertEquals(lastBuildableStructure, b.getLastBuildableStructure(), "Getter method did not return value correctly.");
    }
    private void getterWHBTest(BuildableStructures b,  boolean whetherHaveBuilt) {
        b.whetherHaveBuilt = whetherHaveBuilt;
        assertEquals(whetherHaveBuilt, b.getWhetherHaveBuilt(), "Getter method did not return value correctly.");

    }

    private void testResConstraint(BuildableStructures b, boolean w,int[] currentResources, boolean expected) {
        b.whetherHaveBuilt=w;
        assertNotNull(currentResources,"Expected non-null object, but got null.");
        assertNotEquals(expected, b.getWhetherHaveBuilt() , "Unexpected result for '" + currentResources+ "'");
        boolean out = b.resourcesConstraint(currentResources);
        assertEquals(expected, out, "Unexpected result for '" + currentResources+ "'");
    }


    @Test
    public void GetterIndex() {
        BuildableStructures b = new BuildableStructures();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int v = r.nextInt(20);
            getterIndexTest(b, v);
        }
    }

    @Test
    void getScores() {
        BuildableStructures b = new BuildableStructures();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int v = r.nextInt(100);
            getterScoresTest(b, v);
        }
    }

    @Test
    void setgetWhetherBuild() {
        BuildableStructures b = new BuildableStructures();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            boolean v = r.nextBoolean();
            setterTest(b, v);
            getterWHBTest(b,v);
        }
    }

    @Test
    void getDemandOfResources() {
        BuildableStructures b = new BuildableStructures();
        Random r = new Random();
        int[] arr=new int[r.nextInt(20)];
        for(int i=0;i<arr.length;i++)
            arr[i]=(int)(Math.random());
        for (int i = 0; i < 10; i++) {
            int v = r.nextInt(100);
            getterDORTest(b, arr);
        }
    }

    @Test
    void getLastBuildableStructure() {
        GetterIndex() ;
        getScores();
        setgetWhetherBuild();
        getDemandOfResources();
        }


    @Test
    void resourcesConstraint() {
        BuildableStructures b = new BuildableStructures();

        int[] a=new int[]{1,1,1,0,0,0};
        int[] c=new int[]{1,1,1,-2,3,0};
        int[] d=new int[]{1,3,1,2,5,2};
        int[] e=new int[]{3,2,0,-1,1,1};
        testResConstraint(b,true,a,true);
        testResConstraint(b,true,c,false);
        testResConstraint(b,false, d,true);
        testResConstraint(b,false,e,false);
    }
}