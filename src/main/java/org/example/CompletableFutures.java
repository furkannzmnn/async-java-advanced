package org.example;

import com.sun.tools.javac.Main;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutures {
    public static void main(String[] args) {
        //exampleSingleThread();

         CompletableFutures main = new CompletableFutures();

         main.concatThreads(); // 5 saniye sürer

         main.nonConcatThreads(); // 10 saniye sürer
    }

    private static void exampleSingleThread() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {

                        // Web hizmetine istek gönderme
                        String response = sendRequestToWebService();

                        // İstek başarılıysa, cevabı işleme
                        processResponse(response);
                })
                .exceptionally(ex -> {
                    // Hata durumunda hatayı işleme
                    handleException(ex);
                    return null;
                });


        CompletableFuture.delayedExecutor(1000, TimeUnit.MILLISECONDS)
                .execute(() -> {
                    if (!future.isDone()) {
                        System.out.println("Request timed out");
                    }
                }
                );

        try {
            future.get();
        } catch (Throwable ex) {
            System.out.println("Exception: " + ex);
        }

        CompletableFuture.supplyAsync(() -> {
            try {
                // Web hizmetine istek gönderme
                String response = sendRequestToWebService();

                // İstek başarılıysa, cevabı işleme
                processResponse(response);
            } catch (Exception ex) {
                // Hata durumunda hatayı işleme
                handleException(ex);
            }
            return null;
        })
                .exceptionally(ex -> {
                    // Hata durumunda hatayı işleme
                    handleException(ex);
                    return null;
                })
                .thenRun(() -> System.out.println("Request completed"))
                .join();
    }

    private static void handleException(Throwable ex) {
        System.out.println("hop Exception: " + ex.getMessage());
    }

    private static void processResponse(String response) {
        System.out.println("Response: " + response);
    }

    private static String sendRequestToWebService() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int a = 1 / 0;
        return "response";
    }


    public String threadOne() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "threadOne";
    }

    public String threadTwo() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "threadTwo";
    }

    public String threadThree() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "threadThree";
    }

    public void concatThreads() {
        Long startTime = System.currentTimeMillis();

        CompletableFuture<String> threadOne = CompletableFuture.supplyAsync(this::threadOne);
        CompletableFuture<String> threadTwo = CompletableFuture.supplyAsync(this::threadTwo);
        CompletableFuture<String> threadThree = CompletableFuture.supplyAsync(this::threadThree);

        CompletableFuture.allOf(threadOne, threadTwo, threadThree).join();

        String result = threadOne.join() + threadTwo.join() + threadThree.join();
        Long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime));
        System.out.println(result);
    }
    public void nonConcatThreads() {
        Long strartTime = System.currentTimeMillis();

        threadOne();
        threadTwo();
        threadThree();

        Long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - strartTime));
    }
}
