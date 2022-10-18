package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author
 */
class TestBuildableStructures {
    private void setterTest(BuildableStructures b, boolean whetherBuild) {
        b.setWhetherBuild(whetherBuild);
        assertEquals(whetherBuild, b.whetherHaveBuilt, "Setter method did not set value correctly.");
    }

    private void getterIndexTest(BuildableStructures b, int x) {
        b.index = x;
        assertEquals(x, b.getX(), "Getter method did not return value correctly.");
    }

    private void getterScoresTest(BuildableStructures b, int scores) {
        b.scores = scores;
        assertEquals(scores, b.getScores(), "Getter method did not return value correctly.");
    }

    private void getterDORTest(BuildableStructures b, int[] de) {
        b.demandOfResources = de;
        assertEquals(de, b.getDemandOfResources(), "Getter method did not return value correctly.");
    }

    private void getterWHBTest(BuildableStructures b, boolean whetherHaveBuilt) {
        b.whetherHaveBuilt = whetherHaveBuilt;
        assertEquals(whetherHaveBuilt, b.getWhetherHaveBuilt(), "Getter method did not return value correctly.");
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
            getterWHBTest(b, v);
        }
    }

    @Test
    void getDemandOfResources() {
        BuildableStructures b = new BuildableStructures();
        Random r = new Random();
        int[] arr = new int[6];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(20);
        }
        for (int i = 0; i < 10; i++) {
            getterDORTest(b, arr);
        }
    }


}