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
        assertNull(board.knights.get(0),"Knights initialization has not be finished normally");
        assertNull(board.knights.get(7),"Knights initialization has not be finished normally");
        assertNull(board.knights.get(8),"Knights initialization has not be finished normally");
        assertNull(board.knights.get(9),"Knights initialization has not be finished normally");
        assertNull(board.knights.get(10),"Knights initialization has not be finished normally");
        assertNull(board.knights.get(11),"Knights initialization has not be finished normally");

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

    private void buildingConstraintTest(Board board, BuildableStructures buildableStructures, boolean bool){
        assertEquals(board.buildingConstraint(buildableStructures),bool, "Unexpected output, expected: "+bool+" but actual: "+ board.buildingConstraint(buildableStructures));
    }

    private void whetherCanBeBuiltTest(Board board, BuildableStructures buildableStructures, int[] resources, boolean bool){
        assertEquals(board.whetherCanBeBuilt(buildableStructures,resources),bool, "Unexpected output, expected: "+bool+" but actual: "+ board.buildingConstraint(buildableStructures));
    }

    private void buildTest(Board board, BuildableStructures buildableStructures){
        board.build(buildableStructures);
        assertTrue(buildableStructures.whetherHaveBuilt,"It should have been built");
    }

    private void whetherShouldFinishTest(Board board, boolean bool){
        assertEquals(board.whetherShouldFinish(),bool, bool? "It is time to finish the game at round "+board.round:"In correct time to finish the game at round "+board.round);
    }

    private void calculateFinalScoresTest(Board board, int sum){
        assertEquals(board.calculateFinalScores(), sum, "Incorrect sum of the final scores");
    }

    @Test
    public void testConstructorAndInitialization() {
        Board board = new Board();
        constructorTest(board);
    }

    @Test
    public void testBuildingConstraint(){
        Board board = new Board();
        buildingConstraintTest(board, board.knights.get(1), true);
        buildingConstraintTest(board, board.knights.get(2), false);
        buildingConstraintTest(board, board.knights.get(3), false);
        buildingConstraintTest(board, board.knights.get(4), false);
        buildingConstraintTest(board, board.knights.get(5), false);
        buildingConstraintTest(board, board.knights.get(6), false);

        board.knights.get(1).setWhetherBuild(true);
        buildingConstraintTest(board, board.knights.get(1), false);
        buildingConstraintTest(board, board.knights.get(2), true);
        buildingConstraintTest(board, board.knights.get(3), false);

        buildingConstraintTest(board, board.settlements.get(3), true);
        buildingConstraintTest(board, board.cities.get(7), false);
        buildingConstraintTest(board, board.roads.get(0), true);
        buildingConstraintTest(board, board.roads.get(1), false);

        board.roads.get(0).setWhetherBuild(true);
        buildingConstraintTest(board, board.roads.get(0), false);
        buildingConstraintTest(board, board.roads.get(1), true);
        buildingConstraintTest(board, board.roads.get(2), true);

        board.settlements.get(3).setWhetherBuild(true);
        board.roads.get(1).setWhetherBuild(true);
        buildingConstraintTest(board, board.roads.get(1), false);
        buildingConstraintTest(board, board.roads.get(2), true);
        buildingConstraintTest(board, board.settlements.get(3), false);
        buildingConstraintTest(board, board.settlements.get(4), false);
        buildingConstraintTest(board, board.cities.get(7), true);

        board.roads.get(2).setWhetherBuild(true);
        board.cities.get(7).setWhetherBuild(true);
        buildingConstraintTest(board, board.roads.get(2), false);
        buildingConstraintTest(board, board.roads.get(3), true);
        buildingConstraintTest(board, board.cities.get(7), false);
        buildingConstraintTest(board, board.cities.get(12), false);
        board.roads.get(3).setWhetherBuild(true);
        board.roads.get(4).setWhetherBuild(true);
        buildingConstraintTest(board, board.cities.get(12), true);



    }

    @Test
    public void testWhetherCanBeBuilt(){
        Board board = new Board();
        whetherCanBeBuiltTest(board, board.knights.get(1), new int[]{1,1,1,0,0,0},true);
        whetherCanBeBuiltTest(board, board.knights.get(2), new int[]{1,1,2,0,0,0},false);
        whetherCanBeBuiltTest(board, board.knights.get(3), new int[]{1,1,1,1,0,0},false);
        whetherCanBeBuiltTest(board, board.knights.get(4), new int[]{1,1,1,0,1,0},false);
        whetherCanBeBuiltTest(board, board.knights.get(5), new int[]{1,1,1,0,0,1},false);
        whetherCanBeBuiltTest(board, board.knights.get(6), new int[]{1,1,1,0,0,0},false);
        whetherCanBeBuiltTest(board, board.knights.get(1), new int[]{1,1,0,0,0,0},false);

        board.knights.get(1).setWhetherBuild(true);
        whetherCanBeBuiltTest(board, board.knights.get(1), new int[]{1,1,1,0,0,0},false);
        whetherCanBeBuiltTest(board, board.knights.get(2), new int[]{1,1,1,0,0,0},true);
        whetherCanBeBuiltTest(board, board.knights.get(3), new int[]{1,1,1,0,0,0},false);

        whetherCanBeBuiltTest(board, board.settlements.get(3), new int[]{0,1,3,1,1,0}, true);
        whetherCanBeBuiltTest(board, board.settlements.get(3), new int[]{0,1,0,1,1,1}, false);
        whetherCanBeBuiltTest(board, board.cities.get(7), new int[]{3,2,0,1,0,0}, false);
        whetherCanBeBuiltTest(board, board.roads.get(0), new int[]{0,0,0,1,2,0}, true);
        whetherCanBeBuiltTest(board, board.roads.get(1), new int[]{0,0,0,5,1,0}, false);

        board.roads.get(0).setWhetherBuild(true);
        whetherCanBeBuiltTest(board, board.roads.get(0), new int[]{0,0,0,1,0,0}, false);
        whetherCanBeBuiltTest(board, board.roads.get(1), new int[]{0,0,0,1,1,0}, true);
        whetherCanBeBuiltTest(board, board.roads.get(1), new int[]{0,0,0,0,0,0}, false);
        whetherCanBeBuiltTest(board, board.roads.get(2), new int[]{0,0,0,1,1,0}, true);

        board.settlements.get(3).setWhetherBuild(true);
        board.roads.get(1).setWhetherBuild(true);
        whetherCanBeBuiltTest(board, board.roads.get(1), new int[]{0,0,0,0,1,0}, false);
        whetherCanBeBuiltTest(board, board.roads.get(2), new int[]{0,0,0,2,1,0}, true);
        whetherCanBeBuiltTest(board, board.settlements.get(3),new int[]{0,1,1,1,1,0}, false);
        whetherCanBeBuiltTest(board, board.settlements.get(4),new int[]{0,1,1,1,0,0}, false);
        whetherCanBeBuiltTest(board, board.cities.get(7), new int[]{3,2,0,1,0,0},true);

        board.roads.get(2).setWhetherBuild(true);
        board.cities.get(7).setWhetherBuild(true);
        whetherCanBeBuiltTest(board, board.roads.get(2), new int[]{0,0,0,1,3,0},false);
        whetherCanBeBuiltTest(board, board.roads.get(3), new int[]{0,0,0,1,2,0}, true);
        whetherCanBeBuiltTest(board, board.cities.get(7), new int[]{3,0,0,1,0,0},false);
        whetherCanBeBuiltTest(board, board.cities.get(7), new int[]{3,2,0,1,0,0},false);
        whetherCanBeBuiltTest(board, board.cities.get(12), new int[]{3,2,0,1,0,0},false);
        board.roads.get(3).setWhetherBuild(true);
        board.roads.get(4).setWhetherBuild(true);
        whetherCanBeBuiltTest(board, board.cities.get(12),new int[]{3,2,0,1,0,0}, true);



    }

    @Test
    public void testBuild(){
        Board board = new Board();
        for(Road road:board.roads.values()){
            if(!road.whetherHaveBuilt){
                buildTest(board, road);
            }
        }
        for(Settlement settlement:board.settlements.values()){
            if(!settlement.whetherHaveBuilt){
                buildTest(board, settlement);
            }
        }
        for(City city:board.cities.values()){
            if(!city.whetherHaveBuilt){
                buildTest(board, city);
            }
        }
        for(Knight knight:board.knights.values()){
            if(!knight.whetherHaveBuilt){
                buildTest(board, knight);
            }
        }

    }

    @Test
    public void testWhetherShouldFinished(){
        Board board = new Board();
        for(int i=0; i < 15;i++){
            board.round = i;
            whetherShouldFinishTest(board, false);
        }
        for(int i=15; i < 30;i++){
            board.round = i;
            whetherShouldFinishTest(board, true);
        }
    }

    @Test
    public void testCalculateFinalScoresTest(){
        Board board = new Board();
        Random random = new Random();
        for(int i = 0; i<=100; i++){
            int random1 = random.nextInt();
            int random2 = random.nextInt();
            int random3 = random.nextInt();
            int random4 = random.nextInt();
            int random5 = random.nextInt();
            int random6 = random.nextInt();
            board.scoresRecorder = new int[]{random1, random2, random3, random4, random5,random6};
            calculateFinalScoresTest(board, Arrays.stream(board.scoresRecorder).sum());
        }
    }
}
