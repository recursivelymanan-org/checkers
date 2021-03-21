public class Game {
    Board board;
    Player black;
    Player red;

    public Game() {
        this.board = new Board();
        this.black = new Player(Player.Color.B);
        this.red = new Player(Player.Color.R);
    }

    public boolean isGameOver(Board board) {
        return getWinner(this.board) != null;
    }

    /*
    Returns Player who won if game is over, else returns null
     */
    public Player getWinner(Board board) {
        if (board.blackCount == 0) {
            return this.black;
        }
        else if (board.redCount == 0) {
            return this.red;
        }
        else {
            return null;
        }
    }

    public static void main(String[] args) {
        Game game = new Game();

    }

}
