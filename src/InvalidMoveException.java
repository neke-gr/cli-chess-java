public class InvalidMoveException extends Exception {

    public InvalidMoveException(String message) {
        super(message);
    }

    public InvalidMoveException() {
        super("Invalid Move!");
    }
}
