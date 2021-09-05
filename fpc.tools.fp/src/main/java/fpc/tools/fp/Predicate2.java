package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Bastien Aracil
 */
public interface Predicate2<A,B> extends BiPredicate<A,B> {

    boolean f(@NonNull A a,@NonNull B b);

    @Override
    default boolean test(@NonNull A a,@NonNull B b) {
        return f(a,b);
    }

    @Override
    default Predicate2<A,B> and(@NonNull BiPredicate<? super A, ? super B> other) {
        return (a,b) -> this.f(a,b) && other.test(a,b);
    }

    @Override
    default Predicate2<A,B> or(@NonNull BiPredicate<? super A, ? super B> other) {
        return (a,b) -> this.f(a,b) || other.test(a,b);
    }

    default Predicate2<A,B> and1(@NonNull Predicate<? super A> other) {
        return (a,b) -> this.f(a,b) && other.test(a);
    }

    default Predicate2<A,B> and2(@NonNull Predicate<? super B> other) {
        return (a,b) -> this.f(a,b) && other.test(b);
    }

    default Predicate2<A,B> or1(@NonNull Predicate<? super A> other) {
        return (a,b) -> this.f(a,b) || other.test(a);
    }

    default Predicate2<A,B> or2(@NonNull Predicate<? super B> other) {
        return (a,b) -> this.f(a,b) || other.test(b);
    }

    default Predicate1<B> f1(A a) {
        return b -> this.f(a, b);
    }

    default Predicate1<A> f2(B b) {
        return a -> this.f(a, b);
    }

    default Predicate1<B> f1(Supplier<? extends A> a) {
        return b -> this.f(a.get(), b);
    }

    default Predicate1<A> f2(Supplier<? extends B> b) {
        return a -> this.f(a, b.get());
    }
}
