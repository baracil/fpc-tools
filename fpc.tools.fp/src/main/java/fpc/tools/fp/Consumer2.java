package fpc.tools.fp;

import java.util.function.BiConsumer;

public interface Consumer2<A,B> extends BiConsumer<A,B>, TryConsumer2<A,B,RuntimeException> {

    @Deprecated
    default void f(A a, B b) {
        accept(a,b);
    }

    @Override
    void accept(A a, B b);

    default Consumer1<A> f2(B b) {
        return a -> f(a,b);
    }
}
