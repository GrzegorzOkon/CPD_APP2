package okon.CPD_APP2;

public class ApiException extends RuntimeException {
    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message) {
        super(message);
    }
}
