package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.lang.MapTool;
import lombok.NonNull;
import net.femtoparsec.tools.state.FPCIdVRemoteMap;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 * @author Bastien Aracil
 */
public interface IdVRemoteMap<I, V extends Versioned> extends fpc.tools.state.RemoteMapBase<I,V> {

    static <I, V extends Versioned & Identified<I>> @NonNull IdVRemoteMap<I, V> empty() {
        return empty(Identified::getIdentification);
    }

    static <I, V extends Versioned & Identified<I>> @NonNull IdVRemoteMap<I, V> create(@NonNull Map<I,V> content) {
        return create(content, Identified::getIdentification);
    }

    static <I, V extends Versioned> @NonNull IdVRemoteMap<I, V> empty(@NonNull Function1<? super V, ? extends I> idGetter) {
        return create(Map.of(),idGetter);
    }

    static <I, V extends Versioned> @NonNull IdVRemoteMap<I, V> create(@NonNull Map<I,V> content, @NonNull Function1<? super V, ? extends I> idGetter) {
        return new FPCIdVRemoteMap<>(MapTool.mapValue(content,RemoteData::loaded),idGetter);
    }


    /**
     * @param predicate a predicate that returns true for the keys that must be to remove
     * @return a new {@link IdVRemoteMap} with the key matching the predicate remove
     */
    @NonNull
    IdVRemoteMap<I,V> removeIfKeyMatches(@NonNull Predicate<? super I> predicate);

    /**
     * @param value the value to put
     * @return a new MapState with the same values of this plus the provided entry.
     */
    @NonNull
    IdVRemoteMap<I, V> put(@NonNull V value);

    /**
     * @param value the value to replace
     * @return this if this does not contain the provided key, otherwise a new MapState with the same values of
     * this plus the provided entry
     */
    @NonNull
    IdVRemoteMap<I, V> replace(@NonNull V value);

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
     * @return this if this contains the key and the associated value should not be updated ]
     * accordingly to the provided predicate, otherwise a new {@link IdVRemoteMap} with the provided
     * entry (key,value) added.
     *
     */
    @NonNull
    IdVRemoteMap<I, V> updateValue(@NonNull V value);

    /**
     * Same as {@link #updateValue(Versioned)}  but for several values at once
     */
    @NonNull
    IdVRemoteMap<I, V> updateValues(@NonNull Collection<V> newValues);

    /**
     * Same as {@link #updateValue(Versioned)}} but for several values at once.
     *
     * @param <T> the type of the items
     * @param items the items used for the update
     * @param valueGetter a function to get the value from one item
     * @return a new updated {@link IdVRemoteMap}
     */
    @NonNull
    <T> IdVRemoteMap<I, V> updateItems(@NonNull Collection<T> items,
                                       @NonNull Function1<? super T, ? extends V> valueGetter);


}
