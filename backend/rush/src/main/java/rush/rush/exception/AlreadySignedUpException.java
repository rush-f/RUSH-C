package rush.rush.exception;

public class AlreadySignedUpException extends IllegalArgumentException {

    public AlreadySignedUpException(String message) {
        super(message);
    }
}
