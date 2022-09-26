package comp1110.ass2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
class TestSettlement {

        private void constructorTest(Settlement settlement, int values) {
            assertNotNull(settlement,"Expected non-null object, but got null.");
            assertEquals(values, settlement.index, "Incorrect settlement");
        }
        private void constructorTest2(Settlement settlement, int values, Settlement se){
            assertNotNull(settlement,"Expected non-null object, but got null.");
            assertEquals(values, settlement.index, "Incorrect settlement");
            assertEquals(se, settlement.lastBuildableStructure, "Incorrect lastBuildableStructure");
        }


        static int[] r = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

        @Test
        public void testConstructor() {
            Settlement settlement = new Settlement(r[1]);
            constructorTest(settlement, r[1]);
            constructorTest(settlement, r[2]);
            constructorTest(settlement, r[3]);
        }

        @Test
        public void testConstructor2() {
            LinkedList<Settlement> site = new LinkedList<>();
            Settlement settlement = new Settlement(r[1], site.get(0));
            constructorTest2(settlement, r[1],site.get(0));
            constructorTest2(settlement, r[1],site.get(1));
        }
    }
