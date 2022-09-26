package comp1110.ass2;

import java.util.Random;

public class Dice {
    /**
     * roll a certain number of dices and return the results as int[].
     *
     * @return An array which contains all random results.
     */
    public static int[] rollDice(int numberOfDice){
        int[] result = new int[]{0,0,0,0,0,0};
        Random random = new Random();
        for(int i =0; i < numberOfDice; i++){
            int resource = random.nextInt(6);
            result[resource]++;
        }
        return result;
    }
}