package fpc.tools.fp;

import lombok.NonNull;

public interface TryConsumer2<A,B,E extends Throwable> {

    static <A,B,E extends Throwable> TryConsumer2<A,B,E> of(@NonNull TryConsumer2<A,B,E> tryConsumer1) {
        return tryConsumer1;
    }

    void accept(@NonNull A a, @NonNull B b) throws E;
    
}
