package fpc.tools.lang;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import fpc.tools.fp.Tuple2;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author bastien.a
 */
@SuppressWarnings("unused")
public class MapTool {

  public static <K, V> Optional<Map.Entry<K, V>> getFirst(Map<K, V> map) {
    return CollectionTool.getFirst(map.entrySet());
  }

  public static <K, V> Optional<V> getFirstValue(Map<K, V> map) {
    return getFirst(map).map(Map.Entry::getValue);
  }

  public static <K, V> Optional<K> getFirstKey(Map<K, V> map) {
    return getFirst(map).map(Map.Entry::getKey);
  }

  public static <A, K, V> Collector<A, ?, Map<K, V>> collector(Function<? super A, ? extends K> keyGetter, Function<? super A, ? extends V> valueGetter) {
    return Collectors.toMap(keyGetter, valueGetter);
  }

  public static <A, K> Collector<A, ?, Map<K, A>> collector(Function<? super A, ? extends K> keyGetter) {
    return collector(keyGetter, Function1.identity());
  }

  public static <K,V> Map<K,V> addOneEntry(Map<K,V> reference, K key, V value) {
    final var result = MapTool.<K,V>hashMap();
    result.putAll(reference);
    result.put(key,value);
    return Collections.unmodifiableMap(result);
  }

  public static <K,V> Map<K,V> hashMap() {
    return new HashMap<>();
  }

  @Deprecated
  public static <A, K> Collector<Tuple2<K, A>, ?, Map<K, A>> value2Collector() {
    return tuple2Collector();
  }

  @Deprecated
  public static <A, K, T> Collector<Tuple2<K, A>, ?, Map<K, T>> simpleValue2Collector(Function<? super A, ? extends T> valueGetter) {
    return simpleTuple2Collector(valueGetter);
  }

  @Deprecated
  public static <A, K, T> Collector<Tuple2<K, A>, ?, Map<K, T>> value2Collector(Function<? super Tuple2<K, A>, ? extends T> valueGetter) {
    return tuple2Collector(valueGetter);
  }

  public static <A, K> Collector<Tuple2<K, A>, ?, Map<K, A>> tuple2Collector() {
    return collector(Tuple2::v1, Tuple2::v2);
  }

  public static <A, K, T> Collector<Tuple2<K, A>, ?, Map<K, T>> simpleTuple2Collector(Function<? super A, ? extends T> valueGetter) {
    return collector(Tuple2::v1, v -> valueGetter.apply(v.v2()));
  }

  public static <A, K, T> Collector<Tuple2<K, A>, ?, Map<K, T>> tuple2Collector(Function<? super Tuple2<K, A>, ? extends T> valueGetter) {
    return collector(Tuple2::v1, valueGetter::apply);
  }

  public static <A, K, V> Function1<Stream<? extends A>, Map<K, V>> collect(Function<A, ? extends K> keyGetter, Function<A, ? extends V> valueGetter) {
    return s -> s.collect(collector(keyGetter, valueGetter));
  }

  public static <A, K> Function1<Stream<? extends A>, Map<K, A>> collect(Function<A, ? extends K> keyGetter) {
    return s -> s.collect(collector(keyGetter, Function1.identity()));
  }

  public static <A, B, Z> Stream<Z> toStream(Map<A, B> map, BiFunction<? super A, ? super B, ? extends Z> function) {
    return map.entrySet().stream().map(e -> function.apply(e.getKey(), e.getValue()));
  }


  public static <K, V> Function2<Map<K, V>, Predicate<? super K>, Map<K, V>> filter() {
    return (map, test) -> map.keySet().stream().filter(test::test).collect(collector(k -> k, map::get));
  }

  public static <K, V> FilteredMap<K, V> filter(Map<K, V> target) {
    return new FilteredMap<>(target);
  }


  public static <K, V> Function1<Map<K, V>, Map<K, V>> filterWith(Predicate<? super K> predicate) {
    return MapTool.<K, V>filter().f2(predicate);
  }

  public static <K, V> Optional<V> get(Map<K, V> map, K key) {
    return Optional.ofNullable(map.get(key));
  }


  public static <K, V> Function1<Map<K, V>, Optional<V>> getter(K key) {
    return m -> get(m, key);
  }


  public static <K1, V1, K2, V2> Function1<Map<? extends K1, ? extends V1>, Map<K2, V2>> remapper(BiFunction<K1, V1, ? extends K2> keyGetter, BiFunction<K1, V1, ? extends V2> valueGetter) {
    return m -> m.entrySet().stream().collect(MapTool.collector(e -> keyGetter.apply(e.getKey(), e.getValue()), e -> valueGetter.apply(e.getKey(), e.getValue())));
  }

  public static <K1, K2, V> Function<Map<? extends K1, ? extends V>, Map<K2, V>> remapper(Function<K1, ? extends K2> keyMapper) {
    return MapTool.remapper((k, v) -> keyMapper.apply(k), (k, v) -> v);
  }

  public static <K, V, T> Map<K, T> mapValue(Map<K, V> input, Function<? super V, ? extends T> mapper) {
    return input.keySet().stream().collect(Collectors.toMap(Function.identity(), k -> mapper.apply(input.get(k))));
  }

  public static <K, V, T> Function<Map<K, V>, Map<K, T>> valueMapper(Function<? super V, ? extends T> mapper) {
    return m -> mapValue(m, mapper);
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class FilteredMap<K, V> implements Function1<Predicate<? super K>, Map<K, V>> {

    private final Map<K, V> target;

    @Override
    public Map<K, V> apply(Predicate<? super K> predicate) {
      return target.keySet().stream().filter(predicate).collect(collector(k -> k, target::get));
    }

    public Map<K, V> with(Predicate<? super K> predicate) {
      return apply(predicate);
    }

  }


}
