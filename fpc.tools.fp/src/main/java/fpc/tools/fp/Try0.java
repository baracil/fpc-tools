package fpc.tools.fp;

import java.util.function.Function;

/**
 * @author Bastien Aracil
 */
public interface Try0<Z,T extends Throwable> {

    Z apply() throws T;

    default TryResult<Throwable, Z> applySafely() {
        try {
            return TryResult.success(apply());
        } catch (Throwable throwable) {
            FPUtils.interruptIfCausedByAnInterruption(throwable);
            return TryResult.failure(throwable);
        }
    }

    default Function0<Z> wrapError(Function1<? super Throwable, ? extends RuntimeException> errorWrapper) {
        return () -> {
            try {
                return apply();
            } catch (Throwable t) {
                throw  errorWrapper.apply(t);
            }
        };
    }

    default Function0<TryResult<Throwable, Z>> safe() {
        return this::applySafely;
    }

    default <Y> Try0<Y,T> then(Function<? super Z, ? extends Y> after) {
        return () -> after.apply(this.apply());
    }

    default <Y> Try0<Y,T> thenTry(Try1<? super Z, ? extends Y, ? extends T> after) {
        return () -> after.apply(this.apply());
    }

    static <A,T extends Throwable> Try0<A,T> of(Try0<A,T> try0) {
        return try0;
    }


    @Deprecated
    default <Y> Try0<Y,T> map(Function<? super Z, ? extends Y> f) {
        return then(f);
    }

    @Deprecated
    default <Y> Try0<Y,T> map(Try1<? super Z, ? extends Y, ? extends T> t) {
        return thenTry(t);
    }


}
