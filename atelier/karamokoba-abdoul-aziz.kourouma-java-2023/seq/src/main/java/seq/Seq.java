package seq;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public interface Seq<ELEMENT_TYPE> extends ExtendedStream<ELEMENT_TYPE> {

    Stream<ELEMENT_TYPE> streamGet();

    static <TYPE_T> Seq<TYPE_T> of(final List<TYPE_T> values) {
        return values::stream;
    }

    static <TYPE_T> Seq<TYPE_T> of(final TYPE_T... values) {
        return () -> Arrays.stream(values);
    }

    static <TYPE_T> Seq<TYPE_T> of(final Stream<TYPE_T> values) {
        return () -> values;
    }

    @Override
    default <KEY_TYPE> Map<KEY_TYPE, ELEMENT_TYPE>
    toMap(final Function<ELEMENT_TYPE, KEY_TYPE> keyMapper) {
        return distinct().collect( Collectors.toMap(keyMapper, Function.identity(), (first, second) -> second));
    }

    @Override
    default <KEY_TYPE, VALUE_TYPE, MAP_TYPE extends Map<KEY_TYPE, VALUE_TYPE>>
    MAP_TYPE toMap(final MAP_TYPE map,
                   final Function<ELEMENT_TYPE, KEY_TYPE> keyMapper,
                   final Function<ELEMENT_TYPE, VALUE_TYPE> valueMapper) {
        var a = distinct().collect( Collectors.toMap(keyMapper, valueMapper, (first, second) -> second));
        map.putAll(a);
        return map;
    }

    @Override
    default <KEY_TYPE, VALUE_TYPE>
    Map<KEY_TYPE, VALUE_TYPE> toMap(final Function<ELEMENT_TYPE, KEY_TYPE> keyMapper,
                                    final Function<ELEMENT_TYPE, VALUE_TYPE> valueMapper) {
        return distinct().collect( Collectors.toMap(keyMapper, valueMapper, (first, second) -> second));
    }

    @Override
    default List<ELEMENT_TYPE> toList() {
        return streamGet().toList();
    }

    @Override
    default <LIST extends List<ELEMENT_TYPE>> LIST toList(final LIST list) {
        list.addAll(toList());
        return list;
    }

    @Override
    default Set<ELEMENT_TYPE> toSet() {
        return collect( Collectors.toSet());
    }

    @Override
    default <SET extends Set<ELEMENT_TYPE>> SET toSet(SET set) {
        set.addAll(toSet());
        return set;
    }

    @Override
    default <ASSOCIATED_TYPE>
    ExtendedStream<Pair<ELEMENT_TYPE, ASSOCIATED_TYPE>> associate(final Supplier<ASSOCIATED_TYPE> supplier) {
        return of(streamGet().map(x -> new Pair<>(x, supplier.get())));
    }

    @Override
    default <ASSOCIATED_TYPE>
    ExtendedStream<Pair<ELEMENT_TYPE, ASSOCIATED_TYPE>> associate(final Stream<ASSOCIATED_TYPE> supplier) {
        var list1 = toList();
        var list2 = supplier.toList();
        List<Pair<ELEMENT_TYPE, ASSOCIATED_TYPE>> result = new ArrayList<>();

        for (int i = 0; i < list1.size() && i < list2.size(); i++) {
            result.add(new Pair<>(list1.get(i), list2.get(i)));
        }

        return of(result);
    }

    @Override
    default ExtendedStream<ELEMENT_TYPE> print() {
        System.out.println(streamGet().toString());
        return this;
    }

    @Override
    default ExtendedStream<ELEMENT_TYPE> plus(final Stream<ELEMENT_TYPE> stream) {
        return of(Stream.concat(streamGet(), stream));
    }

    @Override
    default Object join(final String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        streamGet().forEach(x -> stringBuilder.append(x.toString()).append(delimiter));
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    @Override
    default String join() {
        StringBuilder stringBuilder = new StringBuilder();
        streamGet().forEach(x -> stringBuilder.append(x.toString()));
        return stringBuilder.toString();
    }

    @Override
    default <KEY_TYPE>
    ExtendedStream<Pair<KEY_TYPE, ExtendedStream<ELEMENT_TYPE>>>
    partition(final Function<ELEMENT_TYPE, KEY_TYPE> pivot) {
        var list = toList();
        List<Pair<KEY_TYPE, ExtendedStream<ELEMENT_TYPE>>> res = new ArrayList<>();
        List<KEY_TYPE> keyList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            KEY_TYPE key = pivot.apply(list.get(i));
            if (keyList.contains(key))
                continue;
            keyList.add(key);
            var a = of(list.stream().filter(x -> pivot.apply(x).equals(key)));
            res.add(new Pair<>(key, a));
        }
        return of(res);
    }

    // Delegete Stream method

    @Override
    default Stream<ELEMENT_TYPE> filter(Predicate<? super ELEMENT_TYPE> predicate) {
        return streamGet().filter(predicate);
    }

    @Override
    default  <R> Stream<R> map(Function<? super ELEMENT_TYPE, ? extends R> mapper) {
        return streamGet().map(mapper);
    }

    @Override
    default IntStream mapToInt(ToIntFunction<? super ELEMENT_TYPE> mapper) {
        return streamGet().mapToInt(mapper);
    }

    @Override
    default LongStream mapToLong(ToLongFunction<? super ELEMENT_TYPE> mapper) {
        return streamGet().mapToLong(mapper);
    }

    @Override
    default DoubleStream mapToDouble(ToDoubleFunction<? super ELEMENT_TYPE> mapper) {
        return streamGet().mapToDouble(mapper);
    }

    @Override
    default  <R> Stream<R> flatMap(Function<? super ELEMENT_TYPE, ? extends Stream<? extends R>> mapper) {
        return streamGet().flatMap(mapper);
    }

    @Override
    default IntStream flatMapToInt(Function<? super ELEMENT_TYPE, ? extends IntStream> mapper) {
        return streamGet().flatMapToInt(mapper);
    }

    @Override
    default LongStream flatMapToLong(Function<? super ELEMENT_TYPE, ? extends LongStream> mapper) {
        return streamGet().flatMapToLong(mapper);
    }

    @Override
    default DoubleStream flatMapToDouble(Function<? super ELEMENT_TYPE, ? extends DoubleStream> mapper) {
        return streamGet().flatMapToDouble(mapper);
    }

    @Override
    default Stream<ELEMENT_TYPE> distinct() {
        return streamGet().distinct();
    }

    @Override
    default Stream<ELEMENT_TYPE> sorted() {
        return streamGet().sorted();
    }

    @Override
    default Stream<ELEMENT_TYPE> sorted(Comparator<? super ELEMENT_TYPE> comparator) {
        return streamGet().sorted(comparator);
    }

    @Override
    default Stream<ELEMENT_TYPE> peek(Consumer<? super ELEMENT_TYPE> action) {
        return streamGet().peek(action);
    }

    @Override
    default Stream<ELEMENT_TYPE> limit(long maxSize) {
        return streamGet().limit(maxSize);
    }

    @Override
    default Stream<ELEMENT_TYPE> skip(long n) {
        return streamGet().skip(n);
    }

    @Override
    default void forEach(Consumer<? super ELEMENT_TYPE> action) {
        streamGet().forEach(action);
    }

    @Override
    default void forEachOrdered(Consumer<? super ELEMENT_TYPE> action) {
        streamGet().forEachOrdered(action);
    }

    @Override
    default Object[] toArray() {
        return streamGet().toArray();
    }

    @Override
    default <A> A[] toArray(IntFunction<A[]> generator) {
        return streamGet().toArray(generator);
    }

    @Override
    default ELEMENT_TYPE reduce(ELEMENT_TYPE identity, BinaryOperator<ELEMENT_TYPE> accumulator) {
        return streamGet().reduce(identity, accumulator);
    }

    @Override
    default Optional<ELEMENT_TYPE> reduce(BinaryOperator<ELEMENT_TYPE> accumulator) {
        return streamGet().reduce(accumulator);
    }

    @Override
    default  <U> U reduce(U identity, BiFunction<U, ? super ELEMENT_TYPE, U> accumulator, BinaryOperator<U> combiner) {
        return streamGet().reduce(identity, accumulator, combiner);
    }

    @Override
    default  <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super ELEMENT_TYPE> accumulator, BiConsumer<R, R> combiner) {
        return streamGet().collect(supplier, accumulator ,combiner);
    }

    @Override
    default  <R, A> R collect(Collector<? super ELEMENT_TYPE, A, R> collector) {
        return streamGet().collect(collector);
    }

    @Override
    default Optional<ELEMENT_TYPE> min(Comparator<? super ELEMENT_TYPE> comparator) {
        return streamGet().min(comparator);
    }

    @Override
    default Optional<ELEMENT_TYPE> max(Comparator<? super ELEMENT_TYPE> comparator) {
        return streamGet().max(comparator);
    }

    @Override
    default long count() {
        return streamGet().count();
    }

    @Override
    default boolean anyMatch(Predicate<? super ELEMENT_TYPE> predicate) {
        return streamGet().anyMatch(predicate);
    }

    @Override
    default boolean allMatch(Predicate<? super ELEMENT_TYPE> predicate) {
        return streamGet().allMatch(predicate);
    }

    @Override
    default boolean noneMatch(Predicate<? super ELEMENT_TYPE> predicate) {
        return false;
    }

    @Override
    default Optional<ELEMENT_TYPE> findFirst() {
        return streamGet().findFirst();
    }

    @Override
    default Optional<ELEMENT_TYPE> findAny() {
        return streamGet().findAny();
    }

    @Override
    default Iterator<ELEMENT_TYPE> iterator() {
        return streamGet().iterator();
    }

    @Override
    default Spliterator<ELEMENT_TYPE> spliterator() {
        return streamGet().spliterator();
    }

    @Override
    default boolean isParallel() {
        return streamGet().isParallel();
    }

    @Override
    default Stream<ELEMENT_TYPE> sequential() {
        return streamGet().sequential();
    }

    @Override
    default Stream<ELEMENT_TYPE> parallel() {
        return streamGet().parallel();
    }

    @Override
    default Stream<ELEMENT_TYPE> unordered() {
        return streamGet().unordered();
    }

    @Override
    default Stream<ELEMENT_TYPE> onClose(Runnable closeHandler) {
        return streamGet().onClose(closeHandler);
    }

    @Override
    default void close() {

    }
}