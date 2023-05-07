package fpc.tools.fp;

import lombok.Getter;

/**
 * @author Bastien Aracil
 */
public class TryException extends RuntimeException {

    @Getter
    private final Throwable cause;

    public TryException(Throwable cause) {
        super("A try throw an unexpected exception ",cause);
        this.cause = cause;
    }
}
