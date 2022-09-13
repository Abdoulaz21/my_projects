package foldable;

import functional.Semigroup;

import java.util.List;

public interface FoldableList<ENCLOSED_TYPE> extends List<ENCLOSED_TYPE>, Foldable<ENCLOSED_TYPE> {

    @Override
    default ENCLOSED_TYPE fold(final ENCLOSED_TYPE accumulator, final Semigroup<ENCLOSED_TYPE> semigroup) {
        var result = accumulator;

        for (int i = 0; i < this.size(); i++) {
            result = semigroup.apply(result, this.get(i));
        }
        return result;
    }
}
