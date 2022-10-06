package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.Consumer;

public interface Consumer1<A> extends Consumer<A>, TryConsumer1<A,RuntimeException> {

    @Override
    void accept(@NonNull A a);

    default Function1<A, Nil> toFunction() {
        return a -> {
            f(a);
            return Nil.NULL;
        };
    }

    @NonNull
    default TryResult<Throwable, Nil> acceptSafe(@NonNull A value) {
        return toFunction().applySafely(value);
    }


    @Deprecated
    default void f(@NonNull A a) {
        accept(a);
    }


}
