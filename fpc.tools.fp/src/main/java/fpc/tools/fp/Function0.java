package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.Supplier;

public interface Function0<R> extends Supplier<R>, Try0<R, RuntimeException> {

    static <R> @NonNull Function0<R> of(@NonNull Function0<R> function0) {
        return function0;
    }


    @NonNull R apply();

    @Override
    default R get() {
        return apply();
    }


    static <R> @NonNull Function0<R> cons(@NonNull R parameter) {
        return () -> parameter;
    }

}
