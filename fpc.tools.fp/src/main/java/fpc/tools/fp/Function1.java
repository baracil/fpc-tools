package fpc.tools.fp;

import java.util.function.Function;

public interface Function1<A,R> extends Function<A,R>, Try1<A,R,RuntimeException> {

    R apply(A value);

    static <A> Function1<A,A> identity() {
        return a -> a;
    }

    default <S> Function1<A,S> then(Function1<? super R, ? extends S> after) {
        return r ->after.apply(apply(r));
    }

}
