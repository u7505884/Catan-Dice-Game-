package comp1110.ass2;

import comp1110.ass2.Dice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(value = 1000, unit = MILLISECONDS)

public class TestDice {

    private static void directTest(int numberOfDice) {
        int[] k = Dice.rollDice(numberOfDice);
        boolean y;

        if(k[0]<0 || k[1]<0 || k[2]<0 || k[3]<0 || k[4]<0 || k[5]<0) {y = false;}
        else if(k[0]+k[1]+k[2]+k[3]+k[4]+k[5] > 6) {y = false;}
        else if(k[0]+k[1]+k[2]+k[3]+k[4]+k[5]==numberOfDice) {y = true;}
        else {y = false;}

        assertEquals(true, y, Arrays.toString(k)+" is a wrong combination of elements");
        System.out.println("random "+numberOfDice+" dice: "+Arrays.toString(k));
    }

    @Test
    public void OneDice() {
        directTest(1);}

    @Test
    public void TwoDice() {
        directTest(2);
    }

    @Test
    public void ThreeDice() {
        directTest(3);
    }

    @Test
    public void FourDice() {
        directTest(4);
    }

    @Test
    public void FiveDice() {
        directTest(5);
    }

    @Test
    public void SixDice() {
        directTest(6);
    }
}