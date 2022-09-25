package comp1110.ass2.selfTest;

import comp1110.ass2.Board;
import comp1110.ass2.BuildableStructures;
import comp1110.ass2.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)

public class TestCity {
    private void constructorTest(City city) {
        assertNotNull(city,"Expected non-null object, but got null.");
    }

    private void getterTest(City city){
        assertEquals(city.getWhetherHaveBuilt(),false,"getWhetherHaveBuilt() failed");
    }
    @Test
    public void testConstructor() {
        City city  = new City(1);
        constructorTest(city);
    }
    @Test
}
