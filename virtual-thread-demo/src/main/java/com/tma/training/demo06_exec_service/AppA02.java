/*
 * EXECUTOR SERVICES AND THREAD POOLS
 * Version A02: AutoCloseable
 */


package com.tma.training.demo06_exec_service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppA02 {

    public static void main(String[] args) {

        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            executor.submit(() -> System.out.println("Hello World"));
            executor.submit(() -> System.out.println("Hello Multithreading"));
            Runnable rnn = () -> System.out.println("Hello the Executor Service");
            executor.submit(rnn);
        }

    }

}
