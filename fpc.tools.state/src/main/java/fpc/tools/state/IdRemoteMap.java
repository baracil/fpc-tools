package fpc.tools.state;

import fpc.tools.fp.Function1;
import net.femtoparsec.tools.state.FPCIdRemoteMap;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 * @author Bastien Aracil
 */
public interface IdRemoteMap<I, V> extends fpc.tools.state.RemoteMapBase<I,V> {

    static <I, V> IdRemoteMap<I, V> empty(Function1<? super V, ? extends I> idGetter) {
        return FPCIdRemoteMap.empty(idGetter);
    }

    static <I, V extends Identified<I>> IdRemoteMap<I, V> empty() {
        return FPCIdRemoteMap.empty(Identified::getIdentification);
    }

    IdRemoteMap<I,V> remove(I key);

    /**
     * @param predicate a predicate that returns true for the keys that must be to remove
     * @return a new {@link IdRemoteMap} with the key matching the predicate remove
     */
    IdRemoteMap<I,V> removeIfKeyMatches(Predicate<? super I> predicate);

    /**
     * @param value the value to put
     * @return a new MapState with the same values of this plus the provided entry.
     */
    IdRemoteMap<I, V> put(V value);

    /**
     * @param value the value to replace
     * @return this if this does not contain the provided key, otherwise a new MapState with the same values of
     * this plus the provided entry
     */
    IdRemoteMap<I, V> replace(V value);

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
     * @param value the value of the entry to update
     * @param isNewer a comparator to test which of the two values is newer (up to date)
     * @return this if this contains the key and the associated value should not be updated ]
     * accordingly to the provided predicate, otherwise a new {@link IdRemoteMap} with the provided
     * entry (key,value) added.
     *
     */
    IdRemoteMap<I, V> updateValue(V value, Comparator<? super V> isNewer);

    /**
     * Same as {@link #updateValue(Object, Comparator)} but for several values at once
     */
    IdRemoteMap<I, V> updateValues(Collection<V> newValues,
                                   Comparator<? super V> isNewer);

    /**
     * Same as {@link #updateValue(Object, Comparator)} but for several values at once.
     *
     * @param items the items used for the update
     * @param valueGetter a function to get the value from one item
     * @param isNewer a comparator to test which of the two values is newer (up to date)
     * @param <T> the type of the items
     * @return a new updated {@link IdRemoteMap}
     */
    <T> IdRemoteMap<I, V> updateItems(Collection<T> items,
                                      Function1<? super T, ? extends V> valueGetter,
                                      Comparator<? super V> isNewer);


}
