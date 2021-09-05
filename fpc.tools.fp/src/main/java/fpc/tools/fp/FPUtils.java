package fpc.tools.fp;

import com.google.common.collect.*;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.InterruptedIOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author Bastien Aracil
 */
@UtilityClass
public class FPUtils {

    public static <A> Function1<A, ImmutableList<A>> adderLast(ImmutableList<A> list) {
        return a -> ImmutableList.<A>builder().addAll(list).add(a).build();
    }

    public static <A> Function1<Optional<A>, A> orElse(@NonNull A value) {
        return o -> o.orElse(value);
    }

    public static <O extends Optional<A>,A> Stream<A> filterOutEmpty(Stream<Optional<A>> stream) {
        return stream.filter(Optional::isPresent).map(Optional::get);
    }

    public static <A> Collector<A,ImmutableList.Builder<A>,ImmutableList<A>> immutableListCollector() {
        return Collector.of(ImmutableList::builder, ImmutableList.Builder::add, (b1,b2)->b1.addAll(b2.build()), ImmutableList.Builder::build);
    }

    public static <A,K,V> Collector<A,ImmutableMap.Builder<K,V>,ImmutableMap<K,V>> immutableMapCollector(@NonNull Function<? super A, ? extends K> keyGetter,@NonNull Function<? super A, ? extends V> valueGetter) {
        return Collector.of(ImmutableMap::builder, (b,a) -> b.put(keyGetter.apply(a), valueGetter.apply(a)), (b1,b2)->b1.putAll(b2.build()), ImmutableMap.Builder::build);
    }


    public static <A> Collector<A,ImmutableSet.Builder<A>, ImmutableSet<A>> immutableSetCollector() {
        return Collector.of(ImmutableSet::builder, ImmutableSet.Builder::add, (b1,b2)->b1.addAll(b2.build()), ImmutableSet.Builder::build);
    }

    public static <A extends Comparable<A>> Collector<A,ImmutableSortedSet.Builder<A>, ImmutableSortedSet<A>> immutableSortedSetCollectorNatural() {
        return Collector.of(ImmutableSortedSet::naturalOrder, ImmutableSortedSet.Builder::add, (b1,b2)->b1.addAll(b2.build()), ImmutableSortedSet.Builder::build);
    }

    public static <A> Collector<A,ImmutableSortedSet.Builder<A>, ImmutableSortedSet<A>> immutableSortedSetCollector(@NonNull Comparator<A> comparator) {
        return Collector.of(() -> ImmutableSortedSet.orderedBy(comparator), ImmutableSortedSet.Builder::add, (b1,b2)->b1.addAll(b2.build()), ImmutableSortedSet.Builder::build);
    }

    public static <R,C,A,T> Collector<A, ImmutableTable.Builder<R,C,T> ,ImmutableTable<R,C,T>> immutableTableCollector(Function<? super A, ? extends R> rowKeyGetter, Function<? super A, ? extends C> columnKeyGetter, Function<? super A, ? extends T> valueGetter) {
        return Collector.of(ImmutableTable::builder, (b1, a) -> b1.put(rowKeyGetter.apply(a), columnKeyGetter.apply(a), valueGetter.apply(a)), (b1, b2) -> b1.putAll(b2.build()), ImmutableTable.Builder::build);
    }

    public static <A,K extends Comparable<K>,V> Collector<A,ImmutableSortedMap.Builder<K,V>, ImmutableSortedMap<K,V>> immutableSortedMapCollectorNatural(
            @NonNull Function<? super A, ? extends K> keyGetter,
            @NonNull Function<? super A, ? extends V> valueGetter) {
        return Collector.of(
                ImmutableSortedMap::naturalOrder,
                (b,a) -> b.put(keyGetter.apply(a), valueGetter.apply(a)),
                (b1,b2) -> {b1.putAll(b2.build());return b1;},
                ImmutableSortedMap.Builder::build
        );

    }

    public static <A,K,V> Collector<A,ImmutableSortedMap.Builder<K,V>, ImmutableSortedMap<K,V>> immutableSortedMapCollector(
            @NonNull Function<? super A, ? extends K> keyGetter,
            @NonNull Function<? super A, ? extends V> valueGetter, Comparator<K> comparator) {
        return Collector.of(
                () -> ImmutableSortedMap.orderedBy(comparator),
                (b,a) -> b.put(keyGetter.apply(a), valueGetter.apply(a)),
                (b1,b2) -> {b1.putAll(b2.build());return b1;},
                ImmutableSortedMap.Builder::build
        );

    }


    public static <B> Function1<Object,Optional<B>> as(Class<B> clazz) {
        return o -> {
            if (clazz.isInstance(o)) {
                return Optional.of(clazz.cast(o));
            }
            return Optional.empty();
        };
    }

    public static void interruptIfCausedByInterruption(@NonNull Throwable throwable) {
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
            if (Arrays.stream(current.getSuppressed()).anyMatch(t -> isCausedByInterruption(t,seen))) {
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
