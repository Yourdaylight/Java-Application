package a2;

public abstract class Player {
    private String name;
    public static RedPlayer redPlayer = new RedPlayer();
    public static BluePlayer bluePlayer = new BluePlayer();
    public String toString() {
        return name;
    }
    public Player(String name) {
        this.name = name;
    }

    public static final class RedPlayer extends Player {

        private RedPlayer() {
            super("Red Player");
        }
    }

    public static final class BluePlayer extends Player {

        private BluePlayer() {
            super("Blue Player");
        }
    }

}

