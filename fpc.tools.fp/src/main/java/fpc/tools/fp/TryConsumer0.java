package fpc.tools.fp;

import lombok.NonNull;

public interface TryConsumer0<E extends Throwable> {

    static <E extends Throwable> TryConsumer0<E> of(@NonNull TryConsumer0<E> tryConsumer0) {
        return tryConsumer0;
    }

    void accept() throws E;

}
