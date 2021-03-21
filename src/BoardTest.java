import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void testConstructor() {
        Board test = new Board();
        assertEquals(Piece.Color.EM, test.board.get(0).get(0).piece.color);
    }

    @Test
    public void arrayList() {
        Board testBoard = new Board();
        assertFalse(testBoard.board.get(0).get(0).marked);
        testBoard.board.get(0).get(0).mark();
        Square mark = testBoard.board.get(0).get(0);
        mark.mark();
        assertTrue(testBoard.board.get(0).get(0).marked);

    }

    @Test
    public void testCheckMove() {
        Board testBoard = new Board();
        ArrayList<Square> moves = new ArrayList<>();
        moves.add(testBoard.board.get(5).get(0));
        moves.add(testBoard.board.get(4).get(1));
        assertTrue(testBoard.checkMove(new Player(Player.Color.R), moves));
    }
}
