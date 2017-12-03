package future;

import java.util.concurrent.*;

/**
 * Created by leo on 17-3-22.
 */
public class FutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Runnable task1 = new Runnable() {
            public void run() {
                System.out.println("i am task1...");
            }
        };

        Callable<Integer> task2 = new Callable<Integer>() {
            public Integer call() throws Exception {
                return new Integer(100);
            }
        };

        Future<?> f1 = executorService.submit(task1);
        Future<Integer> f2 = executorService.submit(task2);
        System.out.println("task1 is completed? " + f1.isDone());
        System.out.println("task2 is completed? " + f2.isDone());

        while (f1.isDone()) {
            System.out.println("task1 completed");
            break;
        }

        while (f2.isDone()) {
            System.out.println("return value by task2: " + f2.get());
            break;
        }
    }
}
