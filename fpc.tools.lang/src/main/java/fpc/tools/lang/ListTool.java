package fpc.tools.lang;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import fpc.tools.fp.Function3;
import fpc.tools.fp.Predicate1;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author bastien.a
 */
@SuppressWarnings("unused")
public class ListTool {

  public static <A> List<A> addSorted(List<A> list, Comparator<? super A> comparator, A element) {
    final var result = ListTool.<A>arrayList(list.size()+1);
    var added = false;
    for (A a : list) {
      if (!added && comparator.compare(a, element) >= 0) {
        added = true;
        result.add(element);
      }
      result.add(a);
    }
    return Collections.unmodifiableList(result);
  }


  public static <A> List<A> arrayList(int capacity) {
    return new ArrayList<>(capacity);
  }

  public static <A> List<A> arrayList() {
    return new ArrayList<>();
  }

  public static <A> List<A> replace(List<A> source, A value, Predicate1<A> filter) {
    return source.stream()
        .map(a -> filter.test(a) ? value : a)
        .toList();
  }

  /**
   * Add <code>v1</code> to the beginning of the list <code>list</code>
   *
   * @param v1   the element to add
   * @param list the list to add the element to
   * @param <A>  the type of the elements
   * @return a new immutable list with <code>v1</code> as first element and all elements of <code>list</code> afterward
   */
  public static <A> List<A> addFirst(A v1, List<? extends A> list) {
    final var result = ListTool.<A>arrayList(list.size()+1);
    result.add(v1);
    result.addAll(list);
    return Collections.unmodifiableList(result);
  }

  public static <A> List<A> addFirst(A v1, A v2, List<? extends A> list) {
    final var result = ListTool.<A>arrayList(list.size()+2);
    result.add(v1);
    result.add(v2);
    result.addAll(list);
    return Collections.unmodifiableList(result);
  }

  public static <A> List<A> addFirst(A v1, A v2, A v3, List<? extends A> list) {
    final var result = ListTool.<A>arrayList(list.size()+3);
    result.add(v1);
    result.add(v2);
    result.add(v3);
    result.addAll(list);
    return Collections.unmodifiableList(result);
  }

  public static <A> List<A> addFirst(A v1, A v2, A v3, A v4, List<? extends A> list) {
    final var result = ListTool.<A>arrayList(list.size()+4);
    result.add(v1);
    result.add(v2);
    result.add(v3);
    result.add(v4);
    result.addAll(list);
    return Collections.unmodifiableList(result);
  }

  public static <A, I> int findFirst(List<A> collection, Function<? super A, ? extends I> idGetter, I searchedId) {
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
   * @param <A>  the type of element in the stream
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

  public static <A> Function1<Predicate<? super A>, List<A>> removeOnceFrom(List<A> list) {
    return p -> {
      final Function1<Stream<A>, Stream<A>> r = removeOnce(p);
      return r.apply(list.stream()).collect(ListTool.collector());
    };
  }

  public static <A> Function1<Predicate<? super A>, Function1<List<A>, List<A>>> removeOnceFromList(Class<A> type) {
    return p -> l -> removeOnce(p, type).apply(l.stream()).collect(ListTool.collector());
  }

  public static <A> Function1<Predicate<? super A>, Function1<List<A>, List<A>>> removeOnceFromList() {
    return p -> l -> ListTool.<A>removeOnce(p).apply(l.stream()).collect(ListTool.collector());
  }

  /**
   * @param <A> the elements of the list
   * @return a function that return an immutable list obtained by adding a given element to the beginning of a given immutable list
   */
  public static <A> Function2<List<A>, A, List<A>> addFirst() {
    return (l, a) -> {
      final var result = ListTool.<A>arrayList(l.size() + 1);
      result.add(a);
      result.addAll(l);
      return Collections.unmodifiableList(result);
    };
  }

  /**
   * @param <A> the elements of the list
   * @return a function that return an immutable list obtained by adding a given element to the end of a given immutable list
   */
  public static <A> Function2<List<A>, A, List<A>> addLast() {
    return (l, a) -> {
      final var result = ListTool.<A>arrayList(l.size() + 1);
      result.addAll(l);
      result.add(a);

      return Collections.unmodifiableList(result);
    };
  }

  public static <A> Function1<List<A>, List<A>> addFirst(A a) {
    return ListTool.<A>addFirst().f2(a);
  }

  public static <A> Function1<List<A>, List<A>> addLast(A a) {
    return ListTool.<A>addLast().f2(a);
  }

  public static <A> Function1<A, List<A>> addFirstTo(List<A> list) {
    return ListTool.<A>addFirst().f1(list);
  }

  public static <A> Function1<List<A>, Stream<A>> streamer() {
    return Collection::stream;
  }


  public static <A> Collector<A, ?, List<A>> collector() {
    return Collectors.toList();
  }

  public static <A> Function1<Stream<A>, List<A>> collect() {
    return s -> s.collect(collector());
  }


  public static <A> Function2<List<A>, Predicate<? super A>, List<A>> filter() {
    return (list, test) -> list.stream().filter(test).collect(collector());
  }

  public static <A, B> Function3<List<A>, Function<? super A, ? extends B>, Predicate<? super B>, List<B>> mapAndFilter() {
    return (list, mapper, test) -> list.stream().map(mapper).filter(test).collect(collector());
  }

  public static <A, B> Function3<List<A>, Predicate<? super A>, Function<? super A, ? extends B>, List<B>> filterAndMap() {
    return (list, test, mapper) -> list.stream().filter(test).map(mapper).collect(collector());
  }

  public static <A> FilteredList<A> filter(List<A> target) {
    return new FilteredList<>(target);
  }


  public static <A> Function1<List<A>, List<A>> filterWith(Predicate<? super A> predicate) {
    return ListTool.<A>filter().f2(predicate);
  }


  public static <A, B> Function1<List<A>, List<B>> mapAndfilterWith(Function<? super A, ? extends B> mapper, Predicate<? super B> predicate) {
    return ListTool.<A, B>mapAndFilter().f23(mapper, predicate);
  }

  public static <A, B> Function1<List<A>, List<B>> filterAndMapWith(Predicate<? super A> predicate, Function<? super A, ? extends B> mapper) {
    return ListTool.<A, B>filterAndMap().f23(predicate, mapper);
  }

  public static <A, B> List<B> filterAndMap(Collection<A> input, Predicate<? super A> predicate, Function<? super A, ? extends B> mapper) {
    return input.stream().filter(predicate).map(mapper).collect(ListTool.collector());
  }


  public static <A> Predicate<Collection<A>> allMatch(Predicate<A> elementPredicate) {
    return c -> c.stream().allMatch(elementPredicate);
  }

  public static <A> Predicate<Collection<A>> anyMatch(Predicate<A> elementPredicate) {
    return c -> c.stream().anyMatch(elementPredicate);
  }

  public static <A> Predicate<Collection<A>> noneMatch(Predicate<A> elementPredicate) {
    return c -> c.stream().noneMatch(elementPredicate);
  }

  public static <A, B> List<B> map(Collection<A> list, Function<? super A, ? extends B> mapper) {
    return list.stream().map(mapper).collect(collector());
  }

  public static <A, B> Function1<Collection<A>, List<B>> mapper(Function<? super A, ? extends B> mapper) {
    return l -> map(l, mapper);
  }

  public static <A> List<A> concat(List<? extends A> listFirst, List<? extends A> listSecond) {
    return Stream.concat(
        listFirst.stream(),
        listSecond.stream()
    ).toList();
  }

  public static <A> Function2<List<? extends A>, List<? extends A>, List<A>> concatener(Class<A> type) {
    return ListTool::concat;
  }


  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class FilteredList<A> implements Function1<Predicate<? super A>, List<A>> {

    private final List<A> target;

    @Override
    public List<A> apply(Predicate<? super A> predicate) {
      return target.stream().filter(predicate).collect(collector());
    }

    public List<A> with(Predicate<? super A> predicate) {
      return apply(predicate);
    }

  }


  /**
   * @author Bastien Aracil
   */
  @RequiredArgsConstructor
  private static class OnlyOncePredicate<A> implements Predicate<A> {

    private final AtomicBoolean matched = new AtomicBoolean(false);

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
