package net.femtoparsec.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.state.IdVRemoteMap;
import fpc.tools.state.Identified;
import fpc.tools.state.RemoteData;
import fpc.tools.state.Versioned;
import lombok.NonNull;

import java.util.Collection;
import java.util.Map;

public class FPCIdVRemoteMap<K,V extends Versioned> extends FPCRemoteMapBase<K,V, FPCIdVRemoteMap<K,V>> implements IdVRemoteMap<K,V> {

    @NonNull
    public static <K,V extends Versioned> FPCIdVRemoteMap<K,V> empty(@NonNull Function1<? super V, ? extends K> idGetter) {
        return new FPCIdVRemoteMap<>(Map.of(),idGetter);
    }

    @NonNull
    public static <K,V extends Versioned & Identified<K>> FPCIdVRemoteMap<K,V> empty() {
        return empty(Identified::getIdentification);
    }

    @NonNull
    private final Function1<? super V, ? extends K> idGetter;

    public FPCIdVRemoteMap(
            @NonNull Map<K, RemoteData<V>> content,
            @NonNull Function1<? super V, ? extends K> idGetter) {
        super(content, c-> new FPCIdVRemoteMap<>(c,idGetter));
        this.idGetter = idGetter;
    }

    @Override
    protected FPCIdVRemoteMap<K, V> getThis() {
        return this;
    }

    @Override
    public @NonNull FPCIdVRemoteMap<K, V> put(@NonNull V value) {
        return put(idGetter.apply(value),value);
    }

    @Override
    public @NonNull FPCIdVRemoteMap<K, V> replace(@NonNull V value) {
        return replace(idGetter.apply(value),value);
    }

    @Override
    public @NonNull FPCIdVRemoteMap<K, V> updateValue(@NonNull V value) {
        return update(idGetter.apply(value),value,Versioned.VERSIONED_COMPARATOR);
    }

    @Override
    public @NonNull FPCIdVRemoteMap<K, V> updateValues(@NonNull Collection<V> newValues) {
        return update(newValues,idGetter,Versioned.VERSIONED_COMPARATOR);
    }

    @Override
    public <T> @NonNull FPCIdVRemoteMap<K, V> updateItems(@NonNull Collection<T> items,
                                                          @NonNull Function1<? super T, ? extends V> valueGetter) {
        return update(items,valueGetter.then(idGetter),valueGetter,Versioned.VERSIONED_COMPARATOR);
    }
}
