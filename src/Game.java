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

    public void playGame(Game game) throws IOException {
        int turn = 0;
        Player currentPlayer;
        System.out.println("Game starting!");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        game.board.printBoard();
        System.out.println();

        while (!game.isGameOver(game.board)) {
            if (turn % 2 == 0) {
                currentPlayer = game.blackPlayer;
            }
            else {
                currentPlayer = game.redPlayer;
            }
            ArrayList<String> stringMoves = new ArrayList<>();
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
                        finish = true;
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
        game.playGame(game);

        }

    }

