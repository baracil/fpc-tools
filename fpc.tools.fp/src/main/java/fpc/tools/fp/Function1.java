package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.Function;

public interface Function1<A,R> extends Function<A,R> {

    @NonNull R f(@NonNull A value);

    static @NonNull <A> Function1<A,A> identity() {
        return a -> a;
    }

    @Override
    default R apply(A a) {
        return f(a);
    }

    /**
     * Evaluate safely this function. Calling this method will never throw any {@link Exception}. If an exception occurs during
     * the evaluation, it will be available in the returned value of this method
     * @param a the input parameter used to evaluate this function
     * @return a {@link TryResult} wrapping the result or the exception thrown by this function if any
     */
    @NonNull
    default TryResult<R,RuntimeException> fSafe(@NonNull A a) {
        try {
            return TryResult.success(f(a));
        } catch (RuntimeException e) {
            FPUtils.interruptIfCausedByInterruption(e);
            return TryResult.failure(e);
        }
    }

    default <S> @NonNull Function1<A,S> then(@NonNull Function1<? super R, ? extends S> after) {
        return r ->after.f(f(r));
    }

}
