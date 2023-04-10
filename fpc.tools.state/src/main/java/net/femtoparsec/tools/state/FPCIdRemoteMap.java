package net.femtoparsec.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.state.IdRemoteMap;
import fpc.tools.state.Identified;
import fpc.tools.state.RemoteData;
import lombok.NonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

public class FPCIdRemoteMap<K,V> extends FPCRemoteMapBase<K,V, FPCIdRemoteMap<K,V>> implements IdRemoteMap<K,V> {

    @NonNull
    public static <K,V> FPCIdRemoteMap<K,V> empty(@NonNull Function1<? super V, ? extends K> idGetter) {
        return new FPCIdRemoteMap<>(Map.of(), idGetter);
    }

    @NonNull
    public static <K,V extends Identified<K>> FPCIdRemoteMap<K,V> empty() {
        return new FPCIdRemoteMap<>(Map.of(), Identified::getIdentification);
    }

    @NonNull
    private final Function1<? super V, ? extends K> idGetter;

    public FPCIdRemoteMap(
            @NonNull Map<K, RemoteData<V>> content,
            @NonNull Function1<? super V, ? extends K> idGetter) {
        super(content, c-> new FPCIdRemoteMap<>(c,idGetter));
        this.idGetter = idGetter;
    }

    @Override
    protected FPCIdRemoteMap<K, V> getThis() {
        return this;
    }

    @Override
    public IdRemoteMap<K, V> put(@NonNull V value) {
        return put(idGetter.apply(value),value);
    }

    @Override
    public IdRemoteMap<K, V> replace(@NonNull V value) {
        return replace(idGetter.apply(value),value);
    }

    @Override
    public IdRemoteMap<K, V> updateValue(@NonNull V value, @NonNull Comparator<? super V> isNewer) {
        return update(idGetter.apply(value),value,isNewer);
    }

    @Override
    public IdRemoteMap<K, V> updateValues(@NonNull Collection<V> newValues, @NonNull Comparator<? super V> isNewer) {
        return update(newValues,idGetter,isNewer);
    }

    @Override
    public <T> IdRemoteMap<K, V> updateItems(@NonNull Collection<T> items,
                                             @NonNull Function1<? super T, ? extends V> valueGetter,
                                             @NonNull Comparator<? super V> isNewer) {
        return update(items,valueGetter.then(idGetter),valueGetter,isNewer);
    }
}
