package net.femtoparsec.tools.state;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Map;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 * @author Bastien Aracil
 */
@EqualsAndHashCode(callSuper = true)
public class FPCMapState<K, V> extends FPCMapStateBase<K,V, FPCMapState<K,V>> {

    @SuppressWarnings("rawtypes")
    public static final FPCMapState EMPTY = new FPCMapState<>(Map.of());

    @SuppressWarnings("unchecked")
    public static <K,V> FPCMapState<K,V> empty() {
        return EMPTY;
    }

    public FPCMapState(@NonNull Map<K, V> content) {
        super(content, FPCMapState::new);
    }

    @Override
    protected FPCMapState<K, V> getThis() {
        return this;
    }
}
