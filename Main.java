import connect4.Connect4;

public class Main {
    public static void main(String[] args) {
        Connect4 game = new Connect4();
        game.print();
        game.play(1);
        game.play(1);
        game.play(2);
        game.play(2);
        game.play(3);
        game.play(3);
        game.play(6);
        game.play(4);
        game.play(6);
        game.print();
        game.play(4);
        game.print();
    }
}
