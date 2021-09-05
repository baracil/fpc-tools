package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.Consumer;

public interface Consumer1<A> extends Consumer<A> {

    void f(@NonNull A a);

    @Override
    default void accept(A a) {
        f(a);
    }

    default Function1<A, Nil> toFunction() {
        return a -> {
            f(a);
            return Nil.NULL;
        };
    }

    @NonNull
    default TryResult<Nil,RuntimeException> acceptSafe(@NonNull A value) {
        return toFunction().fSafe(value);
    }

}
