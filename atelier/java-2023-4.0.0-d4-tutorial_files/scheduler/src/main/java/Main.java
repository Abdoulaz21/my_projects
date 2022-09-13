import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //var res = CompletableFuture.supplyAsync(() -> 17,
        //                                  CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS))
        //                                 .thenCompose(e -> CompletableFuture.supplyAsync(() -> e + 20,
        //                                         CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS)));

        //System.out.println("BEFORE");
        //System.out.println(res.get());
        //System.out.println("AFTER");


        var bestShell = MyTask.of(() -> 42)
                                     .andThenWait(1L, TimeUnit.SECONDS)
                                     .andThenDo(value -> value + "sh")
                                     .execute();
        System.out.println(bestShell);
    }
}
