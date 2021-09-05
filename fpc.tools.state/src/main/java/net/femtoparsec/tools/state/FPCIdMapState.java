package net.femtoparsec.tools.state;

import com.google.common.collect.ImmutableMap;
import fpc.tools.fp.Function1;
import fpc.tools.state.IdMapState;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Collection;
import java.util.Comparator;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 *
 * @author Bastien Aracil
 */
@EqualsAndHashCode(exclude = {"idGetter"},callSuper = true)
public class FPCIdMapState<I, V> extends FPCMapStateBase<I, V, FPCIdMapState<I, V>> implements IdMapState<I, V> {

    @SuppressWarnings("unchecked")
    public static <I, V> FPCIdMapState<I, V> empty(@NonNull Function1<? super V, ? extends I> idGetter) {
        return new FPCIdMapState<>(ImmutableMap.of(), idGetter);
    }

    @NonNull
    private final Function1<? super V, ? extends I> idGetter;

    public FPCIdMapState(
            @NonNull ImmutableMap<I, V> content,
            @NonNull Function1<? super V, ? extends I> idGetter) {
        super(content, c -> new FPCIdMapState<>(c, idGetter));
        this.idGetter = idGetter;
    }

    @Override
    protected FPCIdMapState<I, V> getThis() {
        return this;
    }

    @Override
    public IdMapState<I, V> put(@NonNull V value) {
        return put(idGetter.f(value), value);
    }

    @Override
    public IdMapState<I, V> replace(@NonNull V value) {
        return replace(idGetter.f(value), value);
    }

    @Override
    public IdMapState<I, V> updateValue(@NonNull V value, @NonNull Comparator<? super V> isNewer) {
        return update(idGetter.f(value), value, isNewer);
    }

    @Override
    public IdMapState<I, V> updateValues(@NonNull Collection<V> newValues, @NonNull Comparator<? super V> isNewer) {
        return update(newValues, idGetter, isNewer);
    }

    @Override
    public <T> IdMapState<I, V> updateItems(@NonNull Collection<T> items,
                                            @NonNull Function1<? super T, ? extends V> valueGetter,
                                            @NonNull Comparator<? super V> isNewer) {
        return update(items, valueGetter.then(idGetter), valueGetter, isNewer);
    }
}
