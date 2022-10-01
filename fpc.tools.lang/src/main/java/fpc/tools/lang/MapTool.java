package fpc.tools.lang;

import com.google.common.collect.ImmutableMap;
import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import fpc.tools.fp.Tuple2;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author bastien.a
 */
@SuppressWarnings("unused")
public class MapTool {

    public static <K,V> Optional<Map.Entry<K,V>> getFirst(Map<K,V> map) {
        return CollectionTool.getFirst(map.entrySet());
    }

    public static <K,V> Optional<V> getFirstValue(Map<K,V> map) {
        return getFirst(map).map(Map.Entry::getValue);
    }

    public static <K,V> Optional<K> getFirstKey(Map<K,V> map) {
        return getFirst(map).map(Map.Entry::getKey);
    }

    public static <A,K,V> Collector<A,?,ImmutableMap<K,V>> collector(Function<? super A, ? extends K> keyGetter, Function<? super A, ? extends V> valueGetter) {
        return ImmutableMap.toImmutableMap(keyGetter, valueGetter);
    }

    public static <A,K> Collector<A,?,ImmutableMap<K,A>> collector(Function<? super A, ? extends K> keyGetter) {
        return collector(keyGetter, Function1.identity());
    }

    @Deprecated
    public static <A,K> Collector<Tuple2<K,A>, ?, ImmutableMap<K,A>> value2Collector() {
        return tuple2Collector();
    }

    @Deprecated
    public static <A,K,T> Collector<Tuple2<K,A>, ?, ImmutableMap<K,T>> simpleValue2Collector(Function<? super A, ? extends T> valueGetter) {
        return simpleTuple2Collector(valueGetter);
    }

    @Deprecated
    public static <A,K,T> Collector<Tuple2<K,A>, ?, ImmutableMap<K,T>> value2Collector(Function<? super Tuple2<K,A>, ? extends T> valueGetter) {
        return tuple2Collector(valueGetter);
    }

    public static <A,K> Collector<Tuple2<K,A>, ?, ImmutableMap<K,A>> tuple2Collector() {
        return ImmutableMap.toImmutableMap(Tuple2::v1, Tuple2::v2);
    }

    public static <A,K,T> Collector<Tuple2<K,A>, ?, ImmutableMap<K,T>> simpleTuple2Collector(Function<? super A, ? extends T> valueGetter) {
        return ImmutableMap.toImmutableMap(Tuple2::v1, v -> valueGetter.apply(v.v2()));
    }

    public static <A,K,T> Collector<Tuple2<K,A>, ?, ImmutableMap<K,T>> tuple2Collector(Function<? super Tuple2<K,A>, ? extends T> valueGetter) {
        return ImmutableMap.toImmutableMap(Tuple2::v1, valueGetter::apply);
    }

    public static <A,K,V> Function1<Stream<? extends A>, ImmutableMap<K,V>> collect(Function<A, ? extends K> keyGetter, Function<A, ? extends V> valueGetter) {
        return s -> s.collect(collector(keyGetter, valueGetter));
    }

    public static <A,K> Function1<Stream<? extends A>, ImmutableMap<K,A>> collect(Function<A, ? extends K> keyGetter) {
        return s -> s.collect(collector(keyGetter, Function1.identity()));
    }

    public static <A,B,Z> Stream<Z> toStream(ImmutableMap<A,B> map, BiFunction<? super A, ? super B, ? extends Z> function) {
        return map.entrySet().stream().map(e -> function.apply(e.getKey(), e.getValue()));
    }


    public static <K,V> Function2<ImmutableMap<K,V>, Predicate<? super K>, ImmutableMap<K,V>> filter() {
        return (map,test) -> map.keySet().stream().filter(test::test).collect(collector(k -> k, map::get));
    }

    public static <K,V> FilteredMap<K,V> filter(ImmutableMap<K,V> target) {
        return new FilteredMap<>(target);
    }


    public static <K,V> Function1<ImmutableMap<K,V>, ImmutableMap<K,V>> filterWith(Predicate<? super K> predicate) {
        return MapTool.<K,V>filter().f2(predicate);
    }

    public static <K,V> Optional<V> get(Map<K,V> map, K key) {
        return Optional.ofNullable(map.get(key));
    }


    public static <K,V> Function1<Map<K,V>, Optional<V>> getter(K key) {
        return m -> get(m,key);
    }


    public static <K1,V1,K2,V2> Function1<ImmutableMap<? extends K1,? extends V1>, ImmutableMap<K2,V2>> remapper(BiFunction<K1,V1,? extends K2> keyGetter, BiFunction<K1,V1, ? extends V2> valueGetter) {
        return m -> m.entrySet().stream().collect(MapTool.collector(e -> keyGetter.apply(e.getKey(), e.getValue()), e -> valueGetter.apply(e.getKey(), e.getValue())));
    }

    public static <K1,K2,V> Function<ImmutableMap<? extends K1, ? extends V>, ImmutableMap<K2, V>> remapper(Function<K1,? extends K2> keyMapper) {
        return MapTool.remapper((k, v) -> keyMapper.apply(k), (k, v) -> v);
    }

    public static <K,V,T> ImmutableMap<K,T> mapValue(Map<K,V> input, Function<? super V, ? extends T> mapper) {
        final ImmutableMap.Builder<K,T> builder = ImmutableMap.builder();
        input.forEach((k,v) -> builder.put(k,mapper.apply(v)));
        return builder.build();
    }

    public static <K,V,T> Function<Map<K,V>, ImmutableMap<K,T>> valueMapper(Function<? super V, ? extends T> mapper) {
        return m -> mapValue(m,mapper);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FilteredMap<K,V> implements Function1<Predicate<? super K>, ImmutableMap<K,V>> {

        @NonNull
        private final ImmutableMap<K,V> target;

        @NonNull
        @Override
        public ImmutableMap<K,V> apply(@NonNull Predicate<? super K> predicate) {
            return target.keySet().stream().filter(predicate).collect(collector(k -> k, target::get));
        }

        public ImmutableMap<K,V> with(Predicate<? super K> predicate) {
            return apply(predicate);
        }

    }


}
