import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Board {
    public static final String INVALID_MOVE = "Invalid move, please enter a valid move";

    List<List<Square>> board;
    int blackCount;
    int redCount;

    /*
    No-arg constructor initializes a Board object with a matrix system represented by an ArrayList
     of ArrayLists. Places black and red pieces on the board according to the starting
     arrangement of a game of checkers.
     */
    public Board() {
        this.board = new ArrayList<>();
        IntStream.range(0, 8).forEach(i -> {
            ArrayList<Square> squares = new ArrayList<>();
            final int[] startIndex = new int[1];
            Piece.Color color;

            if (i % 2 == 0 && i != 4) {
                startIndex[0] = 1;
            }
            else if (i != 3) {
                startIndex[0] = 0;
            }
            else {
                startIndex[0] = -1;
            }

            color = Piece.Color.getFromRow(i);

            IntStream.range(0,8).forEach(j -> {
                Square square;
                if (j == startIndex[0]) {
                    square = new Square(this, new Piece(color), i, j);
                    startIndex[0] += 2;
                }
                else {
                    square = new Square(this, new Piece(Piece.Color.E), i, j);
                }
                squares.add(square);
            });
            board.add(squares);
        });
        this.blackCount = 12;
        this.redCount = 12;
    }

    /*
    Takes in a list of Squares representing all jumps in one move, and checks if this move is
    legal before performing the move.

    @param Player player : Player making the move
    @param List<Square> moves : List of squares that the piece will jump on to make a full move.
                                Validation for this list takes place in the Game class.
     */
    public void move(Player player, List<Square> moves){
        Square start = moves.get(0);
        Square end = moves.get(moves.size() - 1);
        boolean[] checks = checkMove(player,moves);
        if (!checks[0]) {
            System.out.println(INVALID_MOVE);
            return;
        }
        else {
            for (List<Square> row : this.board) {
                for (Square square : row) {
                    if (square.marked) {
                        if (player.color == Player.Color.B) {
                            redCount--;
                        }
                        else {
                            blackCount--;
                        }
                        square.piece = new Piece(Piece.Color.E);
                    }
                }
            }

            this.board.get(start.row).get(start.col).piece = new Piece(Piece.Color.E);
            if (player.color == Player.Color.B) {
                if (checks[1]) {
                    this.board.get(end.row).get(end.col).piece = new Piece(Piece.Color.BK);
                }
                else {
                    this.board.get(end.row).get(end.col).piece = new Piece(Piece.Color.B);
                }
            }
            else {
                if (checks[1]) {
                    this.board.get(end.row).get(end.col).piece = new Piece(Piece.Color.RK);
                }
                this.board.get(end.row).get(end.col).piece = new Piece(Piece.Color.R);
            }
        }

    }

    /*
    Takes a Player and a list of squares and checks that the overall move is valid. "Overall
    move" refers to the movement of the checkers piece from the first Square in the list to the
    last Square. Squares in between represent "jumps".
     */
    public boolean[] checkMove(Player player, List<Square> moves) {
        boolean[] values = new boolean[2];
        boolean moveFailed = false; // Tracks if the move failed
        boolean startedKing = moves.get(0).piece.isKing;
        boolean turnedKing = false; // Tracks if piece turned into a king mid-turn

        if (moves.get(0).piece.color == Piece.Color.E) {
            return values;
        }

        // First loop checks until list is empty OR the first/last row of the board is visited
        while (moves.size() > 1 && !moveFailed) {
            if (!startedKing && moves.get(0).row == 7 && player.color == Player.Color.B) {
                break;
            } else if (!startedKing && moves.get(0).row == 0 && player.color == Player.Color.R) {
                break;
            }
            Square source = moves.remove(0);
            Square target = moves.get(0);
            Square captured;
            int capturedRow;
            int capturedCol;

            if (player.color == Player.Color.B) {
                if (startedKing) {
                    if (checkMoveBlackKing(source, target)) {
                        capturedRow = average(source.row, target.row);
                        capturedCol = average(source.col, target.col);
                        captured = this.board.get(capturedRow).get(capturedCol);
                        captured.mark();
                        continue;
                    }
                    moveFailed = true;
                } else {
                    if (checkMoveBlack(source, target)) {
                        capturedRow = average(source.row, target.row);
                        capturedCol = average(source.col, target.col);
                        captured = this.board.get(capturedRow).get(capturedCol);
                        captured.mark();
                        continue;
                    }
                    moveFailed = true;
                }
            }

            else if (player.color == Player.Color.R) {
                if (startedKing) {
                    if (checkMoveRedKing(source, target)) {
                        capturedRow = average(source.row, target.row);
                        capturedCol = average(source.col, target.col);
                        captured = this.board.get(capturedRow).get(capturedCol);
                        captured.mark();
                        continue;
                    }
                    moveFailed = true;
                } else {
                    if (checkMoveRed(source, target)) {
                        capturedRow = average(source.row, target.row);
                        capturedCol = average(source.col, target.col);
                        captured = this.board.get(capturedRow).get(capturedCol);
                        captured.mark();
                        continue;
                    }
                    moveFailed = true;
                }
            }
        }

        // This loop continues checking the moves after a piece has been upgraded to a king piece
        // mid-turn
        while (moves.size() > 1 && !moveFailed) {
            Square source = moves.remove(0);
            Square target = moves.get(0);
            Square captured;
            int capturedRow;
            int capturedCol;

            if (player.color == Player.Color.B) {
                    if (checkMoveBlackKing(source, target)) {
                        capturedRow = average(source.row, target.row);
                        capturedCol = average(source.col, target.col);
                        captured = this.board.get(capturedRow).get(capturedCol);
                        captured.mark();
                        continue;
                    }
                    moveFailed = true;
            }
            else if (player.color == Player.Color.R) {
                if (checkMoveRedKing(source, target)) {
                    capturedRow = average(source.row, target.row);
                    capturedCol = average(source.col, target.col);
                    captured = this.board.get(capturedRow).get(capturedCol);
                    captured.mark();
                    continue;
                }
                moveFailed = true;
            }
            turnedKing = true;
        }

        // If a move was failed, we need to unmark all marked squares, because this move will not
        // occur
        if (moveFailed) {
            this.unmark();
            return values;
        }


        if (turnedKing) {
            values[1] = true;
        }

        values[0] = true;
        return values;
    }



    private boolean checkMoveBlack(Square source, Square target) {
        Square captured;
        int capturedRow;
        int capturedCol;
        if (target.row < source.row) {
            return false;
        } else if (target.row == source.row + 1) {
            return target.piece.color == Piece.Color.E;
        } else if (target.row == source.row + 2) {
            capturedRow = average(source.row, target.row);
            capturedCol = average(source.col, target.col);
            captured = this.board.get(capturedRow).get(capturedCol);
            return captured.piece.color == Piece.Color.R;
        }
        return false;
    }

    private boolean checkMoveRed(Square source, Square target) {
        Square captured;
        int capturedRow;
        int capturedCol;
        if (target.row > source.row) {
            return false;
        }
        else if (target.row == source.row - 1) {
            return target.piece.color == Piece.Color.E;
        }
        else if (target.row == source.row - 2) {
            capturedRow = average(source.row, target.row);
            capturedCol = average(source.col, target.col);
            captured = this.board.get(capturedRow).get(capturedCol);

            return captured.piece.color == Piece.Color.B;
        }
        return false;
    }

    private boolean checkMoveBlackKing(Square source, Square target) {
        Square captured;
        int capturedRow;
        int capturedCol;
        if (target.row == source.row) {
            return false;
        }
        else if (target.row == source.row + 1 || target.row == source.row - 1) {
            return target.piece.color == Piece.Color.E;
        }
        else if (target.row == source.row + 2 || target.row == source.row - 2) {
            capturedRow = average(source.row, target.row);
            capturedCol = average(source.col, target.col);
            captured = this.board.get(capturedRow).get(capturedCol);

            return captured.piece.color == Piece.Color.R;
        }
        return false;
    }

    private boolean checkMoveRedKing(Square source, Square target) {
        return false;
    }

    public void unmark() {
        for (List<Square> row : this.board) {
            for (Square square : row) {
                square.unmark();
            }
        }
    }
    /*
    Takes in a valid single jump pair of source/target squares and return the square in between
    them.
     */
    public int average(int i, int j) {
        return (i + j) / 2;
    }

    public void printBoard() {
        IntStream.range(0, 8).forEach(i -> {
            System.out.println(this.board.get(i));
        });
    }




}
