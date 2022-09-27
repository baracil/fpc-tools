package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.Function;

/**
 * @author Bastien Aracil
 */
public interface Try0<Z,T extends Throwable> {

    @NonNull
    Z apply() throws T;

    @NonNull
    default TryResult<Z,Throwable> applySafely() {
        try {
            return TryResult.success(apply());
        } catch (Throwable throwable) {
            FPUtils.interruptIfCausedByAnInterruption(throwable);
            return TryResult.failure(throwable);
        }
    }

    @NonNull
    default Function0<Z> wrapError(@NonNull Function1<? super Throwable, ? extends RuntimeException> errorWrapper) {
        return () -> {
            try {
                return apply();
            } catch (Throwable t) {
                throw  errorWrapper.apply(t);
            }
        };
    }

    default Function0<TryResult<Z,Throwable>> safe() {
        return this::applySafely;
    }

    @NonNull
    default <Y> Try0<Y,T> then(Function<? super Z, ? extends Y> after) {
        return () -> after.apply(this.apply());
    }

    @NonNull
    default <Y> Try0<Y,T> thenTry(Try1<? super Z, ? extends Y, ? extends T> after) {
        return () -> after.apply(this.apply());
    }

    @NonNull
    static <A,T extends Throwable> Try0<A,T> of(@NonNull Try0<A,T> try0) {
        return try0;
    }


    @Deprecated
    default <Y> Try0<Y,T> map(@NonNull Function<? super Z, ? extends Y> f) {
        return then(f);
    }

    @Deprecated
    default <Y> Try0<Y,T> map(@NonNull Try1<? super Z, ? extends Y, ? extends T> t) {
        return thenTry(t);
    }


}
