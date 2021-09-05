package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.BiConsumer;

public interface Consumer2<A,B> extends BiConsumer<A,B> {

    void f(@NonNull A a, @NonNull B b);

    @Override
    default void accept(A a, B b) {
        f(a,b);
    }

    default @NonNull Consumer1<A> f2(@NonNull B b) {
        return a -> f(a,b);
    }
}
