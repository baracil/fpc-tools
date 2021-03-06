package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.Function;

/**
 * @author Bastien Aracil
 */
public interface Try2<A,B, Z,T extends Throwable> {

    @NonNull
    Z f(@NonNull A a, @NonNull B b) throws T;

    @NonNull
    default TryResult<Z,Throwable> fSafe(@NonNull A a, @NonNull B b) {
        try {
            return TryResult.success(f(a,b));
        } catch (Throwable throwable) {
            FPUtils.interruptIfCausedByInterruption(throwable);
            return TryResult.failure(throwable);
        }
    }

    @NonNull
    default Function2<A,B,Z> wrapError(@NonNull Function1<? super Throwable, ? extends RuntimeException> errorWrapper) {
        return (a,b) -> {
            try {
                return f(a,b);
            } catch (Throwable t) {
                throw  errorWrapper.f(t);
            }
        };
    }

    default Try1<B,Z,T> f1(@NonNull A a) {
        return b -> f(a,b);
    }

    default Try1<A,Z,T> f2(@NonNull B b) {
        return a -> f(a,b);
    }

    default Try1<B,Z,T> f1(Function0<? extends A> a) {
        return b -> f(a.f(), b);
    }

    default Try1<A,Z,T> f2(Function0<? extends B> b) {
        return a -> f(a, b.f());
    }

    @NonNull
    default <Y> Try2<A,B,Y,T> then(Function<? super Z, ? extends Y> after) {
        return (a,b) -> after.apply(this.f(a,b));
    }


    @NonNull
    default Function2<A,B, TryResult<Z,Throwable>> safe() {
        return this::fSafe;
    }

    @NonNull
    default Function1<A, Try1<B,Z,T>> curry1() {
        return a -> b -> this.f(a,b);
    }

    @NonNull
    default Function1<B, Try1<A,Z,T>> curry2() {
        return b -> a -> this.f(a,b);
    }

    @NonNull
    default Function2<A,B, Try0<Z,T>> curry12() {
        return (a,b) -> () -> this.f(a,b);
    }

    @NonNull
    static <A,B,Z, T extends Throwable> Try2<A,B,Z,T> of(Try2<A,B,Z,T> try2) {
        return try2;
    }

}
