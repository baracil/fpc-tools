package fpc.tools.fp;

import lombok.NonNull;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Bastien Aracil
 */
public interface Predicate1<A> extends Predicate<A> {

    boolean f(@NonNull A a);

    @Override
    default boolean test(@NonNull A a) {
        return f(a);
    }

    @Override
    default Predicate1<A> and(@NonNull Predicate<? super A> other) {
        return a -> this.test(a) && other.test(a);
    }

    @Override
    default Predicate1<A> or(@NonNull Predicate<? super A> other) {
        return a -> this.test(a) || other.test(a);
    }

    default Predicate1<A> safe(boolean valueIfFails) {
        return a -> {
            try {
                return this.f(a);
            } catch (Exception e) {
                return valueIfFails;
            }
        };
    }

    default Predicate1<A> safe(Predicate2<? super A, ? super Exception> valueGetterForError) {
        return a -> {
            try {
                return this.f(a);
            } catch (Exception e) {
                return valueGetterForError.test(a,e);
            }
        };
    }

    default <B> Predicate1<B> o(Function<? super B, ? extends A> f) {
        return b -> this.test(f.apply(b));
    }

    default Function1<Stream<A>, Stream<A>> liftToStream() {
        return s -> s.filter(this);
    }

    default Function1<A,Optional<A>> toOptional() {
        return a -> Optional.of(a).filter(this);
    }

    @Override
    default Predicate1<A> negate() {
        return a -> !test(a);
    }

    static <A> Predicate1<A> of (Predicate<A> predicate) {
        if (predicate instanceof Predicate1) {
            return (Predicate1<A>)predicate;
        }
        return predicate::test;
    }

}
