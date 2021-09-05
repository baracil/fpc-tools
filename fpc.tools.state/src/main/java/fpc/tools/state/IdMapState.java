package fpc.tools.state;

import com.google.common.collect.ImmutableMap;
import fpc.tools.fp.Function1;
import lombok.NonNull;
import net.femtoparsec.tools.state.FPCIdMapState;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 * @author Bastien Aracil
 */
public interface IdMapState<I, V> extends fpc.tools.state.MapStateBase<I,V> {

    static <I, V> @NonNull IdMapState<I, V> empty(@NonNull Function1<? super V, ? extends I> idGetter) {
        return FPCIdMapState.empty(idGetter);
    }

    static <I, V extends Identified<I>> @NonNull IdMapState<I, V> empty() {
        return FPCIdMapState.empty(Identified::getIdentification);
    }

    static <I, V extends Identified<I>> @NonNull IdMapState<I, V> with(@NonNull ImmutableMap<I,V> content) {
        return new FPCIdMapState<>(content, Identified::getIdentification);
    }

    @NonNull
    IdMapState<I,V> remove(@NonNull I key);

    /**
     * @param predicate a predicate that returns true for the keys that must be to remove
     * @return a new {@link IdMapState} with the key matching the predicate remove
     */
    @NonNull
    IdMapState<I,V> removeIfKeyMatches(@NonNull Predicate<? super I> predicate);

    /**
     * @param value the value to put
     * @return a new MapState with the same values of this plus the provided entry.
     */
    IdMapState<I, V> put(@NonNull V value);

    /**
     * @param value the value to replace
     * @return this if this does not contain the provided key, otherwise a new MapState with the same values of
     * this plus the provided entry
     */
    IdMapState<I, V> replace(@NonNull V value);

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
    IdMapState<I, V> updateValue(@NonNull V value, @NonNull Comparator<? super V> isNewer);

    /**
     * Same as {@link #updateValue(Object, Comparator)} but for several values at once
     */
    IdMapState<I, V> updateValues(@NonNull Collection<V> newValues,
                                  @NonNull Comparator<? super V> isNewer);

    /**
     * Same as {@link #updateValue(Object, Comparator)} but for several values at once.
     *
     * @param items the items used for the update
     * @param valueGetter a function to get the value from one item
     * @param isNewer a comparator to test which of the two values is newer (up to date)
     * @param <T> the type of the items
     * @return a new updated {@link IdMapState}
     */
    <T> IdMapState<I, V> updateItems(@NonNull Collection<T> items,
                                     @NonNull Function1<? super T, ? extends V> valueGetter,
                                     @NonNull Comparator<? super V> isNewer);


}
