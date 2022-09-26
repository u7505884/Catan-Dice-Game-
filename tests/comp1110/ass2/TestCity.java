package comp1110.ass2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
class TestCity {
    private void constructorTest(City city, int values) {
        assertNotNull(city,"Expected non-null object, but got null.");
        assertEquals(values, city.index, "Incorrect index");
    }
    private void constructorTest2(City city, int values, City lr){
        assertNotNull(city,"Expected non-null object, but got null.");
        assertEquals(values, city.index, "Incorrect index");
        assertEquals(lr, city.lastBuildableStructure, "Incorrect lastBuildableStructure");
    }


    static int[] r = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

    @Test
    public void testConstructor() {
        City city = new City(r[1]);
        constructorTest(city, r[1]);
        constructorTest(city, r[2]);
        constructorTest(city, r[3]);
    }

    @Test
    public void testConstructor2() {
        LinkedList<City> site = new LinkedList<>();
        for (int i=1; i < r.length; i++) {
            City city = new City(r[i-1]);
            site.add(city);}
        City city = new City(r[1],site.get(0));
        constructorTest2(city, r[1],site.get(0));
        constructorTest2(city, r[1],site.get(1));
    }

}