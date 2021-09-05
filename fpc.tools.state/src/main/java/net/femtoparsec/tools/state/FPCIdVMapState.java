package net.femtoparsec.tools.state;

import com.google.common.collect.ImmutableMap;
import fpc.tools.fp.Function1;
import fpc.tools.state.IdVMapState;
import fpc.tools.state.Versioned;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Collection;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 *
 * @author Bastien Aracil
 */
@EqualsAndHashCode(exclude = {"idGetter"},callSuper = true)
public class FPCIdVMapState<I, V extends Versioned> extends FPCMapStateBase<I, V, FPCIdVMapState<I, V>> implements IdVMapState<I, V> {

    @NonNull
    private final Function1<? super V, ? extends I> idGetter;

    public FPCIdVMapState(
            @NonNull ImmutableMap<I, V> content,
            @NonNull Function1<? super V, ? extends I> idGetter) {
        super(content, c -> new FPCIdVMapState<>(c, idGetter));
        this.idGetter = idGetter;
    }

    @Override
    protected FPCIdVMapState<I, V> getThis() {
        return this;
    }

    @Override
    public @NonNull IdVMapState<I, V> put(@NonNull V value) {
        return put(idGetter.f(value), value);
    }

    @Override
    public @NonNull IdVMapState<I, V> replace(@NonNull V value) {
        return replace(idGetter.f(value), value);
    }

    @Override
    public @NonNull IdVMapState<I, V> updateValue(@NonNull V value) {
        return update(idGetter.f(value), value, Versioned.VERSIONED_COMPARATOR);
    }

    @Override
    public @NonNull IdVMapState<I, V> updateValues(@NonNull Collection<V> newValues) {
        return update(newValues, idGetter, Versioned.VERSIONED_COMPARATOR);
    }

    @Override
    public @NonNull <T> IdVMapState<I, V> updateItems(@NonNull Collection<T> items,
                                                      @NonNull Function1<? super T, ? extends V> valueGetter) {
        return update(items, valueGetter.then(idGetter), valueGetter, Versioned.VERSIONED_COMPARATOR);
    }
}
