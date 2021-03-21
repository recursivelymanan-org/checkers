public class Square {
    Board board;
    Piece piece;
    int row;
    int col;
    boolean marked;

    public Square (Board board) {
        this.board = board;
    }

    public Square (Board board, Piece piece, int row, int col) {
        this.board = board;
        this.piece = piece;
        this.row = row;
        this.col = col;
    }

    public void mark() {
        this.marked = true;
    }

    public void unmark() {
        this.marked = false;
    }

    @Override
    public String toString() {
        return piece.toString();
    }
}
