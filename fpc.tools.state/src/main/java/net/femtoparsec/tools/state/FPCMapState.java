package net.femtoparsec.tools.state;

import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 * @author Bastien Aracil
 */
@EqualsAndHashCode(callSuper = true)
public class FPCMapState<K, V> extends FPCMapStateBase<K,V, FPCMapState<K,V>> {

    @SuppressWarnings("rawtypes")
    public static final FPCMapState EMPTY = new FPCMapState<>(ImmutableMap.of());

    @SuppressWarnings("unchecked")
    public static <K,V> FPCMapState<K,V> empty() {
        return EMPTY;
    }

    public FPCMapState(@NonNull ImmutableMap<K, V> content) {
        super(content, FPCMapState::new);
    }

    @Override
    protected FPCMapState<K, V> getThis() {
        return this;
    }
}
