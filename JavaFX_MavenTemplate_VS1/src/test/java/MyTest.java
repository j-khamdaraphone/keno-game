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


}
