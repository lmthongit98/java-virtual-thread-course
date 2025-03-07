/*
 * EXECUTOR SERVICES AND THREAD POOLS
 * Version A01: The executor service (of which thread pool) containing a single thread
 */

package com.tma.training.demo06_exec_service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class AppA01 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> System.out.println("Hello World"));
        executor.submit(() -> System.out.println("Hello Multithreading"));

        Runnable rnn = () -> System.out.println("Hello the Executor Service");
        executor.submit(rnn);

        // If I comment this statement, the app will not exit
        executor.shutdown();
    }

}
