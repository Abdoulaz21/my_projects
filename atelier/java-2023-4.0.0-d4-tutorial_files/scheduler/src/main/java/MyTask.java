import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public class MyTask<INPUT_TYPE, RETURN_TYPE> implements Task<RETURN_TYPE> {
    private final Function<INPUT_TYPE, RETURN_TYPE> taskAction;
    private final CompletableFuture<INPUT_TYPE> previous;
    private long time;
    private TimeUnit timeUnit;

    public MyTask(Function<INPUT_TYPE, RETURN_TYPE> action, CompletableFuture<INPUT_TYPE> prev) {
        this.taskAction = action;
        this.previous = prev;
    }

    public MyTask(Function<INPUT_TYPE, RETURN_TYPE> action, CompletableFuture<INPUT_TYPE> prev, long time, TimeUnit unit){
        this.taskAction = action;
        this.previous = prev;
        this.time = time;
        this.timeUnit = unit;
    }

    public Function<INPUT_TYPE, RETURN_TYPE> getAction() {
        return taskAction;
    }

    public CompletableFuture<INPUT_TYPE> getPrevious() {
        return previous;
    }

    public long getTime() {
        return time;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    static <RETURN_TYPE> Task<RETURN_TYPE> of(Supplier<RETURN_TYPE> actionSupplier) {
        return new MyTask<Void, RETURN_TYPE>(any -> actionSupplier.get(),null);
    }

    @Override
    public CompletableFuture<RETURN_TYPE> build() {
        var executor = this.getTime() != 0 ? CompletableFuture.delayedExecutor(this.getTime(), this.getTimeUnit())
                                                    : ForkJoinPool.commonPool();

        CompletableFuture<RETURN_TYPE> res;
        if (this.getPrevious() == null){
            res =  CompletableFuture.supplyAsync(() -> this.getAction().apply(null));
        }
        else{
            res = this.getPrevious()
                      .thenCompose(result -> CompletableFuture.supplyAsync(() -> this.getAction().apply(result), executor));
        }
        return res;
    }

    @Override
    public Task<RETURN_TYPE> onErrorRecoverWith(Function<Throwable, RETURN_TYPE> recoveryFunction) {
        throw new UnsupportedOperationException("FIXME");
    }

    @Override
    public <NEW_RETURN_TYPE> Task<NEW_RETURN_TYPE> andThenDo(Function<RETURN_TYPE, NEW_RETURN_TYPE> action) {
        return new MyTask<>(action, this.build(), this.getTime(), this.getTimeUnit());
    }

    @Override
    public Task<RETURN_TYPE> andThenWait(long number, TimeUnit timeUnit) {
        return new MyTask<>(any -> any, this.build(),number, timeUnit);
    }
}
