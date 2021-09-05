package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.Supplier;

public interface Function0<R> extends Supplier<R> {


    @NonNull R f();

    @Override
    default R get() {
        return f();
    }

    /**
     * @return the safe evaluation of this
     */
    @NonNull
    default TryResult<R,RuntimeException> fSafe() {
        return safe().f();
    }

    @NonNull
    default Function0<TryResult<R,RuntimeException>> safe() {
        return () -> {
            try {
                return TryResult.success(f());
            } catch (RuntimeException e) {
                FPUtils.interruptIfCausedByInterruption(e);
                return TryResult.failure(e);
            }
        };
    }

    static <R> @NonNull Function0<R> cons(@NonNull R parameter) {
        return () -> parameter;
    }

}
