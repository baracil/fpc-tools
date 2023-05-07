package fpc.tools.fp;

import java.util.function.Consumer;

public interface Consumer1<A> extends Consumer<A>, TryConsumer1<A,RuntimeException> {

    @Override
    void accept(A a);

    default Function1<A, Nil> toFunction() {
        return a -> {
            f(a);
            return Nil.NULL;
        };
    }

    default TryResult<Throwable, Nil> acceptSafe(A value) {
        return toFunction().applySafely(value);
    }


    @Deprecated
    default void f(A a) {
        accept(a);
    }


}
