package org.example;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class NonBlocking {

    public static void main(String[] args) throws InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create("https://dummyjson.com/products/1'"))
                .build();

        CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        future.thenAccept(response -> {
            System.out.println(response.statusCode());
            System.out.println(response.body());
        });
    }

}
