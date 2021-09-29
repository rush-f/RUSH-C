package rush.rush.exception;

public class NotAuthorizedOrExistException extends IllegalStateException {

    public NotAuthorizedOrExistException(String message) {
        super(message);
    }
}
