package net.femtoparsec.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.state.IdVRemoteMap;
import fpc.tools.state.Identified;
import fpc.tools.state.RemoteData;
import fpc.tools.state.Versioned;

import java.util.Collection;
import java.util.Map;

public class FPCIdVRemoteMap<K,V extends Versioned> extends FPCRemoteMapBase<K,V, FPCIdVRemoteMap<K,V>> implements IdVRemoteMap<K,V> {

    public static <K,V extends Versioned> FPCIdVRemoteMap<K,V> empty(Function1<? super V, ? extends K> idGetter) {
        return new FPCIdVRemoteMap<>(Map.of(),idGetter);
    }

    public static <K,V extends Versioned & Identified<K>> FPCIdVRemoteMap<K,V> empty() {
        return empty(Identified::getIdentification);
    }

    private final Function1<? super V, ? extends K> idGetter;

    public FPCIdVRemoteMap(
            Map<K, RemoteData<V>> content,
            Function1<? super V, ? extends K> idGetter) {
        super(content, c-> new FPCIdVRemoteMap<>(c,idGetter));
        this.idGetter = idGetter;
    }

    @Override
    protected FPCIdVRemoteMap<K, V> getThis() {
        return this;
    }

    @Override
    public FPCIdVRemoteMap<K, V> put(V value) {
        return put(idGetter.apply(value),value);
    }

    @Override
    public FPCIdVRemoteMap<K, V> replace(V value) {
        return replace(idGetter.apply(value),value);
    }

    @Override
    public FPCIdVRemoteMap<K, V> updateValue(V value) {
        return update(idGetter.apply(value),value,Versioned.VERSIONED_COMPARATOR);
    }

    @Override
    public FPCIdVRemoteMap<K, V> updateValues(Collection<V> newValues) {
        return update(newValues,idGetter,Versioned.VERSIONED_COMPARATOR);
    }

    @Override
    public <T> FPCIdVRemoteMap<K, V> updateItems(Collection<T> items,
                                                          Function1<? super T, ? extends V> valueGetter) {
        return update(items,valueGetter.then(idGetter),valueGetter,Versioned.VERSIONED_COMPARATOR);
    }
}
