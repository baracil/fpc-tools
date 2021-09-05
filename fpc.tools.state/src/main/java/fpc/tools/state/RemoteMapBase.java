package fpc.tools.state;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 * @author Bastien Aracil
 */
public interface RemoteMapBase<K, V> {


    /**
     * @return the size of this map
     */
    int size();

    boolean isEmpty();

    /**
     * @param key the key of the value to get
     * @return an optional containing the value if it exists, an empty optional otherwise
     */
    @NonNull
    Optional<RemoteData<V>> get(@NonNull K key);

    /**
     * @return the set of the entries of this map
     */
    @NonNull
    ImmutableSet<Map.Entry<K, RemoteData<V>>> entrySet();

    /**
     * @return the set of the keys of this map
     */
    @NonNull
    ImmutableSet<K> keySet();

    /**
     * @return the collection of the values of this map
     */
    @NonNull
    ImmutableCollection<RemoteData<V>> values();

    boolean containsKey(@NonNull K key);

}
