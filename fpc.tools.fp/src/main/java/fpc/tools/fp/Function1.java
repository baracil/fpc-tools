package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.Function;

public interface Function1<A,R> extends Function<A,R>, Try1<A,R,RuntimeException> {

    @NonNull R apply(@NonNull A value);

    static @NonNull <A> Function1<A,A> identity() {
        return a -> a;
    }

    default <S> @NonNull Function1<A,S> then(@NonNull Function1<? super R, ? extends S> after) {
        return r ->after.apply(apply(r));
    }

}
