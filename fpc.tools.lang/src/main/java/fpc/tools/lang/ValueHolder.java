package fpc.tools.lang;

import fpc.tools.fp.Function0;
import fpc.tools.fp.Try0;
import lombok.NonNull;

import java.util.Optional;

public interface ValueHolder<T> {

    @NonNull Optional<T> get();

    void push(@NonNull  T value);

    void pop();

    boolean isEmpty();

    default <E extends Throwable> @NonNull T getOrElseThrow(@NonNull Function0<? extends E> throwableSupplier) throws E {
        return get().orElseThrow(throwableSupplier);
    }


    default @NonNull T getOrElse(@NonNull T defaultValue) {
        return get().orElse(defaultValue);
    }

    default @NonNull T getOrElseGet(@NonNull Function0<? extends T> defaultValue) {
        return get().orElseGet(defaultValue);
    }

    default void popAndPush(@NonNull T value) {
        pop();
        push(value);
    }

    default <R,E extends Throwable> @NonNull R callWith(@NonNull T value, Try0<R,E> try0) throws E {
        try {
            push(value);
            return try0.apply();
        } finally {
            pop();
        }
    }
}
