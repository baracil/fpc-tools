package fpc.tools.lang;

import fpc.tools.fp.Function0;
import fpc.tools.fp.Try0;

import java.util.Optional;

public interface ValueHolder<T> {

    Optional<T> get();

    void push( T value);

    void pop();

    boolean isEmpty();

    default <E extends Throwable> T getOrElseThrow(Function0<? extends E> throwableSupplier) throws E {
        return get().orElseThrow(throwableSupplier);
    }


    default T getOrElse(T defaultValue) {
        return get().orElse(defaultValue);
    }

    default T getOrElseGet(Function0<? extends T> defaultValue) {
        return get().orElseGet(defaultValue);
    }

    default void popAndPush(T value) {
        pop();
        push(value);
    }

    default <R,E extends Throwable> R callWith(T value, Try0<R,E> try0) throws E {
        try {
            push(value);
            return try0.apply();
        } finally {
            pop();
        }
    }
}
