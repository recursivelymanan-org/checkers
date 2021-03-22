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

    public void play(Game game) throws IOException {
        Player currentPlayer = blackPlayer;
        System.out.println("Game starting!\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        while (!game.isGameOver(game.board)) {
            game.board.printBoard();
            currentPlayer = nextPlayer(currentPlayer);
            ArrayList<Integer> intMoves = new ArrayList<>();

            System.out.print(currentPlayer.color + ": Please enter your move," +
                    " jump by jump,(type 'end' when you are done) ->  ");
            while (true) {
                String move = reader.readLine();
                if ("end".equals(move)) {
                    break;
                } else {
                    if (!validateUserEntry(move, intMoves)) {
                        System.out.print("Invalid move, try again -> ");
                        continue;
                    } else {
                        ArrayList<Square> squares = (ArrayList<Square>) convertToSquares(game, intMoves);
                        System.out.println("Moving piece...");
                        game.board.move(currentPlayer, squares);
                    }
                }
            }
        }
    }

    private Player nextPlayer(Player current) {
        if (current == blackPlayer) {
            return redPlayer;
        }
        return blackPlayer;
    }


    private List<Square> convertToSquares(Game game, ArrayList<Integer> intMoves) {
        ArrayList<Square> squares = new ArrayList<>();
        while (!intMoves.isEmpty()) {
            int row = intMoves.remove(0);
            int col = intMoves.remove(0);
            squares.add(game.board.board.get(row).get(col));
        }
        return squares;
    }

    public boolean validateUserEntry(String s, List<Integer> moves) {
        String[] coords = s.split(" ");
        if (coords.length != 2) { return false; }
        Integer row = parseInt(coords[0]);
        Integer col = parseInt(coords[1]);
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return false;
        }
        moves.add(row);
        moves.add(col);
        return true;
    }

    public static void main(String[] args) throws IOException {
        Game game = new Game();
        game.play(game);

        }

    }

