package comp1110.ass2;

import java.util.Random;

public class Dice {
    /**
     * roll a certain number of dices and return the results as int[].
     *
     * @return An array which contains all random results.
     */
    public int[] rollDice(int numberOfDice){
        int[] result = new int[numberOfDice];
        Random random = new Random();
        for(int i =0; i < result.length; i++){
            result[i] = random.nextInt(1,7);
        }
        return result;
    }
}
