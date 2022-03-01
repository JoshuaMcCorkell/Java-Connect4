class Column {
    int[] data;
    int height;

    /**
     * Creates a new blank column object (6 spaces high).
     * 
     * Contains 2 attributes. 
     * <ul><strong>data:</strong> The data representing the tokens in the column (stack).
     * 0 is blank, 1 is first player, 2 is second player.</ul>
     * <ul><strong>height:</strong> The height of the row. Starts at 0, increases by 1 every
     * time a token is added to the column.</ul>
     * <em>Note:</em> The data at index height will be 0, and the next token placed will be placed 
     * at index height because array indexes start at 0.
     */
    public Column() {
        data = new int[] {0, 0, 0, 0, 0, 0, 0};
        height = 0;
    }

    /**
     * Adds value to the top of the column (stack).
     * @param value  The value to add. Should be a 1 or a 2.
     */
    public void push(int value) {
        if (height < 6) {
            data[height] = value;
            height++;
        } else {
            throw new IndexOutOfBoundsException("A token was placed when the column was already full!");
        } 
    }

    /**
     * Removes and returns the top value of the column (stack).
     * @return  The value that was removed from the top of the column (stack).
     */
    public int pop() {
        int val = data[height - 1];
        data[height - 1] = 0;
        height--;
        return val;
    }
}
