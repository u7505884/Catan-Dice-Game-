package comp1110.ass2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 1000, unit = MILLISECONDS)

public class TestKnightDavid {
    private void directTest(String action, String board_state, boolean expected) {
        boolean out = Knight.Swap(action, board_state);
        assertEquals(expected, out, "Expected '" + expected + "' but got '" + out + "' for '" + action + "' on board '" + board_state);
    }

    @Test
    public void testWrongAction() {
        directTest("trade 3", "S3,R0,K1,K2,K3,K4,K5,J6", false);
        directTest("build C30", "S3,R0,K1,K2,K3,K4,K5,J6", false);
    }

    @Test
    public void testSwap() {
        directTest("swap 5 0", "S3,R0,J1,J2,J3,J4,K5,K6", true);
        directTest("swap 4 0", "S3,R0,K1,J2,J3,J4,K5,J6", true);
        directTest("swap 1 0", "S3,R0,K1,J2,J3,J4,K5,K6", false);
        directTest("swap 0 1", "S3,R0,J1,J2,J3,J4,K5,K6", true);
        directTest("swap 0 2", "S3,R0,J1,J2,J3,J4,K5,K6", true);
        directTest("swap 0 3", "S3,R0,J1,J2,J3,J4,K5,K6", true);
        directTest("swap 0 4", "S3,R0,J1,J2,J3,J4,K5,K6", false);
        directTest("swap 0 4", "S3,R0,J1,J2,J3,J4,K5,J6", true);
        directTest("swap 0 5", "S3,R0,J1,J2,J3,J4,J5,K6", false);
        directTest("swap 0 5", "S3,R0,K1,K2,K3,K4,K5,J6", true);
        directTest("swap 6 6", "S3,R0,K1,K2,K3,K4,K5,J6", false);
    }

    @Test
    public void testBeyoudFive() {
        directTest("swap 6 6", "S3,R0,K1,K2,K3,K4,K5,J6", false);
    }
}