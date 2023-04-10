package net.femtoparsec.tools.state;

import fpc.tools.lang.MapTool;
import fpc.tools.state.RemoteData;
import fpc.tools.state.RemoteMap;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Map;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 * @author Bastien Aracil
 */
@EqualsAndHashCode(callSuper = true)
public class FPCRemoteMap<K, V> extends FPCRemoteMapBase<K,V, FPCRemoteMap<K,V>> implements RemoteMap<K,V> {

    @SuppressWarnings("rawtypes")
    public static final FPCRemoteMap EMPTY = new FPCRemoteMap<>(Map.of());

    @SuppressWarnings("unchecked")
    public static <K,V> FPCRemoteMap<K,V> empty() {
        return EMPTY;
    }

    @NonNull
    public static <K,V> FPCRemoteMap<K,V> create(@NonNull Map<K,V> data) {
        return new FPCRemoteMap<>(MapTool.mapValue(data, RemoteData::loaded));
    }

    public FPCRemoteMap(@NonNull Map<K, RemoteData<V>> content) {
        super(content, FPCRemoteMap::new);
    }

    @Override
    protected FPCRemoteMap<K, V> getThis() {
        return this;
    }
}
