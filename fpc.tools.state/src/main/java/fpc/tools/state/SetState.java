package fpc.tools.state;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Bastien Aracil
 */
@RequiredArgsConstructor
@EqualsAndHashCode(of = "content")
public class SetState<V> implements Iterable<V> {

    private static final SetState EMPTY = new SetState<>(Set.of());

    @SuppressWarnings("unchecked")
    public static <V> SetState<V> empty() {
        return EMPTY;
    }

    @Getter
    private final Set<V> content;

    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean contains(Object value) {
        return content.contains(value);
    }

    public int size() {
        return content.size();
    }

    @Override
    public Iterator<V> iterator() {
        return content.iterator();
    }

    public Stream<V> stream() {
        return content.stream();
    }

    public SetState<V> removeIf(Predicate<? super V> test) {
        return new SetState<>(content.stream().filter(test.negate()).collect(Collectors.toSet()));
    }

    public SetState<V> remove(V value) {
        if (content.contains(value)) {
            return removeIf(value::equals);
        }
        return this;
    }

    public SetState<V> add(V value) {
        return toBuilder().add(value).build();
    }

    public Builder<V> toBuilder() {
        return new Builder<>(this);
    }

    public static <V> Builder<V> builder() {
        return new Builder<>(empty());
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    @RequiredArgsConstructor
    public static class Builder<V> {

        private final SetState<V> reference;

        private final Set<Object> removedValues = new HashSet<>();

        private final Set<V> addedValues = new HashSet<>();

        public Builder<V> addAll(Collection<V> values) {
            addedValues.addAll(values);
            removedValues.removeAll(values);
            return this;
        }

        public Builder<V> add(V value) {
            addedValues.add(value);
            removedValues.remove(value);
            return this;
        }

        @SuppressWarnings("SuspiciousMethodCalls")
        public Builder<V> remove(Object value) {
            addedValues.remove(value);
            removedValues.add(value);
            return this;
        }

        public Builder<V> removeAll(Collection<V> values) {
            addedValues.removeAll(values);
            removedValues.addAll(values);
            return this;
        }

        public SetState<V> build() {
            final Set<V> content = Stream.concat(
                    reference.content.stream().filter(this::isNotRemovedNorAdded),
                    addedValues.stream()
            ).collect(Collectors.toSet());
            return new SetState<>(content);
        }

        private boolean isNotRemovedNorAdded(V value) {
            return !removedValues.contains(value) && !addedValues.contains(value);
        }

    }


}
