package net.femtoparsec.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.state.IdMapState;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 *
 * @author Bastien Aracil
 */
@EqualsAndHashCode(exclude = {"idGetter"},callSuper = true)
public class FPCIdMapState<I, V> extends FPCMapStateBase<I, V, FPCIdMapState<I, V>> implements IdMapState<I, V> {

    @SuppressWarnings("unchecked")
    public static <I, V> FPCIdMapState<I, V> empty(Function1<? super V, ? extends I> idGetter) {
        return new FPCIdMapState<>(Map.of(), idGetter);
    }

    private final Function1<? super V, ? extends I> idGetter;

    public FPCIdMapState(
            Map<I, V> content,
            Function1<? super V, ? extends I> idGetter) {
        super(content, c -> new FPCIdMapState<>(c, idGetter));
        this.idGetter = idGetter;
    }

    @Override
    protected FPCIdMapState<I, V> getThis() {
        return this;
    }

    @Override
    public IdMapState<I, V> put(V value) {
        return put(idGetter.apply(value), value);
    }

    @Override
    public IdMapState<I, V> replace(V value) {
        return replace(idGetter.apply(value), value);
    }

    @Override
    public IdMapState<I, V> updateValue(V value, Comparator<? super V> isNewer) {
        return update(idGetter.apply(value), value, isNewer);
    }

    @Override
    public IdMapState<I, V> updateValues(Collection<V> newValues, Comparator<? super V> isNewer) {
        return update(newValues, idGetter, isNewer);
    }

    @Override
    public <T> IdMapState<I, V> updateItems(Collection<T> items,
                                            Function1<? super T, ? extends V> valueGetter,
                                            Comparator<? super V> isNewer) {
        return update(items, valueGetter.then(idGetter), valueGetter, isNewer);
    }
}
