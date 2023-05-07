package fpc.tools.state;

import lombok.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
    Optional<RemoteData<V>> get(K key);

    /**
     * @return the set of the entries of this map
     */
    Set<Map.Entry<K, RemoteData<V>>> entrySet();

    /**
     * @return the set of the keys of this map
     */
    Set<K> keySet();

    /**
     * @return the collection of the values of this map
     */
    Collection<RemoteData<V>> values();

    boolean containsKey(K key);

}
