package fpc.tools.lang;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class UnsubscriptionError extends FPCException {

    @NonNull
    @Getter
    private final List<? extends Throwable> errors;

    public UnsubscriptionError(
            @NonNull String message, @NonNull List<? extends Throwable> errors
    ) {
        super(message);
        this.errors = errors;
    }
}
