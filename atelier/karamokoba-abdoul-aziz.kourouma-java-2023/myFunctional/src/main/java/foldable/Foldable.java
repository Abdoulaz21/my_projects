package foldable;

import functional.Monoid;
import functional.Semigroup;

public interface Foldable<ENCLOSED_TYPE> {

    default ENCLOSED_TYPE fold(final Monoid<ENCLOSED_TYPE> monoid) {
        return fold(monoid.getNeutralElement(), monoid);
    }

    ENCLOSED_TYPE fold(ENCLOSED_TYPE accumulator, final Semigroup<ENCLOSED_TYPE> semigroup);
}
