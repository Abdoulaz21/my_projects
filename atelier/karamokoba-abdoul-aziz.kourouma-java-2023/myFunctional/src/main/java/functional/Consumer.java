package functional;

import java.util.NoSuchElementException;

public interface Consumer<TYPE_T> {

    void consume(TYPE_T generic);

    default Consumer<TYPE_T> andThen(Consumer<? super TYPE_T> after) {
        if (after == null)
            throw new NoSuchElementException("after is null");
        return (TYPE_T t) -> { consume(t); after.consume(t); };
    }

}
