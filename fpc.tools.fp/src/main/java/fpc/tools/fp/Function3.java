package fpc.tools.fp;

import lombok.NonNull;

public interface Function3<A,B,C,R> {

    @NonNull R apply(@NonNull A a, @NonNull B b, @NonNull C c);

    default @NonNull Function1<A,R> f23(@NonNull B b, @NonNull C c) {
        return a -> apply(a,b,c);
    }
}
