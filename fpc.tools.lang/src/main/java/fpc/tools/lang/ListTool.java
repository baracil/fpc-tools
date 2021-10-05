package fpc.tools.lang;

import com.google.common.collect.ImmutableList;
import fpc.tools.fp.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author bastien.a
 */
@SuppressWarnings("unused")
public class ListTool {


    public static <A> ImmutableList<A> replace(@NonNull ImmutableList<A> source, @NonNull A value, @NonNull Predicate1<A> filter) {
        return source.stream()
                     .map(a -> filter.f(a)?value:a)
                     .collect(ImmutableList.toImmutableList());
    }

    /**
     * Add <code>v1</code> to the beginning of the list <code>list</code>
     * @param v1 the element to add
     * @param list the list to add the element to
     * @param <A> the type of the elements
     * @return a new immutable list with <code>v1</code> as first element and all elements of <code>list</code> afterward
     */
    public static <A> ImmutableList<A> addFirst(@NonNull A v1,@NonNull ImmutableList<? extends A> list) {
        return ImmutableList.<A>builder().add(v1).addAll(list).build();
    }

    public static <A> ImmutableList<A> addFirst(@NonNull A v1,@NonNull A v2,@NonNull ImmutableList<? extends A> list) {
        return ImmutableList.<A>builder().add(v1).add(v2).addAll(list).build();
    }

    public static <A> ImmutableList<A> addFirst(@NonNull A v1,@NonNull A v2,@NonNull A v3,@NonNull ImmutableList<? extends A> list) {
        return ImmutableList.<A>builder().add(v1).add(v2).add(v3).addAll(list).build();
    }

    public static <A> ImmutableList<A> addFirst(@NonNull A v1,@NonNull A v2,@NonNull A v3,@NonNull A v4,@NonNull ImmutableList<? extends A> list) {
        return ImmutableList.<A>builder().add(v1).add(v2).add(v3).add(v4).addAll(list).build();
    }

    public static <A,I> int findFirst(@NonNull List<A> collection,@NonNull Function<? super A, ? extends I> idGetter, @NonNull I searchedId) {
        int i = 0;
        for (A a : collection) {
            if (searchedId.equals(idGetter.apply(a))) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    /**
     * @param test the predicate to use to test for the removal
     * @param <A> the type of element in the stream
     * @return a function that remove from an input stream on and only one element.
     */
    public static <A> Function1<Stream<A>, Stream<A>> removeOnce(Predicate<? super A> test) {
        return s -> s.filter(new OnlyOncePredicate<>(test));
    }

    public static <A> Function1<Stream<A>, Stream<A>> removeOnce(Predicate<? super A> test, Class<A> type) {
        return s -> s.filter(new OnlyOncePredicate<>(test));
    }

    public static <A> Function1<Predicate<? super A>, Function1<Stream<A>, Stream<A>>> removeOnce(Class<A> type) {
        return p -> removeOnce(p, type);
    }

    public static <A> Function1<Predicate<? super A>, ImmutableList<A>> removeOnceFrom(ImmutableList<A> list) {
        return p -> {
            final Function1<Stream<A>, Stream<A>> r = removeOnce(p);
            return r.f(list.stream()).collect(ListTool.collector());
        };
    }

    public static <A> Function1<Predicate<? super A>, Function1<ImmutableList<A>, ImmutableList<A>>> removeOnceFromList(Class<A> type) {
        return p -> l -> removeOnce(p, type).f(l.stream()).collect(ListTool.collector());
    }

    public static <A> Function1<Predicate<? super A>, Function1<ImmutableList<A>, ImmutableList<A>>> removeOnceFromList() {
        return p -> l -> ListTool.<A>removeOnce(p).f(l.stream()).collect(ListTool.collector());
    }

    /**
     * @param <A> the elements of the list
     * @return a function that return an immutable list obtained by adding a given element to the beginning of a given immutable list
     */
    public static <A> Function2<ImmutableList<A>, A, ImmutableList<A>> addFirst() {
        return (l,a) -> ImmutableList.<A>builder().add(a).addAll(l).build();
    }

    /**
     * @param <A> the elements of the list
     * @return a function that return an immutable list obtained by adding a given element to the end of a given immutable list
     */
    public static <A> Function2<ImmutableList<A>, A, ImmutableList<A>> addLast() {
        return (l,a) -> ImmutableList.<A>builder().addAll(l).add(a).build();
    }

    public static <A> Function1<ImmutableList<A>, ImmutableList<A>> addFirst(A a) {
        return ListTool.<A>addFirst().f2(a);
    }

    public static <A> Function1<ImmutableList<A>, ImmutableList<A>> addLast(A a) {
        return ListTool.<A>addLast().f2(a);
    }

    public static <A> Function1<A, ImmutableList<A>> addFirstTo(ImmutableList<A> list) {
        return ListTool.<A>addFirst().f1(list);
    }

    public static <A> Function1<ImmutableList<A>, Stream<A>> streamer() {
        return Collection::stream;
    }


    public static <A> Collector<A,?,ImmutableList<A>> collector() {
        return FPUtils.immutableListCollector();
    }

    public static <A> Function1<Stream<A>, ImmutableList<A>> collect() {
        return s -> s.collect(collector());
    }


    public static <A> Function2<ImmutableList<A>, Predicate<? super A>, ImmutableList<A>> filter() {
        return (list,test) -> list.stream().filter(test).collect(collector());
    }

    public static <A,B> Function3<ImmutableList<A>, Function<? super A, ? extends B>, Predicate<? super B>, ImmutableList<B>> mapAndFilter() {
        return (list,mapper, test) -> list.stream().map(mapper).filter(test).collect(collector());
    }

    public static <A,B> Function3<ImmutableList<A>, Predicate<? super A>, Function<? super A, ? extends B>, ImmutableList<B>> filterAndMap() {
        return (list, test, mapper) -> list.stream().filter(test).map(mapper).collect(collector());
    }

    public static <A> FilteredList<A> filter(ImmutableList<A> target) {
        return new FilteredList<>(target);
    }


    public static <A> Function1<ImmutableList<A>, ImmutableList<A>> filterWith(Predicate<? super A> predicate) {
        return ListTool.<A>filter().f2(predicate);
    }


    public static <A,B> Function1<ImmutableList<A>, ImmutableList<B>> mapAndfilterWith(Function<? super A, ? extends B> mapper, Predicate<? super B> predicate) {
        return ListTool.<A,B>mapAndFilter().f23(mapper, predicate);
    }

    public static <A,B> Function1<ImmutableList<A>, ImmutableList<B>> filterAndMapWith(Predicate<? super A> predicate, Function<? super A, ? extends B> mapper) {
        return ListTool.<A,B>filterAndMap().f23(predicate, mapper);
    }

    public static <A,B> ImmutableList<B> filterAndMap(Collection<A> input, Predicate<? super A> predicate, Function<? super A, ? extends B> mapper) {
        return input.stream().filter(predicate).map(mapper).collect(ListTool.collector());
    }




    public static <A> Predicate<Collection<A>> allMatch(@NonNull Predicate<A> elementPredicate) {
        return c -> c.stream().allMatch(elementPredicate);
    }

    public static <A> Predicate<Collection<A>> anyMatch(@NonNull Predicate<A> elementPredicate) {
        return c -> c.stream().anyMatch(elementPredicate);
    }

    public static <A> Predicate<Collection<A>> noneMatch(@NonNull Predicate<A> elementPredicate) {
        return c -> c.stream().noneMatch(elementPredicate);
    }

    public static <A,B> ImmutableList<B> map(Collection<A> list, Function<? super A, ? extends B> mapper) {
        return list.stream().map(mapper).collect(collector());
    }

    public static <A,B> Function1<Collection<A>, ImmutableList<B>> mapper(Function<? super A,? extends B> mapper) {
        return l -> map(l,mapper);
    }

    @NonNull
    public static <A> ImmutableList<A> concat(List<? extends A> listFirst, List<? extends A> listSecond) {
        return Stream.concat(
                listFirst.stream(),
                listSecond.stream()
        ).collect(ImmutableList.toImmutableList());
    }

    @NonNull
    public static <A> Function2<ImmutableList<? extends A>, ImmutableList<? extends A>, ImmutableList<A>> concatener(Class<A> type) {
        return ListTool::concat;
    }


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FilteredList<A> implements Function1<Predicate<? super A>, ImmutableList<A>> {

        @NonNull
        private final ImmutableList<A> target;

        @NonNull
        @Override
        public ImmutableList<A> f(@NonNull Predicate<? super A> predicate) {
            return target.stream().filter(predicate).collect(collector());
        }

        public ImmutableList<A> with(Predicate<? super A> predicate) {
            return f(predicate);
        }

    }


    /**
     * @author Bastien Aracil
     */
    @RequiredArgsConstructor
    private static class OnlyOncePredicate<A> implements Predicate<A> {

        private final AtomicBoolean matched = new AtomicBoolean(false);

        @NonNull
        private final Predicate<? super A> test;

        @Override
        public boolean test(A a) {
            final boolean toRemove = test.test(a);
            if (matched.compareAndSet(false, toRemove)) {
                return !toRemove;
            }
            return true;
        }
    }
}
