package fpc.tools.fp;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.InterruptedIOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Bastien Aracil
 */
@UtilityClass
public class FPUtils {

  public static <A> Function1<A, List<A>> adderLast(List<A> list) {
    return a -> {
      final List<A> result = new ArrayList<A>(list.size() + 1);
      result.addAll(list);
      result.add(a);
      return Collections.unmodifiableList(result);
    };
  }

  public static <A> Function1<Optional<A>, A> orElse(@NonNull A value) {
    return o -> o.orElse(value);
  }

  public static <O extends Optional<A>, A> Stream<A> filterOutEmpty(Stream<Optional<A>> stream) {
    return stream.filter(Optional::isPresent).map(Optional::get);
  }

  public static <A> Collector<A, ?, List<A>> immutableListCollector() {
    return Collectors.toList();
  }

  public static <A, K, V> Collector<A, ?, Map<K, V>> immutableMapCollector(@NonNull Function<? super A, ? extends K> keyGetter, @NonNull Function<? super A, ? extends V> valueGetter) {
    return Collectors.toUnmodifiableMap(keyGetter, valueGetter);
  }


  public static <A> Collector<A, ?, Set<A>> immutableSetCollector() {
    return Collectors.toUnmodifiableSet();
  }

  public static <A extends Comparable<A>> Collector<A, ?, SortedSet<A>> immutableSortedSetCollectorNatural() {
    return Collectors.collectingAndThen(
        Collector.of(TreeSet<A>::new, Set::add, (t1, t2) -> {
          t1.addAll(t2);
          return t1;
        }),
        Collections::unmodifiableSortedSet
    );
  }

  public static <A> Collector<A, ?, SortedSet<A>> immutableSortedSetCollector(@NonNull Comparator<A> comparator) {
    return Collectors.collectingAndThen(
        Collector.of(() -> new TreeSet<A>(comparator), Set::add, (t1, t2) -> {
          t1.addAll(t2);
          return t1;
        }),
        Collections::unmodifiableSortedSet
    );
  }


  public static <A, K extends Comparable<K>, V> Collector<A, ?, SortedMap<K, V>> immutableSortedMapCollectorNatural(
      @NonNull Function<? super A, ? extends K> keyGetter,
      @NonNull Function<? super A, ? extends V> valueGetter) {
    return
        Collectors.collectingAndThen(
            Collector.of(
                () -> new TreeMap<K, V>(),
                (b, a) -> b.put(keyGetter.apply(a), valueGetter.apply(a)),
                (b1, b2) -> {
                  b1.putAll(b2);
                  return b1;
                }
            ),
            Collections::unmodifiableSortedMap);

  }

  public static <A, K, V> Collector<A, ?, SortedMap<K, V>> immutableSortedMapCollector(
      @NonNull Function<? super A, ? extends K> keyGetter,
      @NonNull Function<? super A, ? extends V> valueGetter, Comparator<K> comparator) {
    return
        Collectors.collectingAndThen(
            Collector.of(
                () -> new TreeMap<K, V>(comparator),
                (b, a) -> b.put(keyGetter.apply(a), valueGetter.apply(a)),
                (b1, b2) -> {
                  b1.putAll(b2);
                  return b1;
                }
            ),
            Collections::unmodifiableSortedMap);
  }


  public static <B> Function1<Object, Optional<B>> as(Class<B> clazz) {
    return o -> {
      if (clazz.isInstance(o)) {
        return Optional.of(clazz.cast(o));
      }
      return Optional.empty();
    };
  }

  public static void interruptIfCausedByAnInterruption(@NonNull Throwable throwable) {
    if (isCausedByInterruption(throwable)) {
      Thread.currentThread().interrupt();
    }
  }

  public static boolean isCausedByInterruption(@NonNull Throwable throwable) {
    if (isInterruption(throwable)) {
      return true;
    }
    return isCausedByInterruption(throwable, new HashSet<>());
  }

  private static boolean isCausedByInterruption(@NonNull Throwable throwable, @NonNull Set<Throwable> seen) {
    Throwable current = throwable;
    do {
      if (isInterruption(current)) {
        return true;
      }
      seen.add(current);
      if (Arrays.stream(current.getSuppressed()).anyMatch(t -> isCausedByInterruption(t, seen))) {
        return true;
      }
      current = current.getCause();
    } while (current != null && !seen.contains(current));
    return false;
  }

  private static boolean isInterruption(@NonNull Throwable throwable) {
    return throwable instanceof InterruptedException || throwable instanceof InterruptedIOException;
  }

}
