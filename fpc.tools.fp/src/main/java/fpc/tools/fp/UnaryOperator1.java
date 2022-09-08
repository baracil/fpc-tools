package fpc.tools.fp;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.util.function.Function;

/**
 * @author Bastien Aracil
 */
public interface UnaryOperator1<A> extends Function1<A,A> {

    @NonNull
    static <T> UnaryOperator1<T> chain(@NonNull ImmutableList<? extends UnaryOperator1<T>> functions) {
        if (functions.isEmpty()) {
            return t -> t;
        }
        else if (functions.size() == 1) {
            return UnaryOperator1.of(functions.get(0));
        }

        UnaryOperator1<T> result = t -> t;

        for (UnaryOperator1<T> function : functions) {
            result = result.then(function);
        }
        return result;
    }


    static <A> UnaryOperator1<A> of(Function1<A,A> f1) {
        if (f1 instanceof UnaryOperator1) {
            return (UnaryOperator1<A>)f1;
        }
        return f1::apply;
    }

    static <A> UnaryOperator1<A> cons(@NonNull A value) {
        return a -> value;
    }

    default UnaryOperator1<A> then(UnaryOperator1<A> after) {
        return a -> after.apply(apply(a));
    }
    
    default <B> UnaryOperator1<B> map(Function<? super B, ? extends A> to, Function<? super A, ? extends B> from) {
        return b -> from.apply(this.apply(to.apply(b)));
    }
}
