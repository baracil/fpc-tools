package fpc.tools.state;

import fpc.tools.lang.ListTool;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Bastien Aracil
 */
@RequiredArgsConstructor
@EqualsAndHashCode(of = "content")
public class ListState<V> implements Iterable<V> {

    private static final ListState EMPTY = new ListState<>(List.of());

    @SuppressWarnings("unchecked")
    public static <V> ListState<V> empty() {
        return EMPTY;
    }

    public static <V> ListState<V> of(List<V> content) {
        return new ListState<>(content);
    }

    private final List<V> content;


    public int size() {
        return content.size();
    }

    public V get(int index) {
        return content.get(index);
    }


    public <U> ListState<U> map(Function<? super V, ? extends U> mapper) {
        return new ListState<>(ListTool.map(content, mapper));
    }

    public ListState<V> replace(Predicate<? super V> filter,V newValue) {
        return map(v -> filter.test(v)?newValue:v);
    }

    public ListState<V> replace(Predicate<? super V> filter,Function<? super V, ? extends V> mapper) {
        if (content.stream().noneMatch(filter)) {
            return this;
        }
        return new ListState<>(ListTool.map(content, v -> filter.test(v)?mapper.apply(v):v));
    }


    public OptionalInt indexOf(Object object) {
        return indexOf(object::equals);
    }

    public OptionalInt indexOf(Predicate<? super V> test) {
        return IntStream.range(0, size()).filter(i -> test.test(content.get(i))).findFirst();
    }

    public Optional<V> find(int index) {
        if (index < 0 || index >= size()) {
            return Optional.empty();
        }
        return Optional.of(get(index));
    }

    public Optional<V> find(Predicate<? super V> test) {
        return content.stream().filter(test).findFirst();
    }


    @Override
    public Iterator<V> iterator() {
        return content.iterator();
    }

    public Stream<V> stream() {
        return content.stream();
    }

    public ListState<V> removeIf(Predicate<? super V> test) {
        return new ListState<>(content.stream().filter(test.negate()).toList());
    }

    public ListState<V> remove(Object value) {
        return removeIf(value::equals);
    }

    public ListState<V> add(V value) {
        return toBuilder().add(value).build();
    }


    public static <V> Builder<V> builder() {
        return new Builder<>(empty());
    }

    public Builder<V> toBuilder() {
        return new Builder<>(this);
    }

    public static class Builder<V> {

        private final List<V> content;

        public Builder(ListState<V> listState) {
            this.content = new ArrayList<>(listState.content);
        }

        public Builder<V> add(V value) {
            content.add(value);
            return this;
        }

        public Builder<V> add(int index, V value) {
            content.add(index, value);
            return this;
        }

        public Builder<V> remove(V value) {
            content.remove(value);
            return this;
        }

        public Builder<V> addAll(Collection<? extends V> values) {
            content.addAll(values);
            return this;
        }
        public Builder<V> addAll(int index, Collection<? extends V> values) {
            content.addAll(index, values);
            return this;
        }

        public ListState<V> build() {
            return new ListState<>(List.copyOf(content));
        }
    }
}
