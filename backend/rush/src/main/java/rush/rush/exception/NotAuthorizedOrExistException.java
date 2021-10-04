package rush.rush.exception;

public class NotAuthorizedOrExistException extends RuntimeException {

    public NotAuthorizedOrExistException(String message) {
        super(message);
    }
}
