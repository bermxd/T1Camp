package bermxd.t1task4.exception;

public class RoleServiceException extends RuntimeException {
    public RoleServiceException(String message) {
        super(message);
    }

    public RoleServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}