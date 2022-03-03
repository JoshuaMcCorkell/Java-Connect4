package connect4;
import java.util.HashMap;

public class Connect4 {
    static final int ROWS = 6;
    static final int COLUMNS = 7;
    static final int TOWIN = 4;
    static final String[] SYMBOL = new String[] {".", "R", "Y"};

    public Position current;
    public int turn;
    public int winner;

    public Connect4() {
        current = new Position(COLUMNS, ROWS);
        turn = 1;
    }

    public void play(int column) {
        current.push(column, turn);
        turn = 3 - turn;
        winner = checkWin();
        if (winner != 0) {
            System.out.print(SYMBOL[winner] + " Wins!!! Played:");System.out.println(column);
        }
    }

    public int checkWin() {
        int n;
        for (int i = 0; i < ROWS; i++) { // Loops through the rows
            for (int j = 0; j < COLUMNS - TOWIN + 1; j++) { 
            // Loops through the column that will be the START of the potential win position.
                n = 1;
                for (int k = 0; k < TOWIN; k++) { 
                /* 
                Loops through the columns in the potential win position,
                multiplying n by the value in each position. If n is 0, 
                no one has won. If not, it checks if one of the players has won. 
                */
                    n *= current.get(j + k, i);
                }
                if (n == 1) {
                    return 1;
                } else if (n > Math.pow(2.0, TOWIN) - 0.5) { //Check an inequality to overcome float innaccuracy.
                    return 2;
                }
            }
        }
        return 0;
    }

    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(100);
        //Build the position, row by row.
        str.append("Current Position:\n");
        for (int i = ROWS - 1; i > -1; i--) {
            for (int j = 0; j < COLUMNS; j++) {
                str.append(SYMBOL[current.get(j, i)] + " ");
            }
            str.append('\n');
        }
        //Add the column numbers.
        for (int j = 0; j < COLUMNS; j++) {
            str.append(j);
            str.append(' ');
        }
        //Add a separator.
        str.append('\n');
        for (int j = 0; j < COLUMNS; j++) {
            str.append("--");
        }
        str.append("\nTurn: " + SYMBOL[turn]);
        return str.toString();
    }
}