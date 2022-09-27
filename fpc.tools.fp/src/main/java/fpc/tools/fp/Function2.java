package fpc.tools.fp;

import lombok.NonNull;

public interface Function2<A,B,R> extends Try2<A,B,R,RuntimeException> {

    @NonNull R apply(@NonNull A a, @NonNull B b);

    default @NonNull Function1<A,R> f2(@NonNull B b) {
        return a -> apply(a,b);
    }

    default @NonNull Function1<B,R> f1(@NonNull A a) {
        return b -> apply(a,b);
    }
}
