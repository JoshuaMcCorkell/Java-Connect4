package connect4;
import java.util.HashMap;
import java.lang.StringBuilder;
public class Connect4 {
    static final int HEIGHT = 6;
    static final int COLUMNS = 7;
    static final char[] SYMBOL = new char[] {'.', 'R', 'Y'};

    private Position current;
    private int turn;
    
    public Connect4() {
        current = new Position(COLUMNS, HEIGHT);
        turn = 1;
    }

    public void play(int column) {
        current.push(column, turn);
        turn = 3 - turn;
        //TODO add a win check here
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(100);
        //Build the position, row by row.
        str.append("Current Position:\n");
        for (int i = HEIGHT - 1; i > -1; i--) {
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