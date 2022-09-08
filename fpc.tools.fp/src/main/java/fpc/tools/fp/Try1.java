package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Bastien Aracil
 */
public interface Try1<A, Z, T extends Throwable> {

    @NonNull
    Z apply(@NonNull A a) throws T;

    @NonNull
    default TryResult<Z,Throwable> fSafe(@NonNull A a) {
        try {
            return TryResult.success(apply(a));
        } catch (Throwable throwable) {
            FPUtils.interruptIfCausedByAnInterruption(throwable);
            return TryResult.failure(throwable);
        }
    }

    @NonNull
    default Function1<A,Z> wrapError(@NonNull Function1<? super Throwable, ? extends RuntimeException> errorWrapper) {
        return a -> {
            try {
                return apply(a);
            } catch (Throwable t) {
                throw  errorWrapper.apply(t);
            }
        };
    }


    default Try0<Z,T> f1(@NonNull A a) {
        return () -> apply(a);
    }

    default Try0<Z,T> apply(@NonNull Supplier<? extends A> a) {
        return () -> apply(a.get());
    }

    @NonNull
    default <Y> Try1<A,Y,T> then(@NonNull Function<? super Z, ? extends Y> after){
        return a -> after.apply(this.apply(a));
    }

    default <Y> Try1<A,Y,T> thenTry(@NonNull Try1<? super Z, ? extends Y,? extends T> after) {
        return a -> after.apply(this.apply(a));
    }

    @NonNull
    default Function1<A,TryResult<Z,Throwable>> safe() {
        return this::fSafe;
    }

    default <C> Try2<C,A,Z,T> cons1() {
        return (c,a) -> this.apply(a);
    }
    default <C> Try2<A,C,Z,T> cons2() {
        return (a,c) -> this.apply(a);
    }

    static <A,Z,T extends Throwable> Try1<A,Z,T> of(@NonNull Try1<A,Z,T> t) {
        return t;
    }

}
