import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        var bestShell = MyTask.of(() -> 42)
                .andThenWait(1L, TimeUnit.SECONDS)
                .andThenDo(value -> value + "sh")
                .execute();
        System.out.println(bestShell);
    }
}
