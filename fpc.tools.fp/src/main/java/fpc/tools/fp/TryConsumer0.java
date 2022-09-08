package fpc.tools.fp;

import lombok.NonNull;

public interface TryConsumer0<E extends Exception> {

    static <E extends Exception> TryConsumer0<E> of(@NonNull TryConsumer0<E> tryConsumer0) {
        return tryConsumer0;
    }

    void accept() throws E;

}
