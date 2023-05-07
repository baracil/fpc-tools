package fpc.tools.fp;

import java.util.function.Function;

/**
 * @author Bastien Aracil
 */
public interface Try2<A,B, Z,T extends Throwable> {

    Z apply(A a, B b) throws T;

    default TryResult<Throwable, Z> applySafely(A a, B b) {
        try {
            return TryResult.success(apply(a,b));
        } catch (Throwable throwable) {
            FPUtils.interruptIfCausedByAnInterruption(throwable);
            return TryResult.failure(throwable);
        }
    }

    default Function2<A,B,Z> wrapError(Function1<? super Throwable, ? extends RuntimeException> errorWrapper) {
        return (a,b) -> {
            try {
                return apply(a,b);
            } catch (Throwable t) {
                throw  errorWrapper.apply(t);
            }
        };
    }

    default Try1<B,Z,T> f1(A a) {
        return b -> apply(a,b);
    }

    default Try1<A,Z,T> f2(B b) {
        return a -> apply(a,b);
    }

    default Try1<B,Z,T> f1(Function0<? extends A> a) {
        return b -> apply(a.apply(), b);
    }

    default Try1<A,Z,T> f2(Function0<? extends B> b) {
        return a -> apply(a, b.apply());
    }

    default <Y> Try2<A,B,Y,T> then(Function<? super Z, ? extends Y> after) {
        return (a,b) -> after.apply(this.apply(a,b));
    }


    default Function2<A,B, TryResult<Throwable, Z>> safe() {
        return this::applySafely;
    }

    default Function1<A, Try1<B,Z,T>> curry1() {
        return a -> b -> this.apply(a,b);
    }

    default Function1<B, Try1<A,Z,T>> curry2() {
        return b -> a -> this.apply(a,b);
    }

    default Function2<A,B, Try0<Z,T>> curry12() {
        return (a,b) -> () -> this.apply(a,b);
    }

    static <A,B,Z, T extends Throwable> Try2<A,B,Z,T> of(Try2<A,B,Z,T> try2) {
        return try2;
    }

}
