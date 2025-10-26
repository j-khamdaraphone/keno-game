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
        game = new KenoGame(5, 3); // 5 spots, 3 draws
    }

    @Test
    void zerohit(){
        assertEquals(0, game.calculateWin(0), "0 hits should return 0");
    }

    @Test
    void onehit(){
        assertEquals(2, game.calculateWin(1), "1 hits should return 2");
    }

    @Test
    void invlaidH(){
        assertEquals(0, game.calculateWin(-11), "1 hits should return 2");
    }

    @Test
    void pickingValidNumbers() {
        Set<Integer> myNumbers = Set.of(1, 2, 3, 4, 5);
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
        Set<Integer> myNumbers = Set.of(1, 2, 3, 4, 5);
        game.selectNumbers(myNumbers);
        Set<Integer> draw = game.performDraw();
        assertNotNull(draw, "Draw should not be null");
        assertEquals(5, draw.size(), "Draw should contain exactly the number of spots played");

        int hits = game.countHits();
        assertTrue(hits >= 0 && hits <= 5, "Hits should be within valid range");
    }

    @Test
    void totalScoreAccumulates() {
        Set<Integer> myNumbers = Set.of(1, 2, 3, 4, 5);
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

    // JK
    @Test
    void testRandomDrawsMultipleRounds() {
        Set<Integer> picks = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
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
        assertEquals(5, picks.size(), "Should generate exactly 5 unique numbers");
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

    void gettersReturnCorrectValues() {
        assertEquals(5, game.getSpotsToPlay());
        assertEquals(3, game.getDrawsToPlay());
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
