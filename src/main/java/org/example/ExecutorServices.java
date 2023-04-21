package org.example;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorServices {
    private final Executor executor = Executors.newSingleThreadExecutor();

    public void runTask(Runnable task) {
        executor.execute(task);
    }

    public void runTask(Runnable task, long delay) {
        executor.execute(() -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            task.run();
        });
    }

    public static void main(String[] args) {
        ExecutorServices executorServices = new ExecutorServices();
        executorServices.runTask(() -> System.out.println("Hello World"));
        executorServices.runTask(() -> System.out.println("Hello World"), 2000);
    }
}
