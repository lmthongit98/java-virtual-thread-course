/*
 * EXECUTOR SERVICES AND THREAD POOLS
 * Version B02: The executor service containing multiple threads - FixedThreadPool
 */

package com.tma.training.demo06_exec_service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;



public class AppB02 {

    public static void main(String[] args) throws InterruptedException {
        final int NUM_THREADS = 2;
        final int NUM_TASKS = 5;


        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);


        IntStream.range(0, NUM_TASKS).forEach(i -> executor.submit(() -> {
            char nameTask = (char) (i + 'A');

            System.out.println("Task %c is starting".formatted(nameTask));

            try { Thread.sleep(3000); } catch (InterruptedException e) { }

            System.out.println("Task %c is completed".formatted(nameTask));
        }));


        executor.shutdown();


        System.out.println("All tasks are submitted");

        executor.awaitTermination(20, TimeUnit.SECONDS);

        System.out.println("All tasks are completed");
    }

}
