package connect4;
public class IllegalMoveException extends Exception {
    public IllegalMoveException(String errorMessage) {
        super(errorMessage);
    }
}