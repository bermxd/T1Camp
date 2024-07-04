package bermxd.t1task4.exception;

public class CodeException extends RuntimeException {
    public CodeException(String message) {
        super(message);
    }

    public CodeException(String message, Throwable cause) {
        super(message, cause);
    }
}