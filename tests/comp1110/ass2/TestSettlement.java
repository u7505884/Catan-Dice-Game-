package comp1110.ass2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
class TestSettlement {

        private void constructorTest(Settlement settlement, int values) {
            assertNotNull(settlement,"Expected non-null object, but got null.");
            assertEquals(values, settlement.index, "Incorrect index");
        }
        private void constructorTest2(Settlement settlement, int values, Settlement se){
            assertNotNull(settlement,"Expected non-null object, but got null.");
            assertEquals(values, settlement.index, "Incorrect settlement");
            assertEquals(se, settlement.lastBuildableStructure, "Incorrect lastBuildableStructure");
        }
        private void getterLBSTest(Settlement settlement, Settlement  lastBuildableStructure) {
            settlement.lastBuildableStructure = lastBuildableStructure;
            assertEquals(lastBuildableStructure, settlement.getLastBuildableStructure(), "Getter method did not return value correctly.");
        }

        static int[] r = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

        @Test
        public void testConstructor() {
            Settlement settlement = new Settlement(r[1]);
            constructorTest(settlement, r[1]);

        }

        @Test
        public void testConstructor2() {
            LinkedList<Settlement> site = new LinkedList<>();
            for (int i=1; i < r.length; i++) {
                Settlement settlement = new Settlement(r[i-1]);
                site.add(settlement);}
            Settlement settlement = new Settlement(r[1], site.get(0));
            constructorTest2(settlement, r[1],site.get(0));
        }

    @Test
    public void testgetLastBuildableStructurer() {
        LinkedList<Settlement> site = new LinkedList<>();
        for (int i = 1; i < r.length; i++) {
            Settlement settlement = new Settlement(r[i - 1]);
            site.add(settlement);
        }
        Settlement k = new Settlement(r[1], site.get(0));
        constructorTest2(k, r[1], site.get(0));
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            Settlement c = site.get((int) (Math.random()));
            getterLBSTest(k, c);

        }
    }
    }
