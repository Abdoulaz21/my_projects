package functional;

public interface Function<TYPE_T, TARGET_T> {

    TARGET_T apply(TYPE_T t);

    default <V> Function<V, TARGET_T> compose(Function<? super V,? extends TYPE_T> before) {
        if (before == null)
            throw new NullPointerException("before is null");
        return (V v) -> apply(before.apply(v));
    }

    default <V> Function<TYPE_T, V> andThen(Function<? super TARGET_T,? extends V> after) {
        if (after == null)
            throw new NullPointerException("after is null");
        return (TYPE_T t) -> after.apply(apply(t));
    }

    static <V> Function<V, V> identity() {
        return (V v) -> v;
    }
}