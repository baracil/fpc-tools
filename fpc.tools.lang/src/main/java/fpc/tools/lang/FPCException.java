package fpc.tools.lang;

public class FPCException extends RuntimeException {

    public FPCException(String message) {
        super(message);
    }

    public FPCException(String message, Throwable cause) {
        super(message, cause);
    }
}
