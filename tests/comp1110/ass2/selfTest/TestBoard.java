package comp1110.ass2;

import comp1110.ass2.Board;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)

public class TestBoard {
    private void constructorTest(Board board) {
        assertNotNull(board,"Expected non-null object, but got null.");
        for(int iRoad =0; iRoad <= 15; iRoad++){
            assertNotNull(board.roads.get(iRoad),"Roads initialization has not be finished normally");
        }
        assertNull(board.roads.get(16),"Roads initialization has not be finished normally");
        assertNull(board.roads.get(17),"Roads initialization has not be finished normally");
        assertNull(board.roads.get(18),"Roads initialization has not be finished normally");
        assertNull(board.roads.get(19),"Roads initialization has not be finished normally");
        assertNull(board.roads.get(20),"Roads initialization has not be finished normally");
        assertNull(board.roads.get(21),"Roads initialization has not be finished normally");
        assertNull(board.roads.get(22),"Roads initialization has not be finished normally");

        for(int iKnight =1; iKnight <= 6; iKnight++){
            assertNotNull(board.knights.get(iKnight),"Knights initialization has not be finished normally");
        }
        assertNotNull(board.knights.get(0),"Knights initialization has not be finished normally");
        assertNotNull(board.knights.get(7),"Knights initialization has not be finished normally");
        assertNotNull(board.knights.get(8),"Knights initialization has not be finished normally");
        assertNotNull(board.knights.get(9),"Knights initialization has not be finished normally");
        assertNotNull(board.knights.get(10),"Knights initialization has not be finished normally");
        assertNotNull(board.knights.get(11),"Knights initialization has not be finished normally");

        assertNotNull(board.settlements.get(3),"Settlements initialization has not be finished normally");
        assertNotNull(board.settlements.get(4),"Settlements initialization has not be finished normally");
        assertNotNull(board.settlements.get(5),"Settlements initialization has not be finished normally");
        assertNotNull(board.settlements.get(7),"Settlements initialization has not be finished normally");
        assertNotNull(board.settlements.get(9),"Settlements initialization has not be finished normally");
        assertNotNull(board.settlements.get(11),"Settlements initialization has not be finished normally");
        assertNull(board.settlements.get(6),"Settlements initialization has not be finished normally");
        assertNull(board.settlements.get(8),"Settlements initialization has not be finished normally");
        assertNull(board.settlements.get(10),"Settlements initialization has not be finished normally");
        assertNull(board.settlements.get(12),"Settlements initialization has not be finished normally");
        assertNull(board.settlements.get(13),"Settlements initialization has not be finished normally");
        assertNull(board.settlements.get(0),"Settlements initialization has not be finished normally");

        assertNotNull(board.cities.get(7),"Cities initialization has not be finished normally");
        assertNotNull(board.cities.get(12),"Cities initialization has not be finished normally");
        assertNotNull(board.cities.get(20),"Cities initialization has not be finished normally");
        assertNotNull(board.cities.get(30),"Cities initialization has not be finished normally");
        assertNull(board.cities.get(0),"Cities initialization has not be finished normally");
        assertNull(board.cities.get(1),"Cities initialization has not be finished normally");
        assertNull(board.cities.get(6),"Cities initialization has not be finished normally");
        assertNull(board.cities.get(10),"Cities initialization has not be finished normally");
        assertNull(board.cities.get(19),"Cities initialization has not be finished normally");
        assertNull(board.cities.get(31),"Cities initialization has not be finished normally");

    }

    private void buildingConstraintTest(){

    }
    @Test
    public void testConstructor() {
        Board board = new Board();
        constructorTest(board);
    }

    @Test
    public  void testBuildingConstraint(){
        Board board = new Board();
    }

    private void setterTest(BuildableStructures buildableStructures, int value) {
        buildableStructures.setWhetherBuild(value);
        assertEquals(value, o.birthyear, "Setter method did not set value correctly.");
    }

    private void getterTest(Q2Artist o, int value) {
        o.birthyear = value;
        assertEquals(value, o.getBirthyear(), "Getter method did not return value correctly.");
    }

    private void functionTest(Q2Artist o, int[] val) {
        int sum = 0;
        for (int i = 0; i < val.length; i++) {
            o.sale(val[i]);
            sum += val[i];
        }
        int expected = val.length == 0 ? 0 : sum/val.length;
        assertEquals(expected, o.averageValueOfWorks(), "Incorrect output for averageValueOfWorks() given: "+ Arrays.toString(val));
    }

    private void toStringTest(Q2Artist o, String expected) {
        assertEquals(expected, o.toString(), "toString() method gave unexpected value");
    }

    public void factoryTest(String[] v1, int[] v2) {
        Q2Artist[] out = Q2Artist.makeArtists(v1, v2);
        if (v1 == null || v2 == null) {
            assertNull(out, "Expected null for inputs '"+v1+"' and '"+v2+"'");
        } else if (v1.length == 0 || v2.length == 0 || v1.length != v2.length) {
            assertNull(out,"Expected to return null for inputs '" + Arrays.toString(v1) + "', '" + Arrays.toString(v2) + "', but did not.");
        } else {
            assertNotNull(out, "Expected to return non-null value for inputs '" + Arrays.toString(v1) + "', '" + Arrays.toString(v2) + "', but did not.");
            for (int i = 0; i < v1.length; i++) {
                assertNotNull(out[i], "Expected non-null value for entry ["+i+"] and inputs '" + Arrays.toString(v1) + "', '" + Arrays.toString(v2) + "', but got null entry.");
                assertEquals(v1[i], out[i].name, "Did not get expected name for entry "+i+", given inputs '" + Arrays.toString(v1) + "', '" + Arrays.toString(v2));
                assertEquals(v2[i], out[i].birthyear, "Did not get expected birth year for entry "+i+", given inputs '" + Arrays.toString(v1) + "', '" + Arrays.toString(v2));
            }
        }
    }

    static String[] names = {"van Gogh", "Manet", "Cezanne", "Sargent", "Picasso", "Monet", "Klimt"};



    @Test
    public void testGetterSetter() {
        Q2Artist o = new Q2Artist(names[1], 1832);
        constructorTest(o, names[1], 1832);
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int v = 1800+r.nextInt(100);
            setterTest(o, v);
            getterTest(o, v);
        }
    }

    @Test
    public void testFunction() {
        Q2Artist o = new Q2Artist(names[1], 1832);
        constructorTest(o, names[1], 1832);
        Random r = new Random();
        int[] z = {};
        functionTest(o, z);
        for (int i = 0; i < 10; i++) {
            o = new Q2Artist(names[1], 1832);
            constructorTest(o, names[1], 1832);
            int l = r.nextInt(12);
            z = new int[l];
            for (int j = 0; j < l; j++)
                z[j] = 1800+r.nextInt(100);
            functionTest(o, z);
        }
    }

    @Test
    public void testToString() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            String n = names[r.nextInt(names.length)];
            int v = 10+r.nextInt(60);
            Q2Artist o = new Q2Artist(n, v);
            constructorTest(o,n, v);
            o.sale(1000*r.nextInt(1000));
            o.sale(1000+r.nextInt(1000));
            toStringTest(o, o.name+" was born in "+o.birthyear+" and their works have sold for $"+o.averageValueOfWorks()+" on average");
        }
    }

    private String[] newRandString(int size) {
        Random r = new Random();
        String[] rtn = new String[size];
        for (int i = 0; i < size; i++) {
            rtn[i] = names[r.nextInt(names.length)];
        }
        return rtn;
    }
    private int[] newRandInt(int size) {
        Random r = new Random();
        int[] rtn = new int [size];
        for (int i = 0; i < size; i++) {
            rtn[i] = 1+r.nextInt(20);
        }
        return rtn;
    }

    @Test
    public void testFactory() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int l1 = 1+r.nextInt(7);
            String[] n = newRandString(l1);
            int[] v;
            int c = r.nextInt(6);
            if (c == 0) {
                v = null;
            } else if (c == 1) {
                v= newRandInt(n.length + 1);
            } else
                v = newRandInt(n.length);
            factoryTest(n, v);
        }
    }
}
