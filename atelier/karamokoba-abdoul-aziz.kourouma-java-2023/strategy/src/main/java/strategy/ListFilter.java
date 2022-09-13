package strategy;

import java.util.List;

public class ListFilter {

    /** The public method used by other classes to select the filter **/
    public static List<Integer> filter(List<Integer> input, int selector) {
        if (selector > 0) {
         return filter(input, new PrimeFilter());
        }
        else if (selector == 0)
            return filter(input, new PowerFilter());
        else
            return filter(input, new EvenFilter());
    }

    /**
     * The private method used internally to apply the selected filter
     **/
    private static List<Integer> filter(List<Integer> input, FilterStrategy strategy) {
        return strategy.filter(input);
    }
}
