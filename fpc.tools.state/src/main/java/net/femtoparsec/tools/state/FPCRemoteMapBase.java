package net.femtoparsec.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.lang.MapTool;
import fpc.tools.state.RemoteData;
import fpc.tools.state.RemoteMap;
import lombok.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 * @author Bastien Aracil
 */
@RequiredArgsConstructor
@EqualsAndHashCode(of = "content")
public abstract class FPCRemoteMapBase<K, V, M extends FPCRemoteMapBase<K,V,M>> implements RemoteMap<K,V> {

    /**
     * The content of this map, not directly accessible
     */
    @Getter(AccessLevel.PROTECTED)
    private final Map<K, RemoteData<V>> content;

    private final Function1<? super Map<K,RemoteData<V>>,? extends M> factory;

    /**
     * @return the size of this map
     */
    public int size() {
        return content.size();
    }

    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }

    /**
     * @param key the key of the value to get
     * @return an optional containing the value if it exists, an empty optional otherwise
     */
    public Optional<RemoteData<V>> get(K key) {
        return Optional.ofNullable(content.get(key));
    }

    /**
     * @return the set of the entries of this map
     */
    @Override
    public Set<Map.Entry<K, RemoteData<V>>> entrySet() {
        return content.entrySet();
    }

    /**
     * @return the set of the keys of this map
     */
    public Set<K> keySet() {
        return content.keySet();
    }

    /**
     * @return the collection of the values of this map
     */
    public Collection<RemoteData<V>> values() {
        return content.values();
    }


    public M remove(K key) {
        if (content.containsKey(key)) {
            return removeIfKeyMatches(key::equals);
        } else {
            return getThis();
        }
    }

    /**
     * @param predicate a predicate that returns true for the keys that must be to remove
     * @return a new {@link FPCRemoteMapBase} with the key matching the predicate remove
     */
    public M removeIfKeyMatches(Predicate<? super K> predicate) {
        return removeIfEntryMatches(e -> predicate.test(e.getKey()));
    }

    /**
     * @param predicate a predicate that returns true for the entries that must be to remove.
     * @return a new {@link FPCRemoteMapBase} with the entries matching the predicate remove
     */
    private M removeIfEntryMatches(Predicate<Map.Entry<K, RemoteData<V>>> predicate) {
        final Map<K, RemoteData<V>> newContent = content.entrySet()
                                                     .stream()
                                                     .filter(predicate.negate())
                                                     .collect(MapTool.collector(Map.Entry::getKey, Map.Entry::getValue));
        return factory.apply(newContent);
    }

    /**
     * @param key the key of the entry to add
     * @param value the value of the entry to add
     * @return a new MapState with the same values of this plus the provided entry.
     */
    public M put(K key, V value) {
        return toBuilder().put(key, value).build();
    }

    /**
     * @param key the key of the entry to replace
     * @param value the value of the entry to replace
     * @return this if this does not contain the provided key, otherwise a new MapState with the same values of
     * this plus the provided entry
     */
    public M replace(K key, V value) {
        if (!this.content.containsKey(key)) {
            return getThis();
        }
        return put(key, value);
    }

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
     * accordingly to the provided predicate, otherwise a new {@link FPCRemoteMapBase} with the provided
     * entry (key,value) added.
     *
     */
    public M update(K key, V value,
                                       Comparator<? super V> isNewer) {
        final RemoteData<V> current = content.get(key);
        final V currentValue = current == null ? null : current.getValue().orElse(null);
        if (currentValue == null || isNewer.compare(currentValue, value) < 0) {
            return put(key, value);
        }
        return getThis();
    }

    /**
     * Same as {@link #update(Object, Object, Comparator)} but for several values at once
     */
    public M update(Map<K, V> newValues,
                                       Comparator<? super V> isNewer) {
        return this.update(newValues.entrySet(), Map.Entry::getKey, Map.Entry::getValue, isNewer);
    }

    /**
     * Same as {@link #update(Object, Object, Comparator)} but for several values at once
     */
    public M update(Collection<V> newValues,
                                       Function1<? super V, ? extends K> keyGetter,
                                       Comparator<? super V> isNewer) {
        return this.update(newValues, keyGetter, v -> v, isNewer);

    }
    /**
     * Same as {@link #update(Object, Object, Comparator)} but for several values at once.
     *
     * @param items the items used for the update
     * @param keyGetter a function to get the keys from one item
     * @param valueGetter a function to get the value from one item
     * @param isNewer a comparator to test which of the two values is newer (up to date)
     * @param <T> the type of the items
     * @return a new updated {@link FPCRemoteMapBase}
     */
    public <T> M update(Collection<T> items,
                                           Function1<? super T, ? extends K> keyGetter,
                                           Function1<? super T, ? extends V> valueGetter,
                                           Comparator<? super V> isNewer) {
        if (items.isEmpty()) {
            return getThis();
        }
        final Builder<K, V,M> builder = this.toBuilder();
        for (T newValue : items) {
            final K key = keyGetter.apply(newValue);
            final V value = valueGetter.apply(newValue);

            final RemoteData<V> current = content.get(key);
            final V currentValue = current == null ? null : current.getValue().orElse(null);
            if (currentValue == null || isNewer.compare(currentValue,value) < 0) {
                builder.put(key,value);
            }
        }

        return builder.build();
    }

    public boolean containsKey(K key) {
        return content.containsKey(key);
    }


    protected abstract M getThis();

    public Builder<K, V,M> toBuilder() {
        return new Builder<>(getThis(), this.content, this.factory);
    }


    @RequiredArgsConstructor
    public static class Builder<K, V,M extends FPCRemoteMapBase<K,V,M>> {

        private final M reference;

        private final Map<K, RemoteData<V>> content;

        private final Function1<? super Map<K,RemoteData<V>>,? extends M> factory;

        private final Set<K> removedKeys = new HashSet<>();

        private final Map<K, RemoteData<V>> addedValues = new HashMap<>();

        public Builder<K, V,M> remove(K key) {
            this.removedKeys.add(key);
            this.addedValues.remove(key);
            return this;
        }

        public Builder<K, V,M> put(K key, V value) {
            this.removedKeys.remove(key);
            this.addedValues.put(key, RemoteData.loaded(value));
            return this;
        }

        public M build() {
            if (removedKeys.isEmpty() && addedValues.isEmpty()) {
                return reference;
            }
            final Map<K, RemoteData<V>> content = Stream.concat(
                    this.content.entrySet().stream().filter(e -> isNotRemovedNorAdded(e.getKey())),
                    addedValues.entrySet().stream()
            ).collect(MapTool.collector(Map.Entry::getKey, Map.Entry::getValue));
            return factory.apply(content);
        }

        private boolean isNotRemovedNorAdded(K key) {
            return !removedKeys.contains(key) && !addedValues.containsKey(key);
        }
    }
}
