public class Player {
    String name;
    Player.Color color;

    public Player(Player.Color color) {

        this.color = color;
    }

    public Player(Player.Color color, String name) {
        this.color = color;
        this.name = name;
    }

    enum Color {
        B,
        R;

    }
}
