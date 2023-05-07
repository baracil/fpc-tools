package fpc.tools.fp;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Bastien Aracil
 */
public interface Try1<A, Z, T extends Throwable> {

    Z apply(A a) throws T;

    default TryResult<Throwable, Z> applySafely(A a) {
        try {
            return TryResult.success(apply(a));
        } catch (Throwable throwable) {
            FPUtils.interruptIfCausedByAnInterruption(throwable);
            return TryResult.failure(throwable);
        }
    }

    default Function1<A,Z> wrapError(Function1<? super Throwable, ? extends RuntimeException> errorWrapper) {
        return a -> {
            try {
                return apply(a);
            } catch (Throwable t) {
                throw  errorWrapper.apply(t);
            }
        };
    }


    default Try0<Z,T> f1(A a) {
        return () -> apply(a);
    }

    default Try0<Z,T> apply(Supplier<? extends A> a) {
        return () -> apply(a.get());
    }

    default <Y> Try1<A,Y,T> then(Function<? super Z, ? extends Y> after){
        return a -> after.apply(this.apply(a));
    }

    default <Y> Try1<A,Y,T> thenTry(Try1<? super Z, ? extends Y,? extends T> after) {
        return a -> after.apply(this.apply(a));
    }

    default Function1<A,TryResult<Throwable, Z>> safe() {
        return this::applySafely;
    }

    default <C> Try2<C,A,Z,T> cons1() {
        return (c,a) -> this.apply(a);
    }
    default <C> Try2<A,C,Z,T> cons2() {
        return (a,c) -> this.apply(a);
    }

    static <A,Z,T extends Throwable> Try1<A,Z,T> of(Try1<A,Z,T> t) {
        return t;
    }

}
