package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.Supplier;

public interface Function0<R> extends Supplier<R> {


    @NonNull R apply();

    @Override
    default R get() {
        return apply();
    }

    /**
     * @return the safe evaluation of this
     */
    @NonNull
    default TryResult<R,RuntimeException> fSafe() {
        return safe().apply();
    }

    @NonNull
    default Function0<TryResult<R,RuntimeException>> safe() {
        return () -> {
            try {
                return TryResult.success(apply());
            } catch (RuntimeException e) {
                FPUtils.interruptIfCausedByAnInterruption(e);
                return TryResult.failure(e);
            }
        };
    }

    static <R> @NonNull Function0<R> cons(@NonNull R parameter) {
        return () -> parameter;
    }

}
