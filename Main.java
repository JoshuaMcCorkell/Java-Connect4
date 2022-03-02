import connect4.Connect4;

public class Main {
    public static void main(String[] args) {
        Connect4 game = new Connect4();
        System.out.println(game);
        game.play(1);
        game.play(3);
        game.play(0);
        game.play(3);
        game.play(3);
        System.out.println(game);
    }
}
