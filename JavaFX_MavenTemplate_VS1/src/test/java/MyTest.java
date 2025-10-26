import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @Test
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
    void maxSpotDraw() {
        KenoGame game = new KenoGame(10, 3);
        Set<Integer> nums = Set.of(1,2,3,4,5,6,7,8,9,10);
        game.selectNumbers(nums);
        Set<Integer> draw = game.performDraw();
        assertEquals(10, draw.size(), "Draw should have exactly 10 numbers");
    }

    @Test
    void singleSpotDraw() {
        KenoGame game = new KenoGame(1, 3);
        Set<Integer> nums = Set.of(50);
        game.selectNumbers(nums);
        Set<Integer> draw = game.performDraw();
        assertEquals(1, draw.size(), "Draw should have exactly 1 number");
    }

    @Test
    void hitsWithinRange() {
        KenoGame game = new KenoGame(5, 3);
        Set<Integer> nums = Set.of(1, 2, 3, 4, 5);
        game.selectNumbers(nums);
        game.performDraw();
        int hits = game.countHits();
        assertTrue(hits >= 0 && hits <= 5, "Hits should always be within 0 to 5");
    }


}
