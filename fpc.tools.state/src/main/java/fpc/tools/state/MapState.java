package fpc.tools.state;

import fpc.tools.fp.Consumer2;
import fpc.tools.fp.Function1;
import fpc.tools.fp.UnaryOperator1;
import lombok.NonNull;
import net.femtoparsec.tools.state.FPCMapState;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 * @author Bastien Aracil
 */
public interface MapState<K, V> extends fpc.tools.state.MapStateBase<K,V> {

    static <K, V> MapState<K, V> empty() {
        return FPCMapState.empty();
    }

    static <K,V> MapState<K,V> with(@NonNull Map<K, V> content) {
        return new FPCMapState<>(content);
    }

    @NonNull Map<K,V> getContent();

    MapState<K,V> remove(@NonNull K key);

    /**
     * @param predicate a predicate that returns true for the keys that must be to remove
     * @return a new {@link MapState} with the key matching the predicate remove
     */
    @NonNull
    MapState<K,V> removeIfKeyMatches(@NonNull Predicate<? super K> predicate);

    /**
     * @param key the key of the entry to add
     * @param value the value of the entry to add
     * @return a new MapState with the same values of this plus the provided entry.
     */
    @NonNull
    MapState<K, V> put(@NonNull K key, @NonNull V value);

    @NonNull
    MapState<K,V> onlyKeepTheKeys(@NonNull Collection<K> keys);

    /**
     * @param keys the keys of the entry to add
     * @param valueGetter the function to use on the key to get the values
     * @return a new MapState with the same values of this plus the provided entry.
     */
    @NonNull
    MapState<K, V> putAll(@NonNull Collection<K> keys, @NonNull Function1<? super K, ? extends V> valueGetter);

    @NonNull
    default MapState<K, V> putAll(@NonNull Collection<K> keys, @NonNull V value) {
        return putAll(keys,k -> value);
    }


    /**
     * @param key the key of the entry to replace
     * @param value the value of the entry to replace
     * @return this if this does not contain the provided key, otherwise a new MapState with the same values of
     * this plus the provided entry
     */
    @NonNull
    MapState<K, V> replace(@NonNull K key, @NonNull V value);

    @NonNull
    MapState<K, V> replace(@NonNull K key, @NonNull UnaryOperator1<V> mutator);

    /**
     * <p>
     * The predicate is used as follow :<br>
     *
     * <code>
     *     shouldUpdate(current,value);
     * </code>
     * </p>
     * where <code>current</code> is the current value in the map associated with the provided key, and <code>value</code>
     * is the value provided by the caller.
     *
     * @param key the key of the entry to update
     * @param value the value of the entry to update
     * @param isNewer a comparator to test which of the two values is newer (up to date)
     * @return this if this contains the key and the associated value should not be updated ]
     * accordingly to the provided predicate, otherwise a new {@link MapState} with the provided
     * entry (key,value) added.
     *
     */
    MapState<K, V> update(@NonNull K key, @NonNull V value,
                            @NonNull Comparator<? super V> isNewer);

    /**
     * Same as {@link #update(Object, Object, Comparator)} but for several values at once
     */
    MapState<K, V> update(@NonNull Map<K, V> newValues,
                            @NonNull Comparator<? super V> isNewer);

    /**
     * Same as {@link #update(Object, Object, Comparator)} but for several values at once
     */
    MapState<K, V> update(@NonNull Collection<V> newValues,
                            @NonNull Function1<? super V, ? extends K> keyGetter,
                            @NonNull Comparator<? super V> isNewer);

    /**
     * Same as {@link #update(Object, Object, Comparator)} but for several values at once.
     *
     * @param items the items used for the update
     * @param keyGetter a function to get the keys from one item
     * @param valueGetter a function to get the value from one item
     * @param isNewer a comparator to test which of the two values is newer (up to date)
     * @param <T> the type of the items
     * @return a new updated {@link MapState}
     */
    <T> MapState<K, V> update(@NonNull Collection<T> items,
                                @NonNull Function1<? super T, ? extends K> keyGetter,
                                @NonNull Function1<? super T, ? extends V> valueGetter,
                                @NonNull Comparator<? super V> isNewer);

    default void forEach(@NonNull Consumer2<? super K, ? super V> action) {
        getContent().forEach(action);
    }

}
