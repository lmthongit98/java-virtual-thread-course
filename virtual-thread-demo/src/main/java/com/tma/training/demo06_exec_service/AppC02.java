/*
 * EXECUTOR SERVICES AND THREAD POOLS
 * Version C02: The executor service and Future objects - Getting started
 */

package com.tma.training.demo06_exec_service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



public class AppC02 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> task = executor.submit(() -> getSquared(7));

        executor.shutdown();

        while (false == task.isDone()) {
            // Waiting...
        }

        Integer result = task.get();
        System.out.println(result);
    }


    private static int getSquared(int x) {
        return x * x;
    }

}
