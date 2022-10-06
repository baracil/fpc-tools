package fpc.tools.fp;

import lombok.NonNull;

public interface Consumer0 extends Runnable, TryConsumer0<RuntimeException> {

    static Consumer0 of(@NonNull Runnable runnable) {
        if (runnable instanceof Consumer0 c0) {
            return c0;
        }
        return runnable::run;
    }

    void accept();

    @Deprecated
    default void f() {
        accept();
    }

    @Override
    default void run() {
        accept();
    }

    default Function0<Nil> toFunction() {
        return () -> {
            accept();
            return Nil.NULL;
        };
    }

    default TryResult<Throwable, Nil> acceptSafe() {
        return toFunction().applySafely();
    }

}
