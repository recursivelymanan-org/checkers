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
        B,
        BK,
        R,
        RK,
        E;

        public static Color getFromRow(int row) {
            if (row >= 0 && row< 3) {
                return B;
            }
            else if (row > 4){
                return R;
            }
            else {
                return E;
            }
        }

        public String toString() {
            switch (this) {
                case B:
                    return "b";
                case BK:
                    return "B";
                case R:
                    return "r";
                case RK:
                    return "R";
                default:
                    return "_";

            }
        }
    }
}
