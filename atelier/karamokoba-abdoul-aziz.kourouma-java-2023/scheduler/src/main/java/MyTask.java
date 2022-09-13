import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public class MyTask<INPUT_TYPE, RETURN_TYPE> implements Task<RETURN_TYPE> {

    private final Function<INPUT_TYPE, RETURN_TYPE> action;
    private final CompletableFuture<INPUT_TYPE> previous;
    private long delay;
    private TimeUnit timeUnit;
    private Function<Throwable, RETURN_TYPE> recoveryFunction;

    public MyTask(Function<INPUT_TYPE, RETURN_TYPE> action, CompletableFuture<INPUT_TYPE> previous) {
        this.action = action;
        this.previous = previous;
        this.recoveryFunction = null;
    }

    public MyTask(Function<INPUT_TYPE, RETURN_TYPE> action, CompletableFuture<INPUT_TYPE> previous, long delay, TimeUnit timeUnit) {
        this.action = action;
        this.previous = previous;
        this.delay = delay;
        this.timeUnit = timeUnit;
        this.recoveryFunction = null;
    }

    static <RETURN_TYPE> Task<RETURN_TYPE> of(Supplier<RETURN_TYPE> actionSupplier) {
        return new MyTask<Void, RETURN_TYPE>(any -> actionSupplier.get(), null);
    }

    @Override
    public CompletableFuture<RETURN_TYPE> build() {
        CompletableFuture<RETURN_TYPE> newCompl;
        if (delay != 0 && previous == null)
            newCompl = CompletableFuture.supplyAsync(() -> action.apply(null),
                    CompletableFuture.delayedExecutor(delay, timeUnit));
        else if (delay != 0 && previous != null)
            newCompl = previous.thenCompose(result ->
                    CompletableFuture.supplyAsync(() -> action.apply(result),
                    CompletableFuture.delayedExecutor(delay, timeUnit)));
        else if (previous == null)
            newCompl = CompletableFuture.supplyAsync(() -> action.apply(null));
        else
            newCompl = previous.thenCompose(result ->
                    CompletableFuture.supplyAsync(() -> action.apply(result)));
        if (recoveryFunction != null)
            newCompl = newCompl.exceptionally(recoveryFunction);
        return newCompl;
    }

    @Override
    public Task<RETURN_TYPE> onErrorRecoverWith(Function<Throwable, RETURN_TYPE> recoveryFunction) {
        this.recoveryFunction = recoveryFunction;
        return this;
    }

    @Override
    public <NEW_RETURN_TYPE> Task<NEW_RETURN_TYPE> andThenDo(Function<RETURN_TYPE, NEW_RETURN_TYPE> action) {
        return new MyTask<RETURN_TYPE, NEW_RETURN_TYPE>(action, build());
    }

    @Override
    public Task<RETURN_TYPE> andThenWait(long number, TimeUnit timeUnit) {
        return new MyTask<>(any -> any, build(), number, timeUnit);
    }
}
