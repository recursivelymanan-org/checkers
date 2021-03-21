public class Piece {
    Color color;
    boolean isKing = false;

    public Piece(Color color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setKing() {
        this.isKing = true;
    }

    @Override
    public String toString() {
        return color.toString();
    }

    enum Color {
        BL,
        BK,
        RE,
        RK,
        EM;

        public static Color getFromRow(int row) {
            if (row >= 0 && row< 3) {
                return BL;
            }
            else if (row > 4){
                return RE;
            }
            else {
                return EM;
            }
        }
    }
}
