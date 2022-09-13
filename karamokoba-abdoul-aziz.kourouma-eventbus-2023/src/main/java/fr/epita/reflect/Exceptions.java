package fr.epita.reflect;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Callable;

/**
 * Utility interface to help manage exceptions.
 */
public interface Exceptions {

    /**
     * Execute the given callable, and wrap any raised exception into a runtime exception.
     *
     * @param supplier        the {@link Callable} to execute.
     * @param <SUPPLIED_TYPE> The type of result produced by the {@link Callable}.
     * @return The result produced by the {@link Callable}.
     */
    static <SUPPLIED_TYPE>
    SUPPLIED_TYPE runtime(@NotNull final Callable<SUPPLIED_TYPE> supplier) {
        try {
            return supplier.call();
        } catch (final Exception exception) {
            throw new RuntimeException("Wrapping exception as a runtime: ", exception);
        }
    }
}
