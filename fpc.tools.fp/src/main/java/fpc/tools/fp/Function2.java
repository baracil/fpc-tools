package fpc.tools.fp;

import lombok.NonNull;

public interface Function2<A,B,R> {

    @NonNull R f(@NonNull A a, @NonNull B b);


    default @NonNull Function1<A,R> f2(@NonNull B b) {
        return a -> f(a,b);
    }

    default @NonNull Function1<B,R> f1(@NonNull A a) {
        return b -> f(a,b);
    }
}
