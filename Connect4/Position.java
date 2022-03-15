package connect4;
import java.util.Arrays;
import java.util.LinkedList;

class Position {
    int[][] data;
    int[] heights;
    int rows;
    int columns;
    int[] checkOrder = {3, 2, 4, 5, 1, 0, 6};

    /**
     * Creates a new blank Position object.
     * 
     * Contains 2 attributes. 
     * <ul>
     * <li><strong>data:</strong> A two dimensional array containing the data for the array.
     * 0 is blank, 1 is first player, 2 is second player. <em>Note that the first column is column 0</em></li>
     * <li><strong>height:</strong> An array containing the current heights of all the columns in data.</li>
     * </ul>
     * <em>Note:</em> The data in a column at index height will be 0, and the next token placed will be placed 
     * at index height because array indexes start at 0.
     */
    public Position(int columns, int height) {
        //initialize data with all 0
        data = new int[columns][];
        for (int i = 0; i < columns; i++) {
            data[i] = new int[height];
                for (int j = 0; j < height; j++) {
                    data[i][j] = 0;
                }
        }
        //initialize heights with all 0
        heights = new int[columns];
        for (int i = 0; i < columns; i++) {
            heights[i] = 0;
        }

        this.rows = height;
        this.columns = columns;
    }

    /**
     * Adds value to the top of the specified column.
     * 
     * @param column  The column to add to.
     * @param value  The value to add. Should be a 1 or a 2.
     * @throws IllegalMoveException
     * @throws IndexOutOfBoundsException  If column is already full.
     */
    public void push(int column, int value) throws IllegalMoveException {
        if (heights[column] < rows) {
            data[column][heights[column]] = value;
            heights[column]++;
        } else {
            throw new IllegalMoveException("A token was placed when the column was already full!");
        } 
    }

    /**
     * Removes and returns the top value of the column (stack).
     * @return  The value that was removed from the top of the column (stack).
     */
    public int pop(int column) {
        int val = data[column][heights[column] - 1];
        data[column][heights[column] - 1] = 0;
        heights[column]--;
        return val;
    }

    /**
     * Returns the disk in the given column and row.
     * Throws ArrayIndexOutOfBoundsException if the spot is not within the bounds of the game.
     * @param column
     * @param row
     * @return 0 if blank, otherwise a 1 or 2 depending on which disk.
     */
    public int get(int column, int row) {
        return data[column][row];
    }

    public Integer[] getLegal() {
        LinkedList<Integer> legalPlays = new LinkedList<>();

        for (int i : checkOrder) {
            if (heights[i] < rows) {
                legalPlays.add(i);
            }
        }
        Integer[] legalPlaysArray = new Integer[legalPlays.size()];
        legalPlays.toArray(legalPlaysArray);
        return legalPlaysArray;
    }

    public boolean isFull() {
        boolean full = true;
        for (int i : heights) {
            if (i != rows) {
                full = false;
                break;
            }
        }
        return full;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Position)) {
            return false;
        }

        Position other = (Position) o;
        return Arrays.deepEquals(data, other.data);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(data);
    }
}
