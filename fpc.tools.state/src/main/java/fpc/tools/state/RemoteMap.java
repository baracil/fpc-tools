package fpc.tools.state;

import fpc.tools.fp.Function1;
import net.femtoparsec.tools.state.FPCMapState;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

public interface RemoteMap<K, V> extends fpc.tools.state.RemoteMapBase<K, V> {

  static <K, V> fpc.tools.state.MapState<K, V> empty() {
    return FPCMapState.empty();
  }

  /**
   * @param predicate a predicate that returns true for the keys that must be to remove
   * @return a new {@link fpc.tools.state.MapState} with the key matching the predicate remove
   */
  RemoteMap<K, V> removeIfKeyMatches(Predicate<? super K> predicate);

  /**
   * @param key   the key of the entry to add
   * @param value the value of the entry to add
   * @return a new MapState with the same values of this plus the provided entry.
   */
  RemoteMap<K, V> put(K key, V value);

  /**
   * @param key   the key of the entry to replace
   * @param value the value of the entry to replace
   * @return this if this does not contain the provided key, otherwise a new MapState with the same values of
   * this plus the provided entry
   */
  RemoteMap<K, V> replace(K key, V value);

  /**
   * <p>
   * The predicate is used as follow :<br>
   *
   * <code>
   * shouldUpdate(current,value);
   * </code>
   * </p>
   * where <code>current</code> is the current value in the map associated with the provided key, and <code>value</code>
   * is the value provided by the caller.
   *
   * @param key     the key of the entry to update
   * @param value   the value of the entry to update
   * @param isNewer a comparator to test which of the two values is newer (up to date)
   * @return this if this contains the key and the associated value should not be updated ]
   * accordingly to the provided predicate, otherwise a new {@link fpc.tools.state.MapState} with the provided
   * entry (key,value) added.
   */
  RemoteMap<K, V> update(K key, V value,
                         Comparator<? super V> isNewer);

  /**
   * Same as {@link #update(Object, Object, Comparator)} but for several values at once
   */
  RemoteMap<K, V> update(Map<K, V> newValues,
                         Comparator<? super V> isNewer);

  /**
   * Same as {@link #update(Object, Object, Comparator)} but for several values at once
   */
  RemoteMap<K, V> update(Collection<V> newValues,
                         Function1<? super V, ? extends K> keyGetter,
                         Comparator<? super V> isNewer);

  /**
   * Same as {@link #update(Object, Object, Comparator)} but for several values at once.
   *
   * @param items       the items used for the update
   * @param keyGetter   a function to get the keys from one item
   * @param valueGetter a function to get the value from one item
   * @param isNewer     a comparator to test which of the two values is newer (up to date)
   * @param <T>         the type of the items
   * @return a new updated {@link fpc.tools.state.MapState}
   */
  <T> RemoteMap<K, V> update(Collection<T> items,
                             Function1<? super T, ? extends K> keyGetter,
                             Function1<? super T, ? extends V> valueGetter,
                             Comparator<? super V> isNewer);


}
