package comp1110.ass2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.LinkedList;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
class TestRoad {
    private void constructorTest(Road road, int values) {
        assertNotNull(road,"Expected non-null object, but got null.");
        assertEquals(values, road.index, "Incorrect index");
    }
    private void constructorTest2(Road road, int values, Road lr){
        assertNotNull(road,"Expected non-null object, but got null.");
        assertEquals(values, road.index, "Incorrect index");
        assertEquals(lr, road.lastBuildableStructure, "Incorrect lastBuildableStructure");
    }


    static int[] r = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

    @Test
    public void testConstructor() {
        Road road = new Road(r[1]);
        constructorTest(road, r[1]);
        constructorTest(road, r[2]);
        constructorTest(road, r[3]);
    }

    @Test
    public void testConstructor2() {
        LinkedList<Road> site = new LinkedList<>();
        for (int i=1; i < r.length; i++) {
            Road road = new Road(r[i-1]);
            site.add(road);}
        Road road = new Road(r[1],site.get(0));
        constructorTest2(road, r[1],site.get(0));
        constructorTest2(road, r[1],site.get(1));
    }
}