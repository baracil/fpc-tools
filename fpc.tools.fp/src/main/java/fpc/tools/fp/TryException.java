package fpc.tools.fp;

import lombok.Getter;
import lombok.NonNull;

/**
 * @author Bastien Aracil
 */
public class TryException extends RuntimeException {

    @NonNull
    @Getter
    private final Throwable cause;

    public TryException(Throwable cause) {
        super("A try throw an unexpected exception ",cause);
        this.cause = cause;
    }
}
