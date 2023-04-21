package org.example.forkJoinFramework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FileProcessor extends RecursiveTask<Integer> {
    private final ExecutorService executorService = ForkJoinPool.commonPool();

    private static final long serialVersionUID = 1L;
    private static final int THRESHOLD = 1000;
    private String filePath;

    @Override
    protected Integer compute() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                count += words.length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.filePath = "src/main/resources/lorem";
        fileProcessor.compute();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken one method: " + (endTime - startTime));
    }
}

class FileProcessorStreamParalelizm {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/lorem"))) {
            long count = br.lines().parallel().map(line -> line.split(" ").length).reduce(0, Integer::sum);
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken stream method: " + (endTime - startTime));

        startTime = System.currentTimeMillis();
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/lorem"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                count += words.length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        endTime = System.currentTimeMillis();
        System.out.println("Time taken concurrent method: " + (endTime - startTime));


    }
}
