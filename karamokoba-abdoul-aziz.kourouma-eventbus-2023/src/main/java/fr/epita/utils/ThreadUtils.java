package fr.epita.utils;

import fr.epita.reflect.Exceptions;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * Utility methods for dealing with threads and friends.
 */
public interface ThreadUtils {

    /**
     * Dispatch the given callable on the fork/join pool, returning a {@link CompletableFuture}. Exceptions are
     * wrapped in a {@link RuntimeException}.
     *
     * @param callable      The execution to dispatch.
     * @param <RESULT_TYPE> The actual result type.
     * @return A {@link CompletableFuture} of the dispatch processing.
     */
    static <RESULT_TYPE> CompletableFuture<RESULT_TYPE> dispatch(final Callable<RESULT_TYPE> callable) {
        return CompletableFuture.supplyAsync(() -> Exceptions.runtime(callable));
    }
}
