package fpc.tools.fp;

import lombok.NonNull;

public interface Consumer0 extends Runnable {

    static Consumer0 of(@NonNull Runnable runnable) {
        if (runnable instanceof Consumer0 c0) {
            return c0;
        }
        return runnable::run;
    }

    void f();

    @Override
    default void run() {
        f();
    }

    default Function0<Nil> toFunction() {
        return () -> {
            f();
            return Nil.NULL;
        };
    }

    default TryResult<Nil,RuntimeException> acceptSafe() {
        return toFunction().fSafe();
    }

}
