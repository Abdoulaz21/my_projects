package listIteratorFunctions;

import java.util.*;
import java.util.function.Function;

public class ListIteratorFunctions {
    public static <T> void ReversePrint(List<T> list) {
        var it = list.listIterator(list.size());
        while(it.hasPrevious()) {
            System.out.println(it.previous());
        }
    }

    public static <T extends Comparable<T>> void Remove(List<T> list,
                                                        T... toRemove) {
        list.removeIf(t -> {
            for (T elmt : toRemove) {
                if (t.compareTo(elmt) == 0) {
                    System.out.println("Removed: " + t);
                    return true;
                }
            }
            System.out.println("Nothing removed");
            return false;
        });
    }

    public static <T> void ReplaceAll(List<T> list, Function<T, T> op) {
        var it = list.listIterator();
        while(it.hasNext()) {
            var newVal = op.apply(it.next());
            if (newVal != null) {
                it.set(newVal);
            }
        }
    }
}
