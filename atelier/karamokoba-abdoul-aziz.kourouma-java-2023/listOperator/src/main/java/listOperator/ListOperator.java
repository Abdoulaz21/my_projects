package listOperator;

import java.util.*;
import java.util.function.Function;


public class ListOperator {

    public static <T extends Comparable<T>> List<T> merge(List<T> l1, List<T> l2) {
        ArrayList<T> arrayList = new ArrayList<>(l1);
        for (T element: l2) {
            if (arrayList.contains(element)) {
                continue;
            }
            arrayList.add(element);
        }

        Collections.sort(arrayList);
        return arrayList.stream().distinct().sorted().toList();
    }

    public static <T extends Comparable<T>> List<T> remove(List<T> l, T... toRemove) {
        for (T elementToRemove: toRemove) {
            l.removeIf(e-> e.equals(elementToRemove));
        }
        return l;
    }

    public static <T, R> List<R> applyAll(List<T> l, Function<T, R> op) {
        return l.stream().map(op).filter(Objects::nonNull).toList();
    }
}