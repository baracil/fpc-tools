package fpc.tools.lang;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class UnsubscriptionError extends FPCException {

    @Getter
    private final List<? extends Throwable> errors;

    public UnsubscriptionError(
            String message, List<? extends Throwable> errors
    ) {
        super(message);
        this.errors = errors;
    }
}
