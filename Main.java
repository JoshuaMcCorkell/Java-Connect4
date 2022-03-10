import java.util.Scanner;

import connect4.Connect4;
import connect4.Connect4GUI;

public class Main {
    public static void main(String[] args) {
        Scanner consoleIn = new Scanner(System.in);
        Connect4 game = new Connect4();
        Connect4GUI gui = new Connect4GUI(game);

        int next;
        while (game.winner == 0) {
            next = consoleIn.nextInt();
            game.play(next);
            gui.updateBoard();
        }
    }
}
