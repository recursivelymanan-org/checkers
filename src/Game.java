import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Game {
    Board board;
    Player blackPlayer;
    Player redPlayer;

    private static final String START_GAME = "Starting game!";
    private static final String RULES = "Moves must be entered jump by jump. If performing a move" +
            " with multiple jumps, you should list out all steps to the move. Rows and column " +
            "coords should be separated by a space, and can be numbers 1-8 (inclusive). Ex:" +
            "\n3 2\n4 3\nend";

    public Game() {
        this.board = new Board();
        this.blackPlayer = new Player(Player.Color.B);
        this.redPlayer = new Player(Player.Color.R);
    }

    public boolean isGameOver(Board board) {
        return getWinner(this.board) != null;
    }

    /*
    Returns Player who won if game is over, else returns null
     */
    public Player getWinner(Board board) {
        if (board.blackCount == 0) {
            return this.blackPlayer;
        }
        else if (board.redCount == 0) {
            return this.redPlayer;
        }
        else {
            return null;
        }
    }

    public Player nextPlayer(Player player) {
        if (player.color == Player.Color.R) {
            return blackPlayer;
        }
        else {
            return redPlayer;
        }
    }

    public void playGame(Game game) throws IOException {
        int turn = 0;
        Player currentPlayer = redPlayer;
        System.out.println("Game starting!");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(RULES);

        game.board.printBoard();
        System.out.println();

        while (!game.isGameOver(game.board)) {
            currentPlayer = nextPlayer(currentPlayer);
            ArrayList<Integer> intMoves = new ArrayList<>();

            System.out.println("It is " + currentPlayer.color + "'s turn. Please enter your move," +
                    " jump by jump, and then type 'done' when you are done." +
                    ".");
            boolean finish = false;
            while (!finish) {
                String move = reader.readLine();
                if ("done".equals(move)) {
                    finish = true;
                } else {
                    if (!validateUserEntry(move, intMoves)) {
                        System.out.println("Invalid move, try again.");
                    }
                }
            }
            ArrayList<Square> squares = (ArrayList<Square>) convertToSquares(game, intMoves);
            System.out.println("Taking turn...");
            game.board.move(currentPlayer, squares);

            System.out.println();
            game.board.printBoard();
            System.out.println();
            turn++;
        }

    }

    private List<Square> convertToSquares(Game game, ArrayList<Integer> intMoves) {
        ArrayList<Square> squares = new ArrayList<>();
        while (!intMoves.isEmpty()) {
            int row = intMoves.remove(0) - 1;
            int col = intMoves.remove(0) - 1;
            squares.add(game.board.board.get(row).get(col));
        }
        return squares;
    }

    public boolean validateUserEntry(String s, List<Integer> moves) {
        String[] coords = s.split(" ");
        if (coords.length != 2) { return false; }
        Integer row = parseInt(coords[0]);
        Integer col = parseInt(coords[1]);
        if (row < 1 || row > 8 || col < 1 || col > 8) {
            return false;
        }
        moves.add(row);
        moves.add(col);
        return true;
    }


    public static void main(String[] args) throws IOException {
        Game game = new Game();
        game.playGame(game);

        }

    }

