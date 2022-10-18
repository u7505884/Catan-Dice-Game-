package comp1110.ass2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)

/**
 * @author
 */
class TestKnight {
    private void constructorTest(Knight knight, int values) {
        assertNotNull(knight,"Expected non-null object, but got null.");
        assertEquals(values, knight.index, "Incorrect index");
    }
    private void constructorTest2(Knight knight, int values, Knight lr){
        assertNotNull(knight,"Expected non-null object, but got null.");
        assertEquals(values, knight.index, "Incorrect index");
        assertEquals(lr, knight.lastBuildableStructure, "Incorrect lastBuildableStructure");
    }
    private void isWhetherHaveSwapped(Knight knight, boolean w) {
        assertEquals(w, knight.whetherHaveSwapped, "Getter method did not return value correctly.");

    }
    private void setterTest(Knight k,  boolean w)
    {
        k.setWhetherHaveSwapped(w);
        assertEquals(w,k.whetherHaveSwapped, "Setter method did not set value correctly.");
    }

    private void getterLBSTest(Knight kight, Knight  lastBuildableStructure) {
        kight.lastBuildableStructure = lastBuildableStructure;
        assertEquals(lastBuildableStructure, kight.getLastBuildableStructure(), "Getter method did not return value correctly.");
    }

    static int[] r = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

    @Test
    public void testConstructor() {
        Knight knight = new Knight(r[1]);
        constructorTest(knight, r[1]);

    }

    @Test
    public void testConstructor2() {
        LinkedList<Knight> site = new LinkedList<>();
        for (int i=1; i < r.length; i++) {
            Knight knight = new Knight(r[i-1]);
            site.add(knight);}
        Knight knight = new Knight(r[1],site.get(0));
        constructorTest2(knight, r[1],site.get(0));

    }


    @Test
    void setgetWhetherHaveSwapped() {
        Knight k = new Knight(r[1]);
        constructorTest(k, r[1]);
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            boolean v = r.nextBoolean();
            setterTest(k,v);
            isWhetherHaveSwapped(k,v);
        }
    }
    @Test
    void setgetWhetherHaveSwapped2() {
        LinkedList<Knight> site = new LinkedList<>();
        for (int i=1; i < r.length; i++) {
            Knight knight = new Knight(r[i-1]);
            site.add(knight);}
        Knight k = new Knight(r[1],site.get(0));
        constructorTest2(k, r[1],site.get(0));
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            boolean v = r.nextBoolean();
            setterTest(k,v);
            isWhetherHaveSwapped(k,v);
        }
    }
    @Test
    public void testgetLastBuildableStructurer() {
        LinkedList<Knight> site = new LinkedList<>();
        for (int i = 1; i < r.length; i++) {
            Knight knight = new Knight(r[i - 1]);
            site.add(knight);
        }
        Knight k = new Knight(r[1], site.get(0));
        constructorTest2(k, r[1], site.get(0));
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            Knight c = site.get((int) (Math.random()));
            getterLBSTest(k, c);

        }
    }
}