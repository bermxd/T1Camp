package bermxd.t1task4.customException;

public class CodeException extends RuntimeException {

    public CodeException(String message) {
        super(message);
    }

    public CodeException(String message, Throwable cause) {
        super(message, cause);
    }
}