/*
    VERSION 01: MULTI THREADS
 */

package com.tma.training.assignments;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App02 {
    public static void main(String[] args) {
        List<Integer> ids = ProductService.getSampleProductIds();

        long start;
        List<Future<String>> futures;
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) { // pool size 100
            start = System.currentTimeMillis();
            futures = ids.stream().map(id -> executor.submit(() -> ProductService.fetchProductName(id))).toList();
        }
        List<String> results = futures.stream().map(App02::getProductName).toList();
        long end = System.currentTimeMillis();

        System.out.println("Multi Threaded Results: " + results);
        System.out.println("Time Taken: " + (end - start) + " ms");
    }

    static <T> T getProductName(Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
