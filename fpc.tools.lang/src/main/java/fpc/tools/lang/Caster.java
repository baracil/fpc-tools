package fpc.tools.lang;

import fpc.tools.fp.Function1;

import java.util.Optional;

public interface Caster<T> extends Function1<Object,Optional<T>> {

    Optional<T> cast(Object object);

    @Override
    default Optional<T> apply(Object o) {
        return cast(o);
    }
}
