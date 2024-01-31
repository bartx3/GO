import go.game.Colour;
import go.game.GameState;
import go.game.GameStateBuilder;
import go.game.Move;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitTest {
    @Test
    public void test() {
        assertTrue(true);
    }

    @Test
    public void test1() {
        Colour[][] board = new Colour[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; ++j) {
                board[i][j] = Colour.EMPTY;
            }
        }
        board[0][0] = Colour.BLACK;
        board[0][1] = Colour.WHITE;
        GameState gameState = new GameState(9, board);

        assertEquals(0, gameState.getPlayer2Captures());

        Move move = new Move(0, 0);

        GameStateBuilder gameStateBuilder = new GameStateBuilder(gameState);

        boolean verify = gameStateBuilder.performAndCheckMove(move, true);

        assertFalse(verify);
    }

    @Test
    public void test2() {
        Colour[][] board = new Colour[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; ++j) {
                board[i][j] = Colour.EMPTY;
            }
        }
        board[0][0] = Colour.BLACK;
        board[0][1] = Colour.WHITE;
        GameState gameState = new GameState(9, board);

        assertEquals(0, gameState.getPlayer2Captures());

        Move move = new Move(1, 0);

        GameStateBuilder gameStateBuilder = new GameStateBuilder(gameState);

        boolean verify = gameStateBuilder.performAndCheckMove(move, true);

        assertTrue(verify);
        assertEquals(1, gameStateBuilder.createGameState().getPlayer1Captures());
        assertEquals(Colour.EMPTY, gameStateBuilder.createGameState().getBoard()[0][0]);
    }
}
