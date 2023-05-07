package fpc.tools.state;

import fpc.tools.fp.Function1;
import lombok.NonNull;
import net.femtoparsec.tools.state.FPCIdMapState;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 * @author Bastien Aracil
 */
public interface IdMapState<I, V> extends fpc.tools.state.MapStateBase<I,V> {

    static <I, V> IdMapState<I, V> empty(Function1<? super V, ? extends I> idGetter) {
        return FPCIdMapState.empty(idGetter);
    }

    static <I, V extends Identified<I>> IdMapState<I, V> empty() {
        return FPCIdMapState.empty(Identified::getIdentification);
    }

    static <I, V extends Identified<I>> IdMapState<I, V> with(Map<I,V> content) {
        return new FPCIdMapState<>(content, Identified::getIdentification);
    }

    IdMapState<I,V> remove(I key);

    /**
     * @param predicate a predicate that returns true for the keys that must be to remove
     * @return a new {@link IdMapState} with the key matching the predicate remove
     */
    IdMapState<I,V> removeIfKeyMatches(Predicate<? super I> predicate);

    /**
     * @param value the value to put
     * @return a new MapState with the same values of this plus the provided entry.
     */
    IdMapState<I, V> put(V value);

    /**
     * @param value the value to replace
     * @return this if this does not contain the provided key, otherwise a new MapState with the same values of
     * this plus the provided entry
     */
    IdMapState<I, V> replace(V value);

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
     * accordingly to the provided predicate, otherwise a new {@link IdMapState} with the provided
     * entry (key,value) added.
     *
     */
    IdMapState<I, V> updateValue(V value, Comparator<? super V> isNewer);

    /**
     * Same as {@link #updateValue(Object, Comparator)} but for several values at once
     */
    IdMapState<I, V> updateValues(Collection<V> newValues,
                                  Comparator<? super V> isNewer);

    /**
     * Same as {@link #updateValue(Object, Comparator)} but for several values at once.
     *
     * @param items the items used for the update
     * @param valueGetter a function to get the value from one item
     * @param isNewer a comparator to test which of the two values is newer (up to date)
     * @param <T> the type of the items
     * @return a new updated {@link IdMapState}
     */
    <T> IdMapState<I, V> updateItems(Collection<T> items,
                                     Function1<? super T, ? extends V> valueGetter,
                                     Comparator<? super V> isNewer);


}
