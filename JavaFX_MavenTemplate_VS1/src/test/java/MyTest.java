import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.*;

class MyTest {

    private KenoGame game;

    @BeforeEach
    void setUp() {
        game = new KenoGame(4, 4); // 5 spots, 3 draws
    }

    @Test
    void zerohit(){
        assertEquals(0, game.calculateWin(0), "0 hits should return 0");
    }

    @Test
    void onehit(){
        assertEquals(1, game.calculateWin(2), "2 hits should return 1");
    }

    @Test
    void invlaidH(){
        assertEquals(0, game.calculateWin(-11), "-11 hits should return 0");
    }

    @Test
    void pickingValidNumbers() {
        Set<Integer> myNumbers = Set.of(1, 2, 3, 4);
        game.selectNumbers(myNumbers);
        assertEquals(myNumbers, game.getSelectedNumbers());
    }

    @Test
    void TooManyNumbers() {
        Set<Integer> myNumbers = Set.of(1, 2, 3, 4, 5, 6);
        assertThrows(IllegalArgumentException.class, () -> game.selectNumbers(myNumbers));
    }

    @Test
    void performDrawHits() {
        Set<Integer> myNumbers = Set.of(1, 2, 3, 4);
        game.selectNumbers(myNumbers);
        Set<Integer> draw = game.performDraw();
        assertNotNull(draw, "Draw should not be null");
        assertEquals(20, draw.size(), "Draw should contain exactly the 20 number ");

        int hits = game.countHits();
        assertTrue(hits >= 0 && hits <= 5, "Hits should be within valid range");
    }

    @Test
    void totalScoreAccumulates() {
        Set<Integer> myNumbers = Set.of(1, 2, 3, 4);
        game.selectNumbers(myNumbers);
        int before = game.getTotalScore();
        game.performDraw();
        assertTrue(game.getTotalScore() >= before, "Total score should increase or stay the same after draw");
    }

    void multipleDraws() {
        Set<Integer> myNumbers = Set.of(1, 2, 3, 4, 5);
        game.selectNumbers(myNumbers);
        int total = 0;
        for (int i = 0; i < 3; i++) { // perform all 3 draws
            game.performDraw();
            total += game.getTotalScore();
        }
        assertTrue(total >= 0, "Total score after multiple draws should be non-negative");
    }

    @Test
    void SpotDrawsCorrect() {
        KenoGame game = new KenoGame(10, 3);
        Set<Integer> nums = Set.of(1,2,3,4,5,6,7,8,9,10);
        game.selectNumbers(nums);
        Set<Integer> draw = game.performDraw();
        assertEquals(20, draw.size(), "Draw should have exactly 20 numbers");
    }

    @Test
    void singleSpotDrawCheck() {
        KenoGame game = new KenoGame(1, 3);
        Set<Integer> nums = Set.of(50);
        game.selectNumbers(nums);
        Set<Integer> draw = game.performDraw();
        assertEquals(20, draw.size(), "Draw should have exactly 20 numbers");
    }

    @Test
    void hitsWithinRange() {
        KenoGame game = new KenoGame(4, 4);
        Set<Integer> nums = Set.of(1, 2, 3, 4);
        game.selectNumbers(nums);
        game.performDraw();
        int hits = game.countHits();
        assertTrue(hits >= 0 && hits < 5, "Hits should always be within 0 to 4");
    }

    @Test
    void gettersReturnExpectedValues() {
        KenoGame customGame = new KenoGame(10, 8);
        assertEquals(10, customGame.getSpotsToPlay(), "SpotsToPlay getter should match constructor value");
        assertEquals(8, customGame.getDrawsToPlay(), "DrawsToPlay getter should match constructor value");
    }
    @Test
    void calculateHitAbove() {
        KenoGame eightSpot = new KenoGame(8, 1);
        assertEquals(0, eightSpot.calculateWin(15),
                "Hit count greater than spotsToPlay should return 0");
    }

    @Test
    void hitsWith8(){
        game = new KenoGame(8, 4);
        Set<Integer> nums = Set.of(7, 5, 38, 24, 34, 22,23, 25);
        game.selectNumbers(nums);
        game.performDraw();
        int hits = game.countHits();
        assertTrue(hits >= 0 && hits < 9, "Hits should always be within 0 to 4");
    }

    @Test
    void TooLessNumbers() {
        Set<Integer> myNumbers = Set.of(1, 2, 6);
        assertThrows(IllegalArgumentException.class, () -> game.selectNumbers(myNumbers));
    }



    @Test
    void drawNumberRangeCheck() {
        Set<Integer> nums = Set.of(1, 2, 3, 4);
        game.selectNumbers(nums);
        Set<Integer> draw = game.performDraw();

        assertEquals(20, draw.size(), "Draw must have 20 unique numbers");
        assertTrue(draw.stream().allMatch(n -> n >= 1 && n <= 80), "All numbers should be between 1 and 80");
    }

    @Test
    void tenSpotHighPayouts() {
        KenoGame tenSpot = new KenoGame(10, 1);
        assertEquals(450, tenSpot.calculateWin(8), "8 hits should return 450");
        assertEquals(4250, tenSpot.calculateWin(9), "9 hits should return 4250");
        assertEquals(100000, tenSpot.calculateWin(10), "10 hits should return 100000");
    }

    @Test
    void eightSpotPayouts() {
        KenoGame eightSpot = new KenoGame(8, 1);
        assertEquals(0, eightSpot.calculateWin(3), "3 hits should return 0");
        assertEquals(2, eightSpot.calculateWin(4), "4 hits should return 2");
        assertEquals(12, eightSpot.calculateWin(5), "5 hits should return 12");
        assertEquals(10000, eightSpot.calculateWin(8), "8 hits should return jackpot");
    }

    @Test
    void oneSpotPayout() {
        KenoGame oneSpot = new KenoGame(1, 1);
        assertEquals(2, oneSpot.calculateWin(1), "1 hit in a 1-spot game should pay 2");
        assertEquals(0, oneSpot.calculateWin(0), "0 hits should pay 0");
    }

    @Test
    void checkRandomPickWithDraws() {
        KenoGame customGame = new KenoGame(4, 8);
        customGame.performRandomPick();

        int before = customGame.getTotalScore();
        customGame.randomDraws();
        int after = customGame.getTotalScore();


        assertTrue(after >= before, "After all draws, total score should be >= before");
    }

    @Test
    void performDrawShouldGenerateDifferentResults() {
        Set<Integer> myNumbers = Set.of(1, 2, 3, 4);
        game.selectNumbers(myNumbers);

        Set<Integer> firstDraw = game.performDraw();
        Set<Integer> secondDraw = game.performDraw();

        // While randomness might rarely repeat, in almost all runs these should differ
        assertNotEquals(firstDraw, secondDraw,
                "Two consecutive draws should usually generate different results");
    }





    // JK
    @Test
    void testRandomDrawsMultipleRounds() {
        Set<Integer> picks = Set.of(1,2,3,4);
        game.selectNumbers(picks);
        int initialScore = game.getTotalScore();

        game.randomDraws();

        assertTrue(game.getTotalScore() >= initialScore,
                "Total score should not decrease after random draws");
    }

    @Test
    void randomPickGeneratesUniqueNumbers() {
        game.performRandomPick();
        Set<Integer> picks = game.getSelectedNumbers();
        //main check is size
        assertEquals(4, picks.size(), "Should generate exactly 5 unique numbers");
        assertTrue(picks.stream().allMatch(n -> n >= 1 && n <= 80), "All picks must be between 1 and 80");
    }

    @Test
    void drawWithoutSelectingNumbersThrowsError() {
        assertThrows(IllegalStateException.class, () -> game.performDraw(),
                "Drawing without selecting numbers should throw an exception");
    }

    @Test
    void payoutLogicForFourSpotGame() {
        KenoGame fourSpot = new KenoGame(4, 1);
        assertEquals(1, fourSpot.calculateWin(2), "2 hits should pay 1");
        assertEquals(5, fourSpot.calculateWin(3), "3 hits should pay 5");
        assertEquals(75, fourSpot.calculateWin(4), "4 hits should pay 75");
        assertEquals(0, fourSpot.calculateWin(1), "1 hit should pay 0");
    }


    @Test
    void updateScoreForTenSpotGameWithFourHits() {

        KenoGame tenSpot = new KenoGame(10, 1);
        Set<Integer> picks = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        tenSpot.selectNumbers(picks);

        Set<Integer> fakeDraw = Set.of(1, 2, 3, 4, 20, 21, 22, 23, 24, 25,
                26, 27, 28, 29, 30, 31, 32, 33, 34, 35);

        int before = tenSpot.getTotalScore();
        int win = tenSpot.calculateWin(4);

        int expectedWin = 0;
        int after = before + win;

        assertEquals(expectedWin, win, "10-spot game with 4 hits should return 0 payout");
        assertEquals(after, before + expectedWin, "Total score should remain unchanged for 0 payout");
    }
}
