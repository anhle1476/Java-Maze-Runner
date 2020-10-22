package maze;

public class DecodeFailedException extends Exception {
    public DecodeFailedException(String reason, String statement) {
        super(reason + ": " + statement);
    }

    public DecodeFailedException(String reason, String statement, Throwable cause) {
        super(reason + ": " + statement, cause);
    }
}
