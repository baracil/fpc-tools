package fpc.tools.fp;

import lombok.NonNull;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Bastien Aracil
 */
public interface Predicate2<A,B> extends BiPredicate<A,B> {

    boolean test(@NonNull A a, @NonNull B b);

    @Override
    default @NonNull Predicate2<A,B> and(@NonNull BiPredicate<? super A, ? super B> other) {
        return (a,b) -> this.test(a,b) && other.test(a,b);
    }

    @Override
    default @NonNull Predicate2<A,B> or(@NonNull BiPredicate<? super A, ? super B> other) {
        return (a,b) -> this.test(a,b) || other.test(a,b);
    }

    default @NonNull Predicate2<A,B> and1(@NonNull Predicate<? super A> other) {
        return (a,b) -> this.test(a,b) && other.test(a);
    }

    default @NonNull Predicate2<A,B> and2(@NonNull Predicate<? super B> other) {
        return (a,b) -> this.test(a,b) && other.test(b);
    }

    default @NonNull Predicate2<A,B> or1(@NonNull Predicate<? super A> other) {
        return (a,b) -> this.test(a,b) || other.test(a);
    }

    default @NonNull Predicate2<A,B> or2(@NonNull Predicate<? super B> other) {
        return (a,b) -> this.test(a,b) || other.test(b);
    }

    default @NonNull Predicate1<B> f1(A a) {
        return b -> this.test(a, b);
    }

    default @NonNull Predicate1<A> f2(B b) {
        return a -> this.test(a, b);
    }

    default @NonNull Predicate1<B> f1(@NonNull Supplier<? extends A> a) {
        return b -> this.test(a.get(), b);
    }

    default @NonNull Predicate1<A> f2(@NonNull Supplier<? extends B> b) {
        return a -> this.test(a, b.get());
    }
}
