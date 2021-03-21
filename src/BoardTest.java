import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void testConstructor() {
        Board test = new Board();
        assertEquals(Piece.Color.E, test.board.get(0).get(0).piece.color);
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
        //assertTrue(testBoard.checkMove(new Player(Player.Color.R), moves));
    }

    @Test
    public void testPrintBoard() {
        Board testBoard = new Board();
        ArrayList<Square> moves = new ArrayList<>();
        moves.add(testBoard.board.get(5).get(0));
        moves.add(testBoard.board.get(4).get(1));
        testBoard.printBoard();
        System.out.println();
        testBoard.move(new Player(Player.Color.R), moves);
        testBoard.printBoard();
        moves.clear();
        moves.add(testBoard.board.get(2).get(3));
        moves.add(testBoard.board.get(3).get(2));
        testBoard.move(new Player(Player.Color.B), moves);
        System.out.println();
        testBoard.printBoard();
        moves.clear();
        moves.add(testBoard.board.get(4).get(1));
        moves.add(testBoard.board.get(2).get(3));
        testBoard.move(new Player(Player.Color.R), moves);
        System.out.println();
        testBoard.printBoard();
    }

}
