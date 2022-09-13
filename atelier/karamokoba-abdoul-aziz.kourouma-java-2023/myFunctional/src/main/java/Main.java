import foldable.FoldableList;
import functional.Monoid;

import java.util.ArrayList;

public class Main {
    private static class FoldableArrayList<TYPE_T> extends ArrayList<TYPE_T> implements FoldableList<TYPE_T> {
    }

    ;

    public static void main(String[] args) {
        final Monoid<Integer> monoid = new Monoid<Integer>() {

            public Integer getNeutralElement() {
                return 0;
            }

            public Integer apply(Integer left, Integer right) {
                return left + right;
            }
        };

        final FoldableList<Integer> list = new FoldableArrayList<>();
        list.add(94002);
        list.add(78208);
        list.add(92046);

        System.out.println(list.fold(monoid));
    }
}
