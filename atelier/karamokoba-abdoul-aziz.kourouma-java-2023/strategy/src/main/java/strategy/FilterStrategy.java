package strategy;

import java.util.List;

/**
 * The FilterStrategy interface.
 */
public interface FilterStrategy {

    /**
     * Filters the content of a List of Integer.
     *
     * @param input The List that must be filtered.
     * @return A filtered List.
     */
    public List<Integer> filter(List<Integer> input);
}
