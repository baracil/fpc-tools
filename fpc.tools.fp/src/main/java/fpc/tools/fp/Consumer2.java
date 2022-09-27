package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.BiConsumer;

public interface Consumer2<A,B> extends BiConsumer<A,B>, TryConsumer2<A,B,RuntimeException> {

    @Deprecated
    default void f(@NonNull A a, @NonNull B b) {
        accept(a,b);
    }

    @Override
    void accept(@NonNull A a, @NonNull B b);

    default @NonNull Consumer1<A> f2(@NonNull B b) {
        return a -> f(a,b);
    }
}
