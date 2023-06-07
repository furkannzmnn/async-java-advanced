package org.example;

import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceWebPageDownload {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        String[] urls = {
                "https://www.google.com",
                "https://www.facebook.com",
                "https://www.twitter.com"
        };

        CompletableFuture<String>[] futures = new CompletableFuture[urls.length];
        for (int i = 0; i < urls.length; i++) {
            final String url = urls[i];
            futures[i] = CompletableFuture.supplyAsync(() -> downloadWebPage(url), executorService);
        }

        CompletableFuture.allOf(futures)
                .thenRun(() -> {
                    for (CompletableFuture<String> future : futures) {
                        System.out.println(future.join());
                    }
                    executorService.shutdown();
                });

    }

    public static String downloadWebPage(String urlString) {
        try {
            // Download the web page
            URL url = new URL(urlString);
            return url.openConnection().getInputStream().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
