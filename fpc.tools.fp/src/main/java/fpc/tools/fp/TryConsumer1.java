package fpc.tools.fp;

import lombok.NonNull;

public interface TryConsumer1<A,E extends Exception> {

    static <A,E extends Exception> TryConsumer1<A,E> of(@NonNull TryConsumer1<A,E> tryConsumer1) {
        return tryConsumer1;
    }

    void accept(@NonNull A a) throws E;
    
}
