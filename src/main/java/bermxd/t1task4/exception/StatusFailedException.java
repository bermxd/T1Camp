package bermxd.t1task4.exception;

public class StatusFailedException extends RuntimeException {
    public StatusFailedException(String message) {
        super(message);
    }

    public StatusFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}