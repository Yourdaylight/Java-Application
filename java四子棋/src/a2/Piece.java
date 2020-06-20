package a2;

public abstract class Piece {
    private String name;
    public static RedPiece redPiece = new RedPiece();
    public static BluePiece bluePiece = new BluePiece();
    public String toString() {
        return name;
    }
    public Piece(String name) {
        this.name = name;
    }

    public static final class RedPiece extends Piece {

        private RedPiece() {
            super("r");
        }
    }

    public static final class BluePiece extends Piece {

        private BluePiece() {
            super("B");
        }
    }
}
