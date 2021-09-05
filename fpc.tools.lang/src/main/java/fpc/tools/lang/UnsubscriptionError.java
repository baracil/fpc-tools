package fpc.tools.lang;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;

public class UnsubscriptionError extends FPCException {

    @NonNull
    @Getter
    private final ImmutableList<? extends Throwable> errors;

    public UnsubscriptionError(
            @NonNull String message, @NonNull ImmutableList<? extends Throwable> errors
    ) {
        super(message);
        this.errors = errors;
    }
}
