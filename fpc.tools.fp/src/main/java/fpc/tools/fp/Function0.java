package fpc.tools.fp;

import java.util.function.Supplier;

public interface Function0<R> extends Supplier<R>, Try0<R, RuntimeException> {

    static <R> Function0<R> of(Function0<R> function0) {
        return function0;
    }


    R apply();

    @Override
    default R get() {
        return apply();
    }


    static <R> Function0<R> cons(R parameter) {
        return () -> parameter;
    }

}
