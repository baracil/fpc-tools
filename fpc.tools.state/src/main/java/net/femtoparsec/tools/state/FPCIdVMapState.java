package net.femtoparsec.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.state.IdVMapState;
import fpc.tools.state.Versioned;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Collection;
import java.util.Map;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 *
 * @author Bastien Aracil
 */
@EqualsAndHashCode(exclude = {"idGetter"},callSuper = true)
public class FPCIdVMapState<I, V extends Versioned> extends FPCMapStateBase<I, V, FPCIdVMapState<I, V>> implements IdVMapState<I, V> {

    private final Function1<? super V, ? extends I> idGetter;

    public FPCIdVMapState(
            Map<I, V> content,
            Function1<? super V, ? extends I> idGetter) {
        super(content, c -> new FPCIdVMapState<>(c, idGetter));
        this.idGetter = idGetter;
    }

    @Override
    protected FPCIdVMapState<I, V> getThis() {
        return this;
    }

    @Override
    public IdVMapState<I, V> put(V value) {
        return put(idGetter.apply(value), value);
    }

    @Override
    public IdVMapState<I, V> replace(V value) {
        return replace(idGetter.apply(value), value);
    }

    @Override
    public IdVMapState<I, V> updateValue(V value) {
        return update(idGetter.apply(value), value, Versioned.VERSIONED_COMPARATOR);
    }

    @Override
    public IdVMapState<I, V> updateValues(Collection<V> newValues) {
        return update(newValues, idGetter, Versioned.VERSIONED_COMPARATOR);
    }

    @Override
    public <T> IdVMapState<I, V> updateItems(Collection<T> items,
                                                      Function1<? super T, ? extends V> valueGetter) {
        return update(items, valueGetter.then(idGetter), valueGetter, Versioned.VERSIONED_COMPARATOR);
    }
}
