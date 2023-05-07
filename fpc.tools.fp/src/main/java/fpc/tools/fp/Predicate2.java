package fpc.tools.fp;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Bastien Aracil
 */
public interface Predicate2<A,B> extends BiPredicate<A,B> {

    boolean test(A a, B b);

    @Override
    default Predicate2<A,B> and(BiPredicate<? super A, ? super B> other) {
        return (a,b) -> this.test(a,b) && other.test(a,b);
    }

    @Override
    default Predicate2<A,B> or(BiPredicate<? super A, ? super B> other) {
        return (a,b) -> this.test(a,b) || other.test(a,b);
    }

    default Predicate2<A,B> and1(Predicate<? super A> other) {
        return (a,b) -> this.test(a,b) && other.test(a);
    }

    default Predicate2<A,B> and2(Predicate<? super B> other) {
        return (a,b) -> this.test(a,b) && other.test(b);
    }

    default Predicate2<A,B> or1(Predicate<? super A> other) {
        return (a,b) -> this.test(a,b) || other.test(a);
    }

    default Predicate2<A,B> or2(Predicate<? super B> other) {
        return (a,b) -> this.test(a,b) || other.test(b);
    }

    default Predicate1<B> f1(A a) {
        return b -> this.test(a, b);
    }

    default Predicate1<A> f2(B b) {
        return a -> this.test(a, b);
    }

    default Predicate1<B> f1(Supplier<? extends A> a) {
        return b -> this.test(a.get(), b);
    }

    default Predicate1<A> f2(Supplier<? extends B> b) {
        return a -> this.test(a, b.get());
    }
}
